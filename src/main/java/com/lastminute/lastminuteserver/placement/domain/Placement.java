package com.lastminute.lastminuteserver.placement.domain;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.geo.Point;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "placement")
public class Placement {

    @EmbeddedId
    private PlacementId placementId;

    @NotNull
    @Column
    private Point location;

    @Builder
    public Placement(PlacementId placementId, Point location) {
        this.placementId = placementId;
        this.location = location;
    }

    @Builder
    public Placement(String title, String roadAddress, Point location) {
        this(PlacementId.builder()
                .title(title)
                .roadAddress(roadAddress)
                .build(), location);
    }
}
