package com.es.core.model.order;

import com.es.core.exceptions.PhoneNotFoundException;
import com.es.core.model.phone.PhoneDao;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.sql.PreparedStatement;
import java.util.List;
import java.util.Optional;

@Component
public class JdbcOrderDao implements OrderDao {
    @Resource
    private JdbcTemplate jdbcTemplate;
    @Resource
    private PhoneDao phoneDao;

    private static final String INSERT_ORDER_QUERY = "insert into orders (secureId, subtotal, deliveryPrice, totalPrice," +
            "firstName, lastName, deliveryAddress, contactPhoneNo, additionalInformation, created, status) " +
            "values(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
    private static final String SELECT_ORDER_BY_SECURE_ID_QUERY = "select * from orders where secureId = ?";
    private static final String INSERT_ORDER_ITEM_QUERY = "insert into orderItems (phoneId, orderId, quantity) values (?, ?, ?)";
    private static final String SELECT_ORDER_ITEMS_QUERY = "select * from orderItems where orderId = ?";
    private static final String SELECT_ALL_ORDERS_QUERY = "select * from orders limit ? offset ?";
    private static final String SELECT_ORDERS_QUANTITY_QUERY = "select count(*) from orders";
    private static final String SELECT_ORDER_BY_ID_QUERY = "select * from orders where id = ?";
    private static final String UPDATE_STATUS_QUERY = "update orders set status = ? where id = ?";

    @Override
    public Optional<Order> get(Long id) {
        Optional<Order> order = Optional.ofNullable(jdbcTemplate.queryForObject(SELECT_ORDER_BY_ID_QUERY, new Object[]{id},
                BeanPropertyRowMapper.newInstance(Order.class)));
        order.ifPresent(this::setOrderItems);
        return order;
    }

    @Override
    public Optional<Order> getBySecureId(String secureId) {
        Optional<Order> order = Optional.ofNullable(jdbcTemplate
                .queryForObject(SELECT_ORDER_BY_SECURE_ID_QUERY, new Object[]{secureId},
                        BeanPropertyRowMapper.newInstance(Order.class)));
        order.ifPresent(this::setOrderItems);
        return order;
    }

    @Override
    public void save(Order order) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement preparedStatement = connection
                    .prepareStatement(INSERT_ORDER_QUERY);
            preparedStatement.setString(1, order.getSecureId());
            preparedStatement.setObject(2, order.getSubtotal());
            preparedStatement.setObject(3, order.getDeliveryPrice());
            preparedStatement.setObject(4, order.getTotalPrice());
            preparedStatement.setString(5, order.getFirstName());
            preparedStatement.setString(6, order.getLastName());
            preparedStatement.setString(7, order.getDeliveryAddress());
            preparedStatement.setString(8, order.getContactPhoneNo());
            preparedStatement.setString(9, order.getAdditionalInformation());
            preparedStatement.setObject(10, order.getCreated());
            preparedStatement.setString(11, order.getStatus().toString());
            return preparedStatement;
        }, keyHolder);
        order.setId(keyHolder.getKey().longValue());
        jdbcTemplate.batchUpdate(INSERT_ORDER_ITEM_QUERY, order.getOrderItems(), order.getOrderItems().size(),
                ((preparedStatement, orderItem) -> {
                    preparedStatement.setLong(1, orderItem.getPhone().getId());
                    preparedStatement.setLong(2, orderItem.getOrder().getId());
                    preparedStatement.setLong(3, orderItem.getQuantity());
                }));
        order.getOrderItems()
                .forEach(orderItem -> phoneDao.decreaseStock(orderItem.getPhone().getId(), orderItem.getQuantity()));
    }

    @Override
    public List<Order> findAll(int offset, int limit) {
        List<Order> orders = jdbcTemplate.query(SELECT_ALL_ORDERS_QUERY, new Object[]{limit, offset},
                new BeanPropertyRowMapper<>(Order.class));
        orders.forEach(this::setOrderItems);
        return orders;
    }

    @Override
    public long getOrdersQuantity() {
        return jdbcTemplate.queryForObject(SELECT_ORDERS_QUANTITY_QUERY, Long.class);
    }

    @Override
    public void updateStatus(long id, OrderStatus status) {
        jdbcTemplate.update(UPDATE_STATUS_QUERY, status.toString(), id);
    }

    private void setOrderItems(Order order) {
        List<OrderItem> orderItems = jdbcTemplate.query(SELECT_ORDER_ITEMS_QUERY, new Object[]{order.getId()},
                (resultSet, i) -> {
                    OrderItem orderItem = new OrderItem();
                    orderItem.setId(resultSet.getLong(1));
                    orderItem.setPhone(phoneDao.get(resultSet.getLong(2))
                            .orElseThrow(PhoneNotFoundException::new));
                    orderItem.setOrder(order);
                    orderItem.setQuantity(resultSet.getLong(4));
                    return orderItem;
                });
        order.setOrderItems(orderItems);
    }
}
