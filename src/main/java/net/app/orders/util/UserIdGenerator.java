package net.app.orders.util;

import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.atomic.AtomicLong;

@Component
public class UserIdGenerator {
    private final AtomicLong counter = new AtomicLong(1);

    public String generateUserId(){
        long id = counter.getAndIncrement();
        return "USER_" + String.format("_%04d_", id);
    }

}
