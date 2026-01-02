package net.app.orders.util;


import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.atomic.AtomicLong;

@Component
public class OrderIdGenerator {
    private final AtomicLong counter = new AtomicLong(1);

    public String generateOrderId(){
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("ddMMyyyy"));
        long id = counter.getAndIncrement();
        return "ORD-" + timestamp + "-" + String.format("%04d", id);
    }
}
