package com.modec.vessel_engine.repositories;

import com.modec.vessel_engine.entities.Equipment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EquipmentRepository extends JpaRepository<Equipment, String> {
}
