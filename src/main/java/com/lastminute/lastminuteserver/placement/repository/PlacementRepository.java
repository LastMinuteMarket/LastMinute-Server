package com.lastminute.lastminuteserver.placement.repository;

import com.lastminute.lastminuteserver.placement.domain.Placement;
import com.lastminute.lastminuteserver.placement.domain.PlacementId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlacementRepository extends JpaRepository<Placement, PlacementId> {
}
