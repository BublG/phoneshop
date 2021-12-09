package com.es.phoneshop.web.controller.pages;

import com.es.core.exceptions.OrderNotFoundException;
import com.es.core.model.order.Order;
import com.es.core.model.order.OrderDao;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.annotation.Resource;

@Controller
@RequestMapping(value = "/orderOverview")
public class OrderOverviewPageController {
    @Resource
    private OrderDao orderDao;

    private static final String ORDER_ATTRIBUTE = "order";

    @RequestMapping(method = RequestMethod.GET, path = "{secureId}")
    public String showPlacedOrder(Model model, @PathVariable String secureId) {
        model.addAttribute(ORDER_ATTRIBUTE, orderDao.getBySecureId(secureId).orElseThrow(OrderNotFoundException::new));
        return "orderOverview";
    }
}
