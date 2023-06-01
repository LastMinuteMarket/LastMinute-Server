package com.lastminute.lastminuteserver.payment.repository;

import com.lastminute.lastminuteserver.payment.domain.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {
}
