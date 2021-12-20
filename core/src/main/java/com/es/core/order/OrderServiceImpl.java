package com.es.core.order;

import com.es.core.cart.Cart;
import com.es.core.cart.CartItem;
import com.es.core.cart.CartService;
import com.es.core.model.order.Order;
import com.es.core.model.order.OrderDao;
import com.es.core.model.order.OrderItem;
import com.es.core.model.order.OrderStatus;
import com.es.core.model.phone.PhoneDao;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Date;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@PropertySource("classpath:application.properties")
public class OrderServiceImpl implements OrderService {
    @Resource
    private OrderDao orderDao;
    @Resource
    private PhoneDao phoneDao;
    @Resource
    private CartService cartService;

    @Value( "${delivery.price}" )
    private BigDecimal deliveryPrice;

    @Override
    public Order createOrder(Cart cart) {
        synchronized (cart) {
            Order order = new Order();
            order.setOrderItems(cart.getItems().stream()
                    .map(cartItem -> new OrderItem(cartItem.getPhone(), order, cartItem.getQuantity()))
                    .collect(Collectors.toList()));
            order.setSubtotal(cart.getTotalCost());
            order.setDeliveryPrice(deliveryPrice);
            order.setTotalPrice(order.getSubtotal().add(order.getDeliveryPrice()));
            return order;
        }
    }

    @Override
    @Transactional
    public void placeOrder(Cart cart, Order order) throws OutOfStockException {
        synchronized (cart) {
            long prevTotalQuantity = cart.getTotalQuantity();
            cart.getItems().forEach(cartItem -> {
                long inStockQuantity = phoneDao.getInStockQuantity(cartItem.getPhone().getId());
                if (inStockQuantity < cartItem.getQuantity()) {
                    cartService.update(cart, cartItem.getPhone().getId(), inStockQuantity);
                }
            });
            cart.getItems().removeIf(cartItem -> cartItem.getQuantity() == 0);
            long newTotalQuantity = cart.getTotalQuantity();
            if (newTotalQuantity != prevTotalQuantity) {
                throw new OutOfStockException();
            }
            order.setSecureId(UUID.randomUUID().toString());
            order.setStatus(OrderStatus.NEW);
            order.setCreated(new Date());
            orderDao.save(order);
        }
    }
}
