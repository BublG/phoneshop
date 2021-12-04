package com.es.phoneshop.web.controller.pages;

import com.es.core.cart.*;
import com.es.core.exceptions.PhoneOutOfStockException;
import addToCartForm.AddToCartForm;
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
    @Resource
    private CartService cartService;

    private static final String WRONG_FORMAT_ERROR = "Wrong format";
    private static final String OUT_OF_STOCK_ERROR = "Quantity is out of stock";
    private static final String CART_PAGE = "cart";

    @RequestMapping(method = RequestMethod.GET)
    public String showCart(Model model, HttpSession session) {
        model.addAttribute(HttpSessionCartService.CART_SESSION_ATTRIBUTE, cartService.getCart(session));
        return CART_PAGE;
    }

    @RequestMapping(method = RequestMethod.PUT)
    @ResponseBody
    public Map<Long, String> updateCart(@RequestBody List<AddToCartForm> addToCartForms, HttpSession session) {
        Cart cart = cartService.getCart(session);
        Map<Long, String> errors = new HashMap<>();
        for (AddToCartForm addToCartForm: addToCartForms) {
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
        return errors;
    }

    @RequestMapping(method = RequestMethod.DELETE)
    public void deleteCartItem(HttpSession session, @RequestParam long phoneId) {
        Cart cart = cartService.getCart(session);
        cartService.remove(cart, phoneId);
    }
}
