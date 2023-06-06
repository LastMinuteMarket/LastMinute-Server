package com.lastminute.lastminuteserver.purchase.service;

import com.lastminute.lastminuteserver.exceptions.RequestException;
import com.lastminute.lastminuteserver.exceptions.RequestExceptionCode;
import com.lastminute.lastminuteserver.purchase.domain.Purchase;
import com.lastminute.lastminuteserver.purchase.dto.PurchaseCreateDto;
import org.springframework.stereotype.Component;

@Component
public class PgAgency {
    public Purchase createPurchase(PurchaseCreateDto purchaseCreateDto){
        if (!tryCreatePurchase(purchaseCreateDto)){
            throw RequestException.of(RequestExceptionCode.PAYMENT_CREATION_FAILED);
        }
        return purchaseCreateDto.toEntity();
    }

    public void deletePurchase(Long purchaseId) {
        if (!tryDeletePurchase(purchaseId)){
            throw RequestException.of(RequestExceptionCode.PAYMENT_DELETION_FAILED);
        }
    }

    private Boolean tryCreatePurchase(PurchaseCreateDto purchaseCreateDto){ // PG사와의 연동 결과 리턴
        return true;
    }
    private Boolean tryDeletePurchase(Long purchaseId){ // PG사와의 연동 결과 리턴
        return true;
    }

}
