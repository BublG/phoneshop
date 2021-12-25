package dto;

import com.es.core.model.order.OrderStatus;

public class OrderStatusForm {
    private long id;
    private OrderStatus status;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }
}
