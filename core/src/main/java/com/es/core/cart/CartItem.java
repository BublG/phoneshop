package com.es.core.cart;

import com.es.core.model.phone.Phone;

public class CartItem {
    private final Phone phone;
    private long quantity;

    public CartItem(Phone phone, long quantity) {
        this.phone = phone;
        this.quantity = quantity;
    }

    public Phone getPhone() {
        return phone;
    }

    public long getQuantity() {
        return quantity;
    }

    public void setQuantity(long quantity) {
        this.quantity = quantity;
    }
}
