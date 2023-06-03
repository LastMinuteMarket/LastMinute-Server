package com.lastminute.lastminuteserver.purchase.service;

import com.lastminute.lastminuteserver.purchase.dto.PaymentBridgeCreateDto;
import com.lastminute.lastminuteserver.purchase.dto.PaymentCreateDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class PgAgencyAdapter {

    private final PgAgencyService pgAgencyService;

    public Optional<PaymentBridgeCreateDto> createPayment(PaymentCreateDto request) {
        return pgAgencyService.createPayment(request);
    }

    public void deletePayment(Long pgId){
        pgAgencyService.deletePayment(pgId);
    }
}
