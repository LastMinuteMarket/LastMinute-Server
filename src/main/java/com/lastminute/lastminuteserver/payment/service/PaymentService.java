package com.lastminute.lastminuteserver.payment.service;

import com.lastminute.lastminuteserver.exceptions.RequestException;
import com.lastminute.lastminuteserver.exceptions.RequestExceptionCode;
import com.lastminute.lastminuteserver.payment.domain.Payment;
import com.lastminute.lastminuteserver.payment.dto.PaymentBridgeCreateDto;
import com.lastminute.lastminuteserver.payment.dto.PaymentCreateDto;
import com.lastminute.lastminuteserver.payment.dto.PaymentDto;
import com.lastminute.lastminuteserver.payment.repository.PaymentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PaymentService {

    private final PaymentRepository paymentRepository;
    private final PgAgencyAdapter pgAgencyAdapter;

    @Transactional
    public PaymentDto createPayment(PaymentCreateDto request){
        PaymentBridgeCreateDto bridge = pgAgencyAdapter.createPayment(request)
                .orElseThrow(() -> RequestException.of(RequestExceptionCode.PAYMENT_CREATION_FAILED));

        Payment payment = bridge.toEntity();
        paymentRepository.save(payment);
        return PaymentDto.of(payment);
    }

    @Transactional(readOnly = true)
    public PaymentDto readPayment(Long bidId){
        return null;
    }

    @Transactional(readOnly = true)
    public PaymentDto readAllPayment(Long userId){
        // TODO: USER가 판매자 혹은 구매자인 모든 BID 불러오기
        // TODO: 해당 BID들과 매핑된 모든 결제 내역 리턴
        return null;
    }

    @Transactional
    public void deletePayment(Long paymentId){
        Payment payment = findPaymentInternal(paymentId);
        paymentRepository.delete(payment);
    }

    private Payment findPaymentInternal(Long paymentId) {
        return paymentRepository.findById(paymentId)
                .orElseThrow(() -> RequestException.of(RequestExceptionCode.PAYMENT_NOT_FOUND));
    }
}
