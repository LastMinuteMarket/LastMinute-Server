package com.lastminute.lastminuteserver.product.domain;

import com.lastminute.lastminuteserver.placement.domain.Placement;
import com.lastminute.lastminuteserver.user.domain.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
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
    @JoinColumn(name = "writer_id", referencedColumnName = "id")
    private User writer;

    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST})
    @JoinColumns ({
        @JoinColumn(name = "placement_title", referencedColumnName = "title"),
        @JoinColumn(name = "placement_road_address", referencedColumnName = "road_address")
    })
    private Placement placement;

    @NotNull
    @Column(length = 100)
    private String title;

    @NotNull
    @Column(length = 1_000)
    private String content;

    @NotNull
    @Size(min = 0, max = 1_000_000_000)
    private Integer price;

    @NotNull
    private Integer views = 0;

    @NotNull
    @Column(length = 8)
    @Enumerated(EnumType.STRING)
    ProductState productState = ProductState.OPEN;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "product")
    private final Set<ProductImage> images = new HashSet<>();

    @NotNull
    @Column(updatable = false)
    @CreatedDate
    private LocalDateTime createdAt;

    @NotNull
    @Column
    @LastModifiedDate
    private LocalDateTime updatedAt;

    public void setProductState(ProductState productState) {
        this.productState = productState;
    }

    public void putImages(Collection<ProductImage> images) {
        this.images.addAll(images);
    }

    public void deleteImage(ProductImage image) {
        this.images.remove(image);
    }

    @Builder
    public Product(User writer, Placement placement, String title, String content, Integer price, ProductState productState) {
        this.writer = writer;
        this.placement = placement;
        this.title = title;
        this.content = content;
        this.price = price;
        this.productState = productState;
    }
}
