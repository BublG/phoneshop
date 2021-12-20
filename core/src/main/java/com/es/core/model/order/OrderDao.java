package com.es.core.model.order;

import java.util.List;
import java.util.Optional;

public interface OrderDao {
    Optional<Order> get(Long id);
    Optional<Order> getBySecureId(String secureId);
    void save(Order order);
    List<Order> findAll(int offset, int limit);
    long getOrdersQuantity();
    void updateStatus(long id, OrderStatus status);
}
