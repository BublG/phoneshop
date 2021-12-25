package com.es.phoneshop.web.controller.pages.admin;

import com.es.core.exceptions.OrderNotFoundException;
import com.es.core.model.order.Order;
import com.es.core.model.order.OrderDao;
import com.es.core.model.order.OrderStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.annotation.Resource;

@Controller
@RequestMapping("/admin/orderDetails")
public class OrderDetailsPageController {
    private static final String ORDER_ATTRIBUTE = "order";
    private static final String ORDER_DETAILS_PAGE = "orderDetails";
    @Resource
    private OrderDao orderDao;

    @RequestMapping(method = RequestMethod.GET, path = "/{id}")
    public String showOrderDetails(Model model, @PathVariable long id) {
        Order order = orderDao.get(id).orElseThrow(OrderNotFoundException::new);
        model.addAttribute(ORDER_ATTRIBUTE, order);
        return ORDER_DETAILS_PAGE;
    }

    @RequestMapping(method = RequestMethod.POST, path = "/{id}")
    public String changeOrderStatus(Model model, @PathVariable long id, OrderStatus status) {
        orderDao.updateStatus(id, status);
        Order order = orderDao.get(id).orElseThrow(OrderNotFoundException::new);
        model.addAttribute(ORDER_ATTRIBUTE, order);
        return ORDER_DETAILS_PAGE;
    }
}
