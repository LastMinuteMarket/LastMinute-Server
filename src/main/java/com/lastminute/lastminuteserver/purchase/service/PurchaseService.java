package com.lastminute.lastminuteserver.purchase.service;

import com.lastminute.lastminuteserver.exceptions.RequestException;
import com.lastminute.lastminuteserver.exceptions.RequestExceptionCode;
import com.lastminute.lastminuteserver.product.domain.Product;
import com.lastminute.lastminuteserver.product.domain.ProductState;
import com.lastminute.lastminuteserver.product.repository.ProductRepository;
import com.lastminute.lastminuteserver.purchase.domain.Purchase;
import com.lastminute.lastminuteserver.purchase.domain.PurchaseState;
import com.lastminute.lastminuteserver.purchase.dto.PurchaseCreateDto;
import com.lastminute.lastminuteserver.purchase.dto.PurchaseResponseDto;
import com.lastminute.lastminuteserver.purchase.repository.PurchaseRepository;
import com.lastminute.lastminuteserver.user.domain.User;
import com.lastminute.lastminuteserver.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PurchaseService {

    private final PurchaseRepository purchaseRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final PgAgency pgAgency;

    @Transactional
    public PurchaseResponseDto createPurchase(Long productId, Long userId, PurchaseCreateDto purchaseCreateDto){
        Product product = validateProduct(productId);
        User user = getUser(userId);

        Purchase purchase = pgAgency.createPurchase(purchaseCreateDto);
        product.setProductState(ProductState.SOLD_OUT);
        purchase.setRelations(product, user);
        purchaseRepository.save(purchase);

        return PurchaseResponseDto.from(purchase);
    }

    @Transactional(readOnly = true)
    public PurchaseResponseDto getPurchase(Long purchaseId, Long userId){
        Purchase purchase = validatePurchase(purchaseId);
        User user = getUser(userId);

        if (purchase.getUser() != user){
            throw RequestException.of(RequestExceptionCode.USER_CANNOT_BEHAVE);
        }

        return PurchaseResponseDto.from(purchase);
    }

    @Transactional(readOnly = true)
    public List<PurchaseResponseDto> getPurchaseListByUser(Long userId){
        User user = getUser(userId);
        return purchaseRepository.findByUser(user).stream()
                .map(purchase -> PurchaseResponseDto.from(purchase))
                .collect(Collectors.toList());
    }

    @Transactional
    public void deletePurchase(Long purchaseId, Long userId){
        Purchase purchase = validatePurchase(purchaseId);
        User user = getUser(userId);
        if (purchase.getPurchaseState() == PurchaseState.COMPLETED){
            throw RequestException.of(RequestExceptionCode.PAYMENT_OVER_CANCEL_PERIOD);
        }
        if (purchase.getUser() != user){
            throw RequestException.of(RequestExceptionCode.USER_CANNOT_BEHAVE);
        }

        pgAgency.deletePurchase(purchaseId);
        purchaseRepository.delete(purchase);
    }

    private Purchase validatePurchase(Long purchaseId) {
        return purchaseRepository.findById(purchaseId)
                .orElseThrow(() -> RequestException.of(RequestExceptionCode.PAYMENT_NOT_FOUND));
    }
    private Product validateProduct(Long productId) {
        return productRepository.findById(productId)
                .orElseThrow(() -> RequestException.of(RequestExceptionCode.PRODUCT_NOT_FOUND));
    }

    private User getUser(Long userId) {
        return userRepository.findById(userId).get();
    }

}
