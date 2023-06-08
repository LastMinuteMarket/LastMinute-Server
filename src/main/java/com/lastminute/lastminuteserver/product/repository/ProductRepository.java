package com.lastminute.lastminuteserver.product.repository;


import com.lastminute.lastminuteserver.product.domain.Product;
import com.lastminute.lastminuteserver.product.domain.ProductState;
import org.locationtech.jts.geom.Point;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;

public interface ProductRepository extends JpaRepository<Product, Long> {

    @Query(value = """
            SELECT pd
            FROM Product pd
            JOIN FETCH User wr
            LEFT JOIN FETCH Placement pl
            WHERE FUNCTION('ST_Distance_Sphere', pl.location, :position) < :meters
            AND pd.reservedTime >= FUNCTION('NOW')
            AND pd.productState = :state
            """)
    Slice<Product> searchByLocation(Point position, ProductState state, Integer meters, Pageable pageable);

    @Query(value = """
            SELECT pd
            FROM Product pd
            JOIN FETCH User wr
            LEFT JOIN FETCH Placement pl
            WHERE FUNCTION('ST_Distance_Sphere', pl.location, :position) < :meters
            AND pd.reservedTime BETWEEN :startTime AND :endTime
            AND pd.productState = :state
            """)
    Slice<Product> searchByLocationAndTime(Point position, ProductState state, Integer meters, LocalDateTime startTime, LocalDateTime endTime, Pageable pageable);

}
