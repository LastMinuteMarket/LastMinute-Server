package com.lastminute.lastminuteserver.purchase.repository;

import com.lastminute.lastminuteserver.purchase.domain.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {
}
