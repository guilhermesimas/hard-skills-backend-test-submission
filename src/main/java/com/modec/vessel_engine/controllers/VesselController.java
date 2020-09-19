package com.modec.vessel_engine.controllers;

import com.modec.vessel_engine.contracts.VesselTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/vessels")
public class VesselController {

    @PostMapping
    public ResponseEntity<VesselTO> create(@Valid @RequestBody VesselTO vessel){
        return ResponseEntity.ok(vessel);
    }
}
