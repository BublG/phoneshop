package com.es.core.order;

import com.es.core.cart.Cart;
import com.es.core.cart.CartItem;
import com.es.core.cart.CartService;
import com.es.core.model.order.Order;
import com.es.core.model.order.OrderDao;
import com.es.core.model.order.OrderItem;
import com.es.core.model.order.OrderStatus;
import com.es.core.model.phone.PhoneDao;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.io.FileInputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {
    @Resource
    private OrderDao orderDao;
    @Resource
    private PhoneDao phoneDao;
    @Resource
    private CartService cartService;

    @Override
    public Order createOrder(Cart cart) {
        synchronized (cart) {
            Order order = new Order();
            order.setOrderItems(cart.getItems().stream()
                    .map(cartItem -> new OrderItem(cartItem.getPhone(), order, cartItem.getQuantity()))
                    .collect(Collectors.toList()));
            order.setSubtotal(cart.getTotalCost());
            order.setDeliveryPrice(getDeliveryPrice());
            order.setTotalPrice(order.getSubtotal().add(order.getDeliveryPrice()));
            return order;
        }
    }

    @Override
    @Transactional
    public void placeOrder(Cart cart, Order order) throws OutOfStockException {
        synchronized (cart) {
            boolean cartChanged = false;
            for (int i = 0; i < cart.getItems().size(); i++) {
                CartItem cartItem = cart.getItems().get(i);
                long inStockQuantity = phoneDao.getInStockQuantity(cartItem.getPhone().getId());
                if (cartItem.getQuantity() > inStockQuantity) {
                    cartChanged = true;
                    if (inStockQuantity == 0) {
                        cartService.remove(cart, cartItem.getPhone().getId());
                        i--;
                    } else {
                        cartService.update(cart, cartItem.getPhone().getId(), inStockQuantity);
                    }
                }
            }
            if (cartChanged) {
                throw new OutOfStockException();
            }
            order.setSecureId(UUID.randomUUID().toString());
            order.setStatus(OrderStatus.NEW);
            orderDao.save(order);
        }
    }

    private BigDecimal getDeliveryPrice() {
        String corePath = Objects.requireNonNull(Thread.currentThread().getContextClassLoader()
                .getResource("")).getPath();
        String coreConfigPath = corePath + "application.properties";
        Properties properties = new Properties();
        try {
            properties.load(new FileInputStream(coreConfigPath));
        } catch (IOException e) {
            throw new RuntimeException();
        }
        return new BigDecimal(properties.getProperty("delivery.price"));
    }
}
