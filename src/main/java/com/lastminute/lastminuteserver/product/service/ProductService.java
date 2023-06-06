package com.lastminute.lastminuteserver.product.service;

import com.lastminute.lastminuteserver.cloudfile.service.ImageFileService;
import com.lastminute.lastminuteserver.exceptions.RequestException;
import com.lastminute.lastminuteserver.exceptions.RequestExceptionCode;
import com.lastminute.lastminuteserver.placement.service.PlacementService;
import com.lastminute.lastminuteserver.product.domain.PriceSchedule;
import com.lastminute.lastminuteserver.product.domain.ProductLike;
import com.lastminute.lastminuteserver.product.domain.ProductState;
import com.lastminute.lastminuteserver.product.dto.PriceScheduleDto;
import com.lastminute.lastminuteserver.product.dto.ProductCreateDto;
import com.lastminute.lastminuteserver.placement.dto.PlacementDto;
import com.lastminute.lastminuteserver.product.dto.ProductAllDto;
import com.lastminute.lastminuteserver.product.dto.ProductSummaryDto;
import com.lastminute.lastminuteserver.product.repository.ProductLikeRepository;
import com.lastminute.lastminuteserver.product.repository.ProductRepository;
import com.lastminute.lastminuteserver.product.domain.Product;
import com.lastminute.lastminuteserver.user.domain.User;
import com.lastminute.lastminuteserver.user.repository.UserRepository;
import com.lastminute.lastminuteserver.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.data.geo.Point;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductService {

    // 검색 범위 2km
    public static final int SEARCH_RANGE = 2_000;

    private final ProductRepository productRepository;

    private final UserService userService;
    private final PlacementService placementService;
    private final ImageFileService imageFileService;
    private final ProductLikeRepository productLikeRepository;
    private final UserRepository userRepository;

    @Transactional
    public ProductAllDto createProduct(Long writerId, ProductCreateDto request) {
        //TODO : 이미지 업로드
        PlacementDto placement = placementService.createIfNotExist(request.placement());
        if (!userService.isActivateUser(writerId)) {
            throw RequestException.of(RequestExceptionCode.USER_ILLEGAL_BEHAVIOR);
        }

        Product product = Product.builder()
                .writerId(writerId)
                .placementTitle(placement.title())
                .placementRoadAddress(placement.roadAddress())
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

    public Slice<ProductSummaryDto> searchOpeningProducts(Point position, Pageable pageable) {
        Slice<Product> products = productRepository.searchByLocation(
                position,
                ProductState.OPEN,
                SEARCH_RANGE,
                pageable
        );

        return new SliceImpl<>(
                products.stream()
                        .map(ProductSummaryDto::of)
                        .collect(Collectors.toList())
        );
    }

    public Slice<ProductSummaryDto> searchOpeningProducts(Point position, LocalDateTime startTime, LocalDateTime endTime, Pageable pageable) {
        Slice<Product> products = productRepository.searchByLocationAndTime(
                position,
                ProductState.OPEN,
                SEARCH_RANGE,
                startTime,
                endTime,
                pageable
        );

        return new SliceImpl<>(
                products.stream()
                        .map(ProductSummaryDto::of)
                        .collect(Collectors.toList()));
    }

    public ProductAllDto getProductById(Long productId) {
        Product product = findProductInternal(productId);

        if (!product.isVisible()) {
            throw RequestException.of(RequestExceptionCode.PRODUCT_NOT_VISIBLE);
        }

        product.increaseView();
        productRepository.save(product);
        return ProductAllDto.of(product);
    }

    public void deleteProduct(Long userId, Long productId) {
        Product product = findProductInternal(productId);

        if (!product.getProductState().isVisible()) {
            throw RequestException.of(RequestExceptionCode.PRODUCT_ALREADY_HIDDEN);
        }

        if (!Objects.equals(userId, product.getWriterId())) {
            throw RequestException.of(RequestExceptionCode.PRODUCT_FORBIDDEN);
        }

        product.setProductState(ProductState.HIDDEN);
        productRepository.save(product);
    }

    @Transactional
    public void likeProduct(Long userId, Long productId) {
        Product product = findProductInternal(productId);

        ProductLike productLike = ProductLike.builder()
                .productId(productId)
                .userId(userId)
                .build();
        product.addProductLike(productLike);
    }

    @Transactional
    public void removeLikeProduct(Long userId, Long productId) {
        Product product = findProductInternal(productId);
        User user = getUser(userId);

        ProductLike productLike = productLikeRepository.findByUserAndProduct(user, product)
                        .orElseThrow(() -> RequestException.of(RequestExceptionCode.PRODUCT_LIKE_NOT_FOUND));
        product.removeProductLike(productLike);
        productLikeRepository.delete(productLike);
    }

    private Product findProductInternal(Long productId) {
        return productRepository.findById(productId)
                .orElseThrow(() -> RequestException.of(RequestExceptionCode.PRODUCT_NOT_FOUND));
    }

    private User getUser(Long userId){
        return userRepository.findById(userId)
                .orElseThrow(() -> RequestException.of(RequestExceptionCode.USER_NOT_FOUND));
    }
}
