package com.lastminute.lastminuteserver.product.presentation;

import com.lastminute.lastminuteserver.common.ResponseDto;
import com.lastminute.lastminuteserver.product.dto.ProductAllDto;
import com.lastminute.lastminuteserver.product.dto.ProductCreateDto;
import com.lastminute.lastminuteserver.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/api/products")
public class ProductController {

    private final ProductService productService;

    @GetMapping("/{productId}")
    ResponseEntity<ResponseDto<?>> getProductById(@PathVariable("productId") Long productId) {
        ProductAllDto product = productService.getProductById(productId);

        return ResponseEntity
                .ok(ResponseDto.of(product));
    }

    @PostMapping()
    ResponseEntity<ResponseDto<?>> postProduct(@RequestBody ProductCreateDto productCreate) {

        // TODO : 토큰에서 id 불러오기
        Long writerId = 1L;

        ProductAllDto product = productService.createProduct(writerId, productCreate);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ResponseDto.of(product));
    }

    @DeleteMapping("/{productId}")
    ResponseEntity<ResponseDto<?>> deleteProduct(@PathVariable("productId") Long productId) {

        // TODO : 토큰에서 id 불러오기
        Long userId = 12L;
        productService.deleteProduct(userId, productId);
        return ResponseEntity
                .ok(ResponseDto.emptySuccess());
    }
}
