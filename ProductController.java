package com.example.demo;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("ProductOrder")
public class ProductController {
	@Autowired
	private ProductOrderRepository productOrderRepository;
	
	@GetMapping("PlaceOrder")
	public String placeOder(@RequestBody ProdOrdReq prodOrdReq)
	{	ProductOrder po=new ProductOrder();
		po.setQty(prodOrdReq.getQty());
		po.setPricePerUnit(prodOrdReq.getPricePerUnit());
		po.setOrderStatus("Processing");
		productOrderRepository.save(po);
		return "Order placed successfully";
	}
	@GetMapping("fetchAllProductOrders")
	public ResponseEntity<List<ProductOrder>> fetchAllProductOrders()
	{	return ResponseEntity.ok(productOrderRepository.findAll());
	}
	@GetMapping("fetchByOrderId")
	public ResponseEntity<ProductOrder> fetchById(@RequestParam Long id)
	{	Optional<ProductOrder> optprodord = productOrderRepository.findById(id);
		if(optprodord.isPresent())
			return ResponseEntity.ok(optprodord.get());
		else
			return ResponseEntity.notFound().build();
	}
	@GetMapping("Update")
	public String updateStatus(@RequestBody UpdateValues updateValues)
	{	ProductOrder po = productOrderRepository.findById(updateValues.getId()).get();
		if(po.getOrderStatus().equals("DELIVERED"))
			return "Product already delivered!";
		if(po.getOrderStatus().equals("CANCELLED"))
			return "Product delivery was cancelled!";
		if(updateValues.getStatus().equals("DELIVERED"))
			po.setOrderStatus("DELIVERED");
		if(updateValues.getStatus().equals("CANCELLED"))
			po.setOrderStatus("CANCELLED");
		productOrderRepository.save(po);
		return "Update successful!";
	}
}
