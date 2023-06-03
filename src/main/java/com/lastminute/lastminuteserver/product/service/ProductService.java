package com.lastminute.lastminuteserver.product.service;

import com.lastminute.lastminuteserver.cloudfile.service.ImageFileService;
import com.lastminute.lastminuteserver.exceptions.RequestException;
import com.lastminute.lastminuteserver.exceptions.RequestExceptionCode;
import com.lastminute.lastminuteserver.placement.service.PlacementService;
import com.lastminute.lastminuteserver.product.domain.PriceSchedule;
import com.lastminute.lastminuteserver.product.dto.PriceScheduleDto;
import com.lastminute.lastminuteserver.product.dto.ProductCreateDto;
import com.lastminute.lastminuteserver.placement.dto.PlacementDto;
import com.lastminute.lastminuteserver.product.dto.ProductAllDto;
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

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    private final PlacementService placementService;
    private final ImageFileService imageFileService;

    @Transactional
    public ProductAllDto createProduct(Long writerId, ProductCreateDto request) {
        //TODO : 이미지 업로드

        PlacementDto placement = placementService.createIfNotExist(request.placement());
        User writer = userRepository.findById(writerId)
                .orElseThrow(() -> RequestException.of(RequestExceptionCode.USER_NOT_FOUND));

        Product product = Product.builder()
                .writer(writer)
                .placement(placement.toEntity())
                .menu(request.detail().menu())
                .description(request.detail().description())
                .reservationType(request.detail().reservationType())
                .reservedPeoples(request.detail().reservedPeoples())
                .reservedTime(request.detail().reservedTime())
                .pricePaid(request.detail().pricePaid())
                .priceNow(request.detail().priceNow())
                .build();

        putPriceSchedules(product, request.priceSchedules());
        product = productRepository.save(product);
        return ProductAllDto.of(product);
    }

    private static void putPriceSchedules(Product product, List<PriceScheduleDto> priceScheduleDtos) {
        if (Objects.isNull(priceScheduleDtos))
            return;

        List<PriceSchedule> priceSchedules = priceScheduleDtos.stream()
                .map(dto -> PriceSchedule.builder()
                        .productId(product.getId())
                        .price(dto.price())
                        .applyAt(dto.applyAt())
                        .build()
                ).toList();
        product.putPriceSchedules(priceSchedules);
    }

    public Page<ProductAllDto> readProducts(Pageable pageable) {
        Page<Product> products = productRepository.findAll(pageable);
        return new PageImpl<>(
                products.stream()
                .map(ProductAllDto::of)
                .collect(Collectors.toList())
        );
    }

}
