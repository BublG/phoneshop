package com.es.phoneshop.web.controller.pages;

import com.es.core.cart.Cart;
import com.es.core.cart.CartService;
import com.es.core.model.order.Order;
import com.es.core.order.OrderService;
import com.es.core.order.OutOfStockException;
import dto.OrderForm;
import dto.PlaceOrderErrors;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping(value = "/order")
public class OrderPageController {
    @Resource
    private OrderService orderService;
    @Resource
    private CartService cartService;

    private static final String ORDER_PAGE = "order";
    private static final String REDIRECT_TO_ORDER_OVERVIEW_PAGE = "redirect:/orderOverview/%s";

    private static final String ORDER_ATTRIBUTE = "order";
    private static final String ORDER_FORM_ATTRIBUTE = "orderForm";
    private static final String ERRORS_PLACING_ORDER_ATTRIBUTE = "errors";

    private static final String CART_CHANGED_MESSAGE = "Your cart was changed because stock of some phones was decreased";
    private static final String CART_CHANGED_MESSAGE_ATTRIBUTE = "message";

    @RequestMapping(method = RequestMethod.GET)
    public String getOrder(ModelMap model, HttpSession session) {
        Cart cart = cartService.getCart(session);
        model.addAttribute(ORDER_ATTRIBUTE, orderService.createOrder(cart));
        return ORDER_PAGE;
    }

    @RequestMapping(method = RequestMethod.POST)
    public ModelAndView placeOrder(ModelMap model, HttpSession session, @Valid OrderForm orderForm, BindingResult bindingResult) {
        Cart cart = cartService.getCart(session);
        Order order = orderService.createOrder(cart);
        if (bindingResult.hasErrors()) {
            Map<String, String> errors = new HashMap<>();
            bindingResult.getFieldErrors()
                    .forEach(fieldError -> errors.put(fieldError.getField(), fieldError.getDefaultMessage()));
            model.addAttribute(ERRORS_PLACING_ORDER_ATTRIBUTE, new PlaceOrderErrors(errors));
            model.addAttribute(ORDER_ATTRIBUTE, order);
            model.addAttribute(ORDER_FORM_ATTRIBUTE, orderForm);
            return new ModelAndView(ORDER_PAGE, model);
        } else {
            try {
                setOrderForm(order, orderForm);
                orderService.placeOrder(cart, order);
                cartService.clearCart(session);
                return new ModelAndView(String.format(REDIRECT_TO_ORDER_OVERVIEW_PAGE, order.getSecureId()));
            } catch (OutOfStockException e) {
                model.addAttribute(CART_CHANGED_MESSAGE_ATTRIBUTE, CART_CHANGED_MESSAGE);
                model.addAttribute(ORDER_ATTRIBUTE, orderService.createOrder(cart));
                return new ModelAndView(ORDER_PAGE, model);
            }
        }
    }

    private void setOrderForm(Order order, OrderForm orderForm) {
        order.setFirstName(orderForm.getFirstName());
        order.setLastName(orderForm.getLastName());
        order.setDeliveryAddress(orderForm.getDeliveryAddress());
        order.setContactPhoneNo(orderForm.getContactPhoneNo());
        order.setAdditionalInformation(orderForm.getAdditionalInformation());
    }
}
