package com.modec.vessel_engine.controllers;

import com.modec.vessel_engine.controllers.errors.VesselAlreadyExistsException;
import com.modec.vessel_engine.controllers.errors.VesselDoesNotExist;
import com.modec.vessel_engine.entities.Equipment;
import com.modec.vessel_engine.entities.Vessel;
import com.modec.vessel_engine.repositories.EquipmentRepository;
import com.modec.vessel_engine.repositories.VesselRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Optional;
import java.util.Properties;

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
        return new ResponseEntity<>(equipmentRepository.save(equipment), HttpStatus.CREATED);
    }
}
