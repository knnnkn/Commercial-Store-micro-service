package net.app.orders.service;

import net.app.orders.dto.CreateOrderRequest;
import net.app.orders.model.Order;
import net.app.orders.model.OrderItem;
import net.app.orders.model.User;
import net.app.orders.repository.OrderItemRepository;
import net.app.orders.repository.OrderRepository;
import net.app.orders.repository.UserRepository;
import net.app.orders.util.OrderIdGenerator;
import net.app.orders.util.UserIdGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderItemRepository orderItemRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private OrderIdGenerator orderIdGenerator;

    @Autowired
    private UserIdGenerator userIdGenerator;

    @Transactional
    public Order createOrder(CreateOrderRequest request){
        String userIdGeneration = userIdGenerator.generateUserId();
        User user = new User();
//        user.setId(request.getUserId());
        user.setFirstName(request.getFirstName());
        user.setSecondName(request.getSecondName());
        user.setPhoneNumber(request.getPhoneNumber());
        user.setEmail(request.getEmail());

        User savedUser = userRepository.save(user);

        String orderNumber = orderIdGenerator.generateOrderId();

        Order order= new Order();
        order.setOrderId(orderNumber);
        order.setUser(savedUser);

        Order savedOrder = orderRepository.save(order);

        List<OrderItem> items = request.getItems().stream().map(itemReq -> {
            OrderItem item = new OrderItem();
            item.setOrder(savedOrder);
            item.setProductName(itemReq.getProductName());
            item.setQuantity(itemReq.getQuantity());
            return item;
        }).collect(Collectors.toList());




    return savedOrder;
    }
}
