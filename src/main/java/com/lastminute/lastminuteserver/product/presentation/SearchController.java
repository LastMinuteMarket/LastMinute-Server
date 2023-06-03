package com.lastminute.lastminuteserver.product.presentation;

import com.lastminute.lastminuteserver.common.ResponseDto;
import com.lastminute.lastminuteserver.product.dto.ProductSummaryDto;
import com.lastminute.lastminuteserver.product.service.ProductService;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.geo.Point;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/api/search")
public class SearchController {

    private final ProductService productService;

    @GetMapping()
    ResponseEntity<ResponseDto<?>> searchProducts(
            @RequestParam Double lat,
            @RequestParam Double lot,
            @RequestParam(required = false) LocalDateTime startTime,
            @RequestParam(required = false) LocalDateTime endTime,
            Pageable pageable
    ) {
        Point location = new Point(lot, lat);
        Slice<ProductSummaryDto> products;
        
        if (startTime != null && endTime != null) {
            products = productService.searchOpeningProducts(location, startTime, endTime, pageable);
        } else {
            products = productService.searchOpeningProducts(location, pageable);
        }

        return ResponseEntity
                .ok(ResponseDto.of(products));
    }
}
