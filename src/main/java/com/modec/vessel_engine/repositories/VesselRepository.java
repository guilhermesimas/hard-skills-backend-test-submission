package com.modec.vessel_engine.repositories;

import com.modec.vessel_engine.entities.Vessel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VesselRepository extends JpaRepository<Vessel, String> {
}
