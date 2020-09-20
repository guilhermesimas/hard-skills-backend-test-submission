package com.modec.vessel_engine.repositories;

import com.modec.vessel_engine.entities.Equipment;
import com.modec.vessel_engine.entities.Vessel;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.ResponseEntity;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

public interface EquipmentRepository extends JpaRepository<Equipment, String> {
    List<Equipment> findAllByVesselAndStatus(Vessel vessel, String status);
}
