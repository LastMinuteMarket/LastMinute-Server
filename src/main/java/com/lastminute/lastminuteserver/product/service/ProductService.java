package com.lastminute.lastminuteserver.product.service;

import com.lastminute.lastminuteserver.cloudfile.service.ImageFileService;
import com.lastminute.lastminuteserver.placement.service.PlacementService;
import com.lastminute.lastminuteserver.product.dto.ProductCreateDto;
import com.lastminute.lastminuteserver.placement.dto.PlacementDto;
import com.lastminute.lastminuteserver.product.dto.ProductDto;
import com.lastminute.lastminuteserver.product.repository.ProductRepository;
import com.lastminute.lastminuteserver.product.domain.Product;
import com.lastminute.lastminuteserver.user.domain.User;
import com.lastminute.lastminuteserver.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    private final PlacementService placementService;
    private final ImageFileService imageFileService;

    @Transactional
    public ProductDto createProduct(Long writerId, ProductCreateDto request) {
        //TODO : 이미지 업로드

        PlacementDto placement = placementService.createIfNotExist(request.getPlacement());
        User writer = userRepository.findById(writerId)
                .orElseThrow(() -> new RuntimeException(""));

        Product product = Product.builder()
                .writer(writer)
                .title(request.getDetail().title())
                .content(request.getDetail().content())
                .price(request.getDetail().price())
                .placement(placement.toEntity())
                .build();

        product = productRepository.save(product);
        return ProductDto.of(product);
    }

    public Page<ProductDto> readProducts(Pageable pageable) {
        Page<Product> products = productRepository.findAll(pageable);
        return new PageImpl<>(
                products.stream()
                .map(ProductDto::of)
                .collect(Collectors.toList())
        );
    }

}
