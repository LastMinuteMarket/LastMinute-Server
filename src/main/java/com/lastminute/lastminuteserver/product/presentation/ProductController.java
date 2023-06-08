package com.lastminute.lastminuteserver.product.presentation;

import com.lastminute.lastminuteserver.common.ResponseDto;
import com.lastminute.lastminuteserver.product.dto.ProductAllDto;
import com.lastminute.lastminuteserver.product.dto.ProductCreateDto;
import com.lastminute.lastminuteserver.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
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
    ResponseEntity<ResponseDto<?>> postProduct(@RequestParam Long userId,
                                               @RequestBody ProductCreateDto productCreate) {
        ProductAllDto product = productService.createProduct(userId, productCreate);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ResponseDto.of(product));
    }

    @PostMapping("/{productId}/like")
    ResponseEntity<ResponseDto<?>> likeProduct(@PathVariable("productId") Long productId,
                                               @RequestParam Long userId){
        productService.likeProduct(productId, userId);
        return ResponseEntity
                .ok(ResponseDto.emptySuccess());
    }

    @DeleteMapping("/{productId}/like")
    ResponseEntity<ResponseDto<?>> removeLikeProduct(@PathVariable("productId") Long productId,
                                                     @RequestParam Long userId){
        productService.removeLikeProduct(productId, userId);
        return ResponseEntity
                .ok(ResponseDto.emptySuccess());
    }

    @DeleteMapping("/{productId}")
    ResponseEntity<ResponseDto<?>> deleteProduct(@PathVariable("productId") Long productId,
                                                 @RequestParam Long userId) {

        productService.deleteProduct(productId, userId);
        return ResponseEntity
                .ok(ResponseDto.emptySuccess());
    }
}
