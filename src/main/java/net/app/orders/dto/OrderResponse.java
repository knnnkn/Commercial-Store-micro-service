package net.app.orders.dto;


import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;


@Data
public class OrderResponse {
	private Long id;
	private String orderId;
	private String status;
	private LocalDateTime createdAt;
	private List<OrderItemResponse> items;

		@Data
		public static class OrderItemResponse {
			private String productName;
			private Integer quantity;
			private Double price;
		}
}
