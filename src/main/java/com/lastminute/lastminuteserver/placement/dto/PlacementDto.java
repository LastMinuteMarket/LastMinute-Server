package com.lastminute.lastminuteserver.placement.dto;

import com.lastminute.lastminuteserver.placement.domain.Placement;
import com.lastminute.lastminuteserver.placement.domain.PlacementId;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import org.springframework.data.geo.Point;

@Builder
public record PlacementDto (
    @NotNull(message = "위치명을 입력해주세요.")
    @Size(max = 100, message = "위치명은 최대 100자까지 입력할 수 있습니다.")
    String title,

    @NotNull(message = "도로명 주소를 입력해주세요.")
    @Size(max = 255, message = "도로명 주소는 최대 255자까지 입력할 수 있습니다.")
    String roadAddress,

    @NotNull(message = "사용처의 x 좌표를 입력해주세요.")
    Double pointX,

    @NotNull(message = "사용처의 y 좌표를 입력해주세요.")
    Double pointY
) {
    public Placement toEntity() {
        final PlacementId addressId = getEntityId();
        final Point location = new Point(this.pointX, this.pointY);

        return Placement.builder()
                .placementId(addressId)
                .location(location)
                .build();
    }

    public PlacementId getEntityId() {
        return PlacementId.builder()
                .title(this.title)
                .roadAddress(this.roadAddress)
                .build();
    }

    public static PlacementDto of(Placement entity) {
        return PlacementDto.builder()
                .title(entity.getPlacementId().getTitle())
                .roadAddress(entity.getPlacementId().getRoadAddress())
                .pointX(entity.getLocation().getX())
                .pointY(entity.getLocation().getY())
                .build();
    }
}
