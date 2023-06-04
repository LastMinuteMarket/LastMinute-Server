package com.lastminute.lastminuteserver.purchase.service;

import com.lastminute.lastminuteserver.exceptions.RequestException;
import com.lastminute.lastminuteserver.exceptions.RequestExceptionCode;
import com.lastminute.lastminuteserver.purchase.domain.Purchase;
import com.lastminute.lastminuteserver.purchase.dto.PurchaseCreateDto;

public class PgAgency {
    public Purchase createPurchase(PurchaseCreateDto purchaseCreateDto){
        if (!tryPurchase(purchaseCreateDto)){
            throw RequestException.of(RequestExceptionCode.PAYMENT_CREATION_FAILED);
        }
        return purchaseCreateDto.toEntity();
    }

    public void deletePurchase(Long purchaseId) {
    }

    private Boolean tryPurchase(PurchaseCreateDto purchaseCreateDto){ // PG사와의 연동 결과 리턴
        return true;
    }

}
