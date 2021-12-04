package com.es.phoneshop.web.controller.pages;

import com.es.core.cart.CartService;
import com.es.core.cart.HttpSessionCartService;
import com.es.core.exceptions.PhoneNotFoundException;
import com.es.core.model.phone.Phone;
import com.es.core.model.phone.PhoneDao;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

@Controller
@RequestMapping(value = "/productDetails")
public class ProductDetailsPageController {
    @Resource
    private PhoneDao phoneDao;
    @Resource
    private CartService cartService;

    private static final String PHONE_ATTRIBUTE = "phone";
    private static final String PRODUCT_DETAILS_PAGE = "productDetails";

    @RequestMapping(method = RequestMethod.GET, path = "/{id}")
    public String showProductDetails(Model model, @PathVariable long id, HttpSession session) {
        Phone phone = phoneDao.get(id).orElseThrow(PhoneNotFoundException::new);
        model.addAttribute(PHONE_ATTRIBUTE, phone);
        model.addAttribute(HttpSessionCartService.CART_SESSION_ATTRIBUTE, cartService.getCart(session));
        return PRODUCT_DETAILS_PAGE;
    }
}
