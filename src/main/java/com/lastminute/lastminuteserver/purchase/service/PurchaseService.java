package com.lastminute.lastminuteserver.purchase.service;

import com.lastminute.lastminuteserver.exceptions.RequestException;
import com.lastminute.lastminuteserver.exceptions.RequestExceptionCode;
import com.lastminute.lastminuteserver.product.domain.Product;
import com.lastminute.lastminuteserver.product.repository.ProductRepository;
import com.lastminute.lastminuteserver.purchase.domain.InstallmentPeriod;
import com.lastminute.lastminuteserver.purchase.domain.PaymentMethod;
import com.lastminute.lastminuteserver.purchase.domain.Purchase;
import com.lastminute.lastminuteserver.purchase.domain.PurchaseState;
import com.lastminute.lastminuteserver.purchase.dto.PurchaseCreateDto;
import com.lastminute.lastminuteserver.purchase.repository.PurchaseRepository;
import com.lastminute.lastminuteserver.user.domain.User;
import com.lastminute.lastminuteserver.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PurchaseService {

    private final PurchaseRepository purchaseRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;

    @Transactional
    public Purchase createPurchase(Long productId, Long userId, PurchaseCreateDto purchaseCreateDto){
        Product product = validateProduct(productId);
        User user = getUser(userId);
        Purchase purchase = Purchase.builder()
                .paymentMethod(PaymentMethod.findByKey(purchaseCreateDto.getPaymentMethod()))
                .installmentPeriod(InstallmentPeriod.findByKey(purchaseCreateDto.getInstallmentPeriod()))
                .originalPrice(purchaseCreateDto.getOriginalPrice())
                .user(user)
                .product(product)
                .build();
        return purchaseRepository.save(purchase);
    }

    @Transactional(readOnly = true)
    public Purchase getPurchase(Long purchaseId){
        return validatePurchase(purchaseId);
    }

    @Transactional(readOnly = true)
    public List<Purchase> getPurchaseListByUser(Long userId){
        User user = getUser(userId);
        return purchaseRepository.findByUser(user);
    }

    @Transactional
    public void deletePurchase(Long purchaseId){
        Purchase purchase = validatePurchase(purchaseId);
        // WITHDRAWN 일 경우 일정 기간 지나면 DB에서 삭제(구현 x)
        if(purchase.getPurchaseState() == PurchaseState.COMPLETED){
            throw RequestException.of(RequestExceptionCode.PAYMENT_OVER_CANCEL_PERIOD);
        }
        // TODO: 연관관계 제거

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
