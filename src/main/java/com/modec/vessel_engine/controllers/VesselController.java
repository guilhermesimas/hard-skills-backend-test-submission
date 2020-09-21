package com.modec.vessel_engine.controllers;

import com.modec.vessel_engine.controllers.errors.VesselAlreadyExistsException;
import com.modec.vessel_engine.entities.Vessel;
import com.modec.vessel_engine.repositories.VesselRepository;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/vessels")
public class VesselController {

    private final VesselRepository vesselRepository;

    @ApiOperation("Create a vessel")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Returns the created vessel"),
            @ApiResponse(code = 400, message = "Invalid request parameters"),
            @ApiResponse(code = 409, message = "Vessel with same code already exists")
    })
    @PostMapping
    public ResponseEntity<Vessel> create(@Valid @RequestBody Vessel vessel) {
        if(vesselRepository.findById(vessel.getId()).isPresent()) {
            throw new VesselAlreadyExistsException(vessel);
        }
        return new ResponseEntity<>(vesselRepository.save(vessel), HttpStatus.CREATED);
    }
}
