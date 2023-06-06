package com.lastminute.lastminuteserver.purchase.repository;

import com.lastminute.lastminuteserver.purchase.domain.Purchase;
import com.lastminute.lastminuteserver.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface PurchaseRepository extends JpaRepository<Purchase, Long> {
    List<Purchase> findByUser(@Param("user") User user);

    @Query("select o from Purchase o where timestampdiff(day, o.acceptedAt, :now) = 7")
    List<Purchase> findAllByAcceptedAt7DaysAgo(@Param("now") LocalDateTime now);
}
