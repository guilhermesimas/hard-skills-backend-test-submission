package com.modec.vessel_engine.controllers;

import com.modec.vessel_engine.contracts.DeactivateEquipment;
import com.modec.vessel_engine.controllers.errors.EquipmentAlreadyExistsException;
import com.modec.vessel_engine.controllers.errors.EquipmentDoesNotExist;
import com.modec.vessel_engine.controllers.errors.InvalidEquipmentList;
import com.modec.vessel_engine.controllers.errors.VesselDoesNotExist;
import com.modec.vessel_engine.entities.Equipment;
import com.modec.vessel_engine.entities.Vessel;
import com.modec.vessel_engine.repositories.EquipmentRepository;
import com.modec.vessel_engine.repositories.VesselRepository;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class EquipmentController {

    private final VesselRepository vesselRepository;
    private final EquipmentRepository equipmentRepository;

    @ApiOperation("Register an equipment to a vessel")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Returns registered equipment"),
            @ApiResponse(code = 404, message = "Target vessel does not exist"),
            @ApiResponse(code = 400, message = "Invalid request parameters"),
            @ApiResponse(code = 409, message = "Equipment with same code already exists")
    })
    @PostMapping("vessels/{vesselCode}/equipment")
    public ResponseEntity<Equipment> create(@PathVariable("vesselCode") String vesselCode, @Valid @RequestBody Equipment equipment) {
        Optional<Vessel> targetVessel = vesselRepository.findById(vesselCode);

        if(targetVessel.isEmpty()) {
            throw new VesselDoesNotExist(vesselCode);
        }

        equipment.setVessel(targetVessel.get());
        if(equipmentRepository.existsById(equipment.getCode())){
            throw new EquipmentAlreadyExistsException(equipment);
        }
        return new ResponseEntity<>(equipmentRepository.save(equipment), HttpStatus.CREATED);
    }

    @ApiOperation("Batch deactivate equipments")
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "Equipment have been deactivated"),
            @ApiResponse(code = 404, message = "Target vessel does not exist"),
            @ApiResponse(code = 400, message = "Invalid request parameters")
    })
    @PostMapping("vessels/{vesselCode}/equipment/deactivate")
    public ResponseEntity<Void> update(@PathVariable("vesselCode") String vesselCode, @Valid @RequestBody DeactivateEquipment deactivateEquipment) {
        if(vesselRepository.existsById(vesselCode) == false){
            throw new VesselDoesNotExist(vesselCode);
        }

        List<Equipment> toDeactivate = equipmentRepository.findAllById(deactivateEquipment.getEquipment())
                .stream()
                .filter(equipment -> equipment.getStatus().equals("active"))
                .collect(Collectors.toUnmodifiableList());
        if(toDeactivate.size() < deactivateEquipment.getEquipment().size()) {
            List<String> foundEquipment = toDeactivate.stream()
                    .map(Equipment::getCode)
                    .collect(Collectors.toUnmodifiableList());
            List<String> notFoundEquipment = deactivateEquipment.getEquipment().stream()
                    .filter(foundEquipment::contains)
                    .collect(Collectors.toUnmodifiableList());
            throw new InvalidEquipmentList(notFoundEquipment);
        }

        toDeactivate.forEach(equipment -> equipment.setStatus("inactive"));

        equipmentRepository.saveAll(toDeactivate);

        return ResponseEntity.noContent().build();
    }

    @ApiOperation("Get all active equipment for target vessel")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Returns all active equipment for vessel"),
            @ApiResponse(code = 404, message = "Target vessel does not exist")
    })
    @GetMapping("vessels/{vesselCode}/equipment")
    public ResponseEntity<List<Equipment>> listVesselEquipment(@PathVariable("vesselCode") String vesselCode) {
        Optional<Vessel> targetVessel = vesselRepository.findById(vesselCode);

        if(targetVessel.isEmpty()) {
            throw new VesselDoesNotExist(vesselCode);
        }
        return ResponseEntity.ok(equipmentRepository.findAllByVesselAndStatus(targetVessel.get(), "active"));
    }

}
