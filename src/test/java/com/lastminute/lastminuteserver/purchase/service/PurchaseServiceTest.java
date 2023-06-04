package com.lastminute.lastminuteserver.purchase.service;

import com.lastminute.lastminuteserver.product.repository.ProductRepository;
import com.lastminute.lastminuteserver.purchase.dto.PurchaseCreateDto;
import com.lastminute.lastminuteserver.purchase.repository.PurchaseRepository;
import com.lastminute.lastminuteserver.user.repository.UserRepository;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class PurchaseServiceTest {

    @Mock
    private PurchaseRepository purchaseRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private ProductRepository productRepository;

    @Mock
    private PgAgency pgAgency;

    @InjectMocks
    private PurchaseService purchaseService;

    @Disabled
    @Nested
    @DisplayName("결제 생성")
    class CreatePurchase{

        PurchaseCreateDto purchaseCreateDto = PurchaseCreateDto.builder()
                .paymentMethod("CARD")
                .installmentPeriod(1)
                .originalPrice(1000)
                .build();


        @Test
        @DisplayName("Pg사 결제 시 성공해 결제를 성공적으로 수행한다")
        public void successCreatePayment(){
        }

        @Test
        @DisplayName("Pg사 결제 시 실패해 예외가 리턴된다")
        public void failCreatePayment(){
        }
    }

    @Disabled
    @Nested
    @DisplayName("결제 조회")
    class ReadPurchase{

        @Test
        @DisplayName("사용자가 결제한 특정 결제 내역을 리턴한다")
        public void getPurchase(){

        }

        @Test
        @DisplayName("사용자가 결제한 모든 결제 내역을 리턴한다")
        public void readPurchase(){

        }
    }

    @Disabled
    @Nested
    @DisplayName("결제 취소")
    class DeletePayment{

        @Test
        @DisplayName("사용자가 결제한 결제가 취소 가능할 때 취소에 성공한다")
        public void successDeletePayment(){

        }

        @Test
        @DisplayName("사용자가 결제한 결제가 취소 불가능할 때 취소에 실패한다")
        public void failDeletePayment(){

        }
    }
}