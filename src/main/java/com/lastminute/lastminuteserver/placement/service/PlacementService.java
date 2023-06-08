package com.lastminute.lastminuteserver.placement.service;

import com.lastminute.lastminuteserver.placement.domain.Placement;
import com.lastminute.lastminuteserver.placement.repository.PlacementRepository;
import com.lastminute.lastminuteserver.placement.dto.PlacementDto;
import lombok.RequiredArgsConstructor;
import org.locationtech.jts.io.ParseException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PlacementService {

    private final PlacementRepository placementRepository;

    /**
     * (이름, 도로명 주소)로 주소 정보를 생성합니다.
     * 기존 등록된 것이 없으면 새로 생성하고, 있으면 기존 정보 기준으로 반환합니다.
     * @param placement 주소 정보
     */
    public PlacementDto createIfNotExist(PlacementDto placement) throws ParseException {
        Optional<Placement> foundPlacement = placementRepository.findById(placement.getEntityId());

        if (foundPlacement.isPresent()) {
            return PlacementDto.of(foundPlacement.get());
        }

        Placement newPlacement = placement.toEntity();
        newPlacement = placementRepository.save(newPlacement);

        return PlacementDto.of(newPlacement);
    }


}
