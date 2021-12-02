package com.es.phoneshop.web.controller.pages;

import com.es.core.cart.Cart;
import com.es.core.cart.CartService;
import com.es.core.cart.HttpSessionCartService;
import com.es.core.exceptions.PhoneOutOfStockException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
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
    private static final String ERRORS_MAP = "errors";

    @RequestMapping(method = RequestMethod.GET)
    public String showCart(Model model, HttpSession session) {
        model.addAttribute(HttpSessionCartService.CART_SESSION_ATTRIBUTE, cartService.getCart(session));
        return "cart";
    }

    @RequestMapping(method = RequestMethod.POST)
    public ModelAndView updateCart(ModelMap model, HttpSession session, @RequestParam List<Long> phoneId,
                             @RequestParam List<String> quantity) {
        Cart cart = cartService.getCart(session);
        Map<Long, String> errors = new HashMap<>();
        for (int i = 0; i < phoneId.size(); i++) {
            try {
                long q = parseQuantity(quantity.get(i));
                cartService.update(cart, phoneId.get(i), q);
            } catch (NumberFormatException e) {
                errors.put(phoneId.get(i), WRONG_FORMAT_ERROR);
            } catch (PhoneOutOfStockException e) {
                errors.put(phoneId.get(i), OUT_OF_STOCK_ERROR);
            }
        }
        if (errors.isEmpty()) {
            return new ModelAndView("redirect:cart");
        } else {
            model.addAttribute(ERRORS_MAP, errors);
            return new ModelAndView("cart", model);
        }
    }

    @RequestMapping(method = RequestMethod.DELETE)
    public void deleteCartItem(HttpSession session, @RequestParam long phoneId) {
        Cart cart = cartService.getCart(session);
        cartService.remove(cart, phoneId);
    }

    private long parseQuantity(String quantity) throws NumberFormatException {
        long q = Long.parseLong(quantity);
        if (q < 1) {
            throw new NumberFormatException();
        }
        return q;
    }
}
