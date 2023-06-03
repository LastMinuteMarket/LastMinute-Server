package com.lastminute.lastminuteserver.purchase.service;

import com.lastminute.lastminuteserver.purchase.dto.PaymentBridgeCreateDto;
import com.lastminute.lastminuteserver.purchase.dto.PaymentCreateDto;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PgAgencyService {
    public Optional<PaymentBridgeCreateDto> createPayment(PaymentCreateDto request) {
        // TODO: Pg사 연동 방식 정한 후 다시 작성
        return Optional.ofNullable(null);
    }

    public void deletePayment(Long pgId) {
    }
}
