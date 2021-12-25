package com.es.phoneshop.web.controller.pages.admin;

import com.es.core.model.order.OrderDao;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import pagination.Pagination;

import javax.annotation.Resource;

@Controller
@RequestMapping(value = "/admin/orders")
public class OrdersPageController {
    private static final int ORDERS_COUNT_BY_PAGE = 10;
    private static final String ORDERS_ATTRIBUTE = "orders";
    private static final String MAX_PAGE_ATTRIBUTE = "maxPage";
    private static final String PAGE_NUMBERS_ATTRIBUTE = "pageNumbers";
    private static final String ORDER_LIST_PAGE = "orderList";
    private static final int MAX_DISPLAYED_PAGES = 9;
    @Resource
    private OrderDao orderDao;

    @RequestMapping(method = RequestMethod.GET, path = "/{page}")
    public String showOrders(Model model, @PathVariable int page) {
        int offset = ORDERS_COUNT_BY_PAGE * (page - 1);
        long ordersQuantity = orderDao.getOrdersQuantity();
        int maxPage = (int) (ordersQuantity % ORDERS_COUNT_BY_PAGE == 0 ? ordersQuantity / ORDERS_COUNT_BY_PAGE :
                ordersQuantity / ORDERS_COUNT_BY_PAGE + 1);
        model.addAttribute(MAX_PAGE_ATTRIBUTE, maxPage);
        model.addAttribute(PAGE_NUMBERS_ATTRIBUTE, Pagination.getPageNumbers(page, maxPage, MAX_DISPLAYED_PAGES));
        model.addAttribute(ORDERS_ATTRIBUTE, orderDao.findAll(offset, ORDERS_COUNT_BY_PAGE));
        return ORDER_LIST_PAGE;
    }
}
