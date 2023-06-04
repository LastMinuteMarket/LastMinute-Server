package com.lastminute.lastminuteserver.placement.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Embeddable
public class PlacementId implements Serializable {

    @Serial
    private static final long serialVersionUID = 1230819L;

    @Column(name = "menu", length = 100)
    private String title;

    @Column(name = "road_address", length = 255)
    private String roadAddress;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PlacementId placementId = (PlacementId) o;
        return Objects.equals(title, placementId.title) && Objects.equals(roadAddress, placementId.roadAddress);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, roadAddress);
    }

    @Builder
    public PlacementId(String title, String roadAddress) {
        this.title = title;
        this.roadAddress = roadAddress;
    }
}
