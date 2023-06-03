package com.lastminute.lastminuteserver.purchase.repository;

import com.lastminute.lastminuteserver.purchase.domain.Purchase;
import com.lastminute.lastminuteserver.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PurchaseRepository extends JpaRepository<Purchase, Long> {
    List<Purchase> findByUser(@Param("user") User user);
}
