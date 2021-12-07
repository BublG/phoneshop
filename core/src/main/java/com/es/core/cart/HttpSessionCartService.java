package com.es.core.cart;

import com.es.core.exceptions.PhoneNotFoundException;
import com.es.core.exceptions.PhoneOutOfStockException;
import com.es.core.model.phone.Phone;
import com.es.core.model.phone.PhoneDao;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.util.Optional;

@Service
public class HttpSessionCartService implements CartService {
    public static final String CART_SESSION_ATTRIBUTE = "cart";

    @Resource
    private PhoneDao phoneDao;

    public void setPhoneDao(PhoneDao phoneDao) {
        this.phoneDao = phoneDao;
    }

    @Override
    public Cart getCart(HttpSession session) {
        synchronized (session) {
            Cart cart = (Cart) session.getAttribute(CART_SESSION_ATTRIBUTE);
            if (cart == null) {
                session.setAttribute(CART_SESSION_ATTRIBUTE, cart = new Cart());
            }
            return cart;
        }
    }

    @Override
    public void addPhone(Cart cart, Long phoneId, Long quantity) {
        synchronized (cart) {
            Phone phone = phoneDao.get(phoneId).orElseThrow(PhoneNotFoundException::new);
            Optional<CartItem> optionalCartItem = cart.getItems().stream()
                    .filter(item -> item.getPhone().getId().equals(phoneId))
                    .findAny();
            long updatedQuantity = quantity + optionalCartItem
                    .map(CartItem::getQuantity).orElse(0L);
            if (updatedQuantity > phoneDao.getInStockQuantity(phoneId)) {
                throw new PhoneOutOfStockException();
            }
            if (optionalCartItem.isPresent()) {
                optionalCartItem.get().setQuantity(updatedQuantity);
            } else {
                cart.getItems().add(new CartItem(phone, quantity));
            }
            recalculateCart(cart);
        }
    }

    @Override
    public void update(Cart cart, Long phoneId, Long quantity) {
        synchronized (cart) {
            CartItem cartItem = cart.getItems().stream()
                    .filter(item -> phoneId.equals(item.getPhone().getId()))
                    .findAny().get();
            if (quantity > phoneDao.getInStockQuantity(phoneId)) {
                throw new PhoneOutOfStockException();
            }
            cartItem.setQuantity(quantity);
            recalculateCart(cart);
        }
    }

    @Override
    public void remove(Cart cart, Long phoneId) {
        synchronized (cart) {
            cart.getItems().removeIf(cartItem -> cartItem.getPhone().getId().equals(phoneId));
            recalculateCart(cart);
        }
    }

    private void recalculateCart(Cart cart) {
        cart.setTotalQuantity(cart.getItems().stream()
                .map(CartItem::getQuantity)
                .reduce(0L, Long::sum));
        cart.setTotalCost(cart.getItems().stream()
                .map(cartItem -> cartItem.getPhone().getPrice()
                        .multiply(new BigDecimal(cartItem.getQuantity())))
                .reduce(new BigDecimal(0), BigDecimal::add));
    }
}
