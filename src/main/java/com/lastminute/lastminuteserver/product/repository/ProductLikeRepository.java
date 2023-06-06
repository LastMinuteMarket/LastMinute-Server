package com.lastminute.lastminuteserver.product.repository;

import com.lastminute.lastminuteserver.product.domain.Product;
import com.lastminute.lastminuteserver.product.domain.ProductLike;
import com.lastminute.lastminuteserver.product.domain.ProductLikeId;
import com.lastminute.lastminuteserver.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProductLikeRepository extends JpaRepository<ProductLike, ProductLikeId> {
    Optional<ProductLike> findByUserAndProduct(User user, Product product);
}
