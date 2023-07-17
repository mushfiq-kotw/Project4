package com.example.demo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

public interface ProductOrderRepository extends CrudRepository<ProductOrder, Long>{
	Optional<ProductOrder> findById(long id);
	List<ProductOrder> findAll();

}
