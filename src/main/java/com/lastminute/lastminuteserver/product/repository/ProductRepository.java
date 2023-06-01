package com.lastminute.lastminuteserver.product.repository;


import com.lastminute.lastminuteserver.product.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {

}
