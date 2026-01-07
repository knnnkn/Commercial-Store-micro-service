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
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.ReactiveRedisOperations;


import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderService {

	private final Logger logger = LoggerFactory.getLogger(OrderService.class);

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

	@Autowired
	private RedisTemplate<String, Object> redisTemplate;

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
	    logger.info("creating order for user: {}", request.getPhoneNumber());

	    String orderId = orderIdGenerator.generateOrderId();

	    Order order= new Order();
	    order.setOrderId(orderId);
	    order.setUserId(savedUser);

	    Order savedOrder = orderRepository.save(order);

	    List<OrderItem> items = request.getItems().stream().map(itemReq -> {
	        OrderItem item = new OrderItem();
	        item.setOrder(savedOrder);
	        item.setProductName(itemReq.getProductName());
	        item.setQuantity(itemReq.getQuantity());
	        return item;
        }).collect(Collectors.toList());
	    orderItemRepository.saveAll(items);

	    logger.info("order created with ID: {}", orderId);
	    redisTemplate.opsForValue().set("Order:" + orderId, savedOrder);
    return savedOrder;
    }


	public Order findByOrderId(String orderId){
		 Order cached = (Order) redisTemplate.opsForValue().get("order:" + orderId);
		 if(cached != null){
			 return cached;
		 }

		 Order order = orderRepository.findByOrderNumber(orderId);
		 if(order != null){
			 redisTemplate.opsForValue().set("order:" + orderId, order);
		 }
		 return order;
	}

	public List<Order> findOrderByUserId(Long userId){
		return orderRepository.findByUser_Id(userId);
	}






}
