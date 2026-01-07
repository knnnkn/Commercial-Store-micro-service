package net.app.orders.controller;


import jakarta.validation.Valid;
import net.app.orders.dto.CreateOrderRequest;
import net.app.orders.model.Order;
import net.app.orders.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")

public class OrderController {

	@Autowired
	private OrderService orderService;

	@PostMapping
	public ResponseEntity<Order> createOrder(@Valid @RequestBody CreateOrderRequest request) {
		Order order = orderService.createOrder(request);
		return new ResponseEntity<>(order, HttpStatus.CREATED);
	}

	@GetMapping("/{orderId}")
	public ResponseEntity<Order> getOrderById(@PathVariable String orderId) {
		Order order = orderService.findByOrderId(orderId);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@GetMapping("/user/{user}")
	public ResponseEntity<?> getOrderById(@PathVariable Long userId) {
		List<Order> orders = orderService.findOrderByUserId(userId);
		return new ResponseEntity<>(HttpStatus.OK);
	}




}
