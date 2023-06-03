package com.lastminute.lastminuteserver.purchase.service;

import com.lastminute.lastminuteserver.exceptions.RequestException;
import com.lastminute.lastminuteserver.purchase.repository.PurchaseRepository;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class PurchaseServiceTest {

    @Mock
    private PurchaseRepository purchaseRepository;

    @InjectMocks
    private PurchaseService paymentService;

    @Nested
    @DisplayName("결제 생성")
    class CreatePayment{

        String paymentMethod = "CARD";
        Integer installmentPeriod = 1;
        Integer originalPrice = 1000;

        PaymentCreateDto request = PaymentCreateDto.builder()
                .paymentMethod(paymentMethod)
                .installmentPeriod(installmentPeriod)
                .originalPrice(originalPrice)
                .build();

        @Test
        @DisplayName("Pg사로부터 결제 성공 응답이 왔을 때 성공")
        public void successCreatePayment(){
            // given
            PaymentBridgeCreateDto bridge = PaymentBridgeCreateDto.builder()
                    .pgId(1L)
                    .paymentMethod(paymentMethod)
                    .installmentPeriod(installmentPeriod)
                    .originalPrice(originalPrice)
                    .fee(1000)
                    .finalPrice(2000)
                    .build();

            given(pgAgencyAdapter.createPayment(any()))
                    .willReturn(Optional.ofNullable(bridge));

            // when
            PaymentDto readPaymentResponseDto = paymentService.createPayment(request);

            // then
            assertThat(readPaymentResponseDto.paymentMethod()).isEqualTo(paymentMethod);
            assertThat(readPaymentResponseDto.installmentPeriod()).isEqualTo(installmentPeriod);
        }

        @Test
        @DisplayName("Pg사로부터 결제 실패 오류코드가 왔을 때 매칭되는 에러 메시지 전달")
        public void failCreatePayment(){
            // given
            given(pgAgencyAdapter.createPayment(any()))
                    .willReturn(Optional.ofNullable(null));

            // when, then
            assertThatThrownBy(() -> paymentService.createPayment(request))
                    .isInstanceOf(RequestException.class);
        }
    }

    @Disabled
    @Nested
    @DisplayName("결제 조회")
    class ReadPayment{

        @Test
        @DisplayName("이용 중인 사용자가 결제한 특정 결제 내역 리턴")
        public void readPayment(){

        }

        @Test
        @DisplayName("이용 중인 사용자가 결제한 모든 결제 내역 리턴")
        public void readPaymentAll(){

        }
    }

    @Disabled
    @Nested
    @DisplayName("결제 취소")
    class DeletePayment{

        @Test
        @DisplayName("이용 중인 사용자가 결제한 결제가 취소 가능할 때 취소 성공")
        public void successDeletePayment(){

        }

        @Test
        @DisplayName("이용 중인 사용자가 결제한 결제가 취소 불가능할 때 취소 실패")
        public void failDeletePayment(){

        }
    }
}