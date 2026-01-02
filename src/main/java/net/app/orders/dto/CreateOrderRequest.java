package net.app.orders.dto;

import lombok.Data;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.List;

@Data
public class CreateOrderRequest {

    @NotNull(message = "User id is required")
    private Long userId;

    @NotNull(message = "Set your first name")
    private String firstName;

    @NotNull(message = "Set your second name")
    private String secondName;

    @NotNull(message = "Set your email address")
    private String email;

    @NotNull(message = "Set your phone number")
    private String phoneNumber;

    @NotEmpty(message = "Order cannot be empty")
    @Valid
    private List<OrderItemRequest> items;

    @Data
    public static class OrderItemRequest {
        @NotNull(message = "Product name is required")
        private String productName;

        @NotNull(message = "Quantity is required")
        private Integer quantity;

        @NotNull
        private Double price;
    }
}
