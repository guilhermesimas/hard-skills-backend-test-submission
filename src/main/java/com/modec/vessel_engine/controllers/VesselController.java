package com.modec.vessel_engine.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/vessels")
public class VesselController {

    @PostMapping
    public ResponseEntity<String> create(){
        return ResponseEntity.ok("OK");
    }
}
