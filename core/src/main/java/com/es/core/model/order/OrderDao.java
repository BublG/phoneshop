package com.es.core.model.order;

import java.util.Optional;

public interface OrderDao {
    Optional<Order> get(Long id);
    Optional<Order> getBySecureId(String secureId);
    void save(Order order);
}
