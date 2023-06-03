package com.lastminute.lastminuteserver.product.domain;

import com.lastminute.lastminuteserver.placement.domain.Placement;
import com.lastminute.lastminuteserver.user.domain.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
@Entity
@Table(name = "products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "writer_id", referencedColumnName = "id", insertable = false, updatable = false)
    private User writer;

    @Column(name = "wrier_id")
    private Long writerId;

    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST})
    @JoinColumns ({
        @JoinColumn(name = "placement_title", referencedColumnName = "menu", insertable = false, updatable = false),
        @JoinColumn(name = "placement_road_address", referencedColumnName = "road_address", insertable = false, updatable = false)
    })
    private Placement placement;

    @Column(name = "placement_title")
    private String placementTitle;

    @Column(name = "placement_road_address")
    private String placementRoadAddress;

    @NotNull
    @Column(length = 100)
    private String menu;

    @NotNull
    @Column(length = 1000)
    private String description;

    @NotNull
    @Column(name = "reserved_peoples")
    @Min(value = 0)
    private Integer reservedPeoples;

    @NotNull
    @Column(name = "reserved_time")
    private LocalDateTime reservedTime;

    @NotNull
    @Column(name = "reservation_type", length = 10)
    @Enumerated(EnumType.STRING)
    private ReservationType reservationType;

    @NotNull
    @Column(name = "price_paid")
    @Min(value = 0)
    private Integer pricePaid;

    @NotNull
    @Column(name = "price_now")
    @Min(value = 0)
    private Integer priceNow;

    @NotNull
    @Column
    @Min(value = 0)
    private Integer views = 0;

    @NotNull
    @Column(name = "product_state", length = 8)
    @Enumerated(EnumType.STRING)
    private ProductState productState = ProductState.OPEN;

    @NotNull
    @Column(name = "created_at", updatable = false)
    @CreatedDate
    private LocalDateTime createdAt;

    @NotNull
    @Column(name = "updated_at")
    @LastModifiedDate
    private LocalDateTime updatedAt;

    @Version
    @Column(name = "opt_lock")
    private Integer optLock = 0;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "product")
    private final Set<ProductImage> images = new HashSet<>();

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "product")
    private final Set<ProductLike> likes = new HashSet<>();

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "product")
    private final Set<PriceSchedule> priceSchedules = new HashSet<>();

    public boolean isVisible() {
        return this.productState != ProductState.HIDDEN;
    }

    public void setProductState(ProductState productState) {
        this.productState = productState;
    }

    public void putImages(Collection<ProductImage> images) {
        this.images.addAll(images);
    }

    public void deleteImage(ProductImage image) {
        this.images.remove(image);
    }

    public void putPriceSchedule(PriceSchedule priceSchedule) {
        this.priceSchedules.add(priceSchedule);
    }

    public void putPriceSchedules(Collection<PriceSchedule> priceSchedules) {
        this.priceSchedules.addAll(priceSchedules);
    }

    public void increaseView() {
        this.views++;
    }

    @Builder
    public Product(Long writerId,
                   String placementTitle,
                   String placementRoadAddress,
                   String menu,
                   String description,
                   Integer reservedPeoples,
                   LocalDateTime reservedTime,
                   ReservationType reservationType,
                   Integer pricePaid,
                   Integer priceNow,
                   ProductState productState) {
        this.writerId = writerId;
        this.placementTitle = placementTitle;
        this.placementRoadAddress = placementRoadAddress;
        this.menu = menu;
        this.description = description;
        this.reservedPeoples = reservedPeoples;
        this.reservedTime = reservedTime;
        this.reservationType = reservationType;
        this.pricePaid = pricePaid;
        this.priceNow = priceNow;
        this.productState = productState;
    }
}
