package com.es.phoneshop.web.controller.pages;

import dto.AddToCartForm;
import com.es.core.cart.Cart;
import com.es.core.cart.CartService;
import com.es.core.cart.HttpSessionCartService;
import com.es.core.exceptions.PhoneOutOfStockException;
import dto.UpdateCartResponse;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(value = "/cart")
public class CartPageController {
    private static final String WRONG_FORMAT_ERROR = "Wrong format";
    private static final String OUT_OF_STOCK_ERROR = "Quantity is out of stock";
    private static final String CART_PAGE = "cart";
    @Resource
    private CartService cartService;

    @RequestMapping(method = RequestMethod.GET)
    public String showCart(Model model, HttpSession session) {
        model.addAttribute(HttpSessionCartService.CART_SESSION_ATTRIBUTE, cartService.getCart(session));
        return CART_PAGE;
    }

    @RequestMapping(method = RequestMethod.PUT)
    @ResponseBody
    public UpdateCartResponse updateCart(@RequestBody List<AddToCartForm> addToCartForms, HttpSession session) {
        Cart cart = cartService.getCart(session);
        Map<Long, String> errors = new HashMap<>();
        for (AddToCartForm addToCartForm : addToCartForms) {
            long quantity = addToCartForm.getQuantity();
            if (quantity < 1) {
                errors.put(addToCartForm.getPhoneId(), WRONG_FORMAT_ERROR);
            } else {
                try {
                    cartService.update(cart, addToCartForm.getPhoneId(), quantity);
                } catch (PhoneOutOfStockException e) {
                    errors.put(addToCartForm.getPhoneId(), OUT_OF_STOCK_ERROR);
                }
            }
        }
        String updatedCartStatus = cart.getTotalQuantity() + " items " + cart.getTotalCost();
        return new UpdateCartResponse(errors, updatedCartStatus);
    }

    @RequestMapping(method = RequestMethod.DELETE)
    public void deleteCartItem(HttpSession session, @RequestParam long phoneId) {
        Cart cart = cartService.getCart(session);
        cartService.remove(cart, phoneId);
    }
}
