package com.lastminute.lastminuteserver.payment.service;

import com.lastminute.lastminuteserver.payment.dto.PaymentBridgeCreateDto;
import com.lastminute.lastminuteserver.payment.dto.PaymentCreateDto;
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
