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

    @GetMapping("vessels/{vesselCode}/equipment")
    public ResponseEntity<List<Equipment>> listVesselEquipment(@PathVariable("vesselCode") String vesselCode) {
        Optional<Vessel> targetVessel = vesselRepository.findById(vesselCode);

        if(targetVessel.isEmpty()) {
            throw new VesselDoesNotExist(vesselCode);
        }
        return ResponseEntity.ok(equipmentRepository.findAllByVesselAndStatus(targetVessel.get(), "active"));
    }

}
