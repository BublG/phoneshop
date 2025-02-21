package com.es.phoneshop.web.controller.pages;

import com.es.core.cart.CartService;
import com.es.core.cart.HttpSessionCartService;
import com.es.core.model.phone.PhoneDao;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import pagination.Pagination;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.Map;

@Controller
@RequestMapping(value = "/productList")
public class ProductListPageController {
    private static final int PHONES_COUNT_BY_PAGE = 10;
    private static final int MAX_DISPLAYED_PAGES = 9;
    private static final String PHONES_ATTRIBUTE = "phones";
    private static final String MAX_PAGE_ATTRIBUTE = "maxPage";
    private static final String PAGE_NUMBERS_ATTRIBUTE = "pageNumbers";
    private static final String QUERY_PARAM = "query";
    private static final String SORT_FIELD_PARAM = "sortField";
    private static final String SORT_ORDER_PARAM = "sortOrder";
    private static final String PRODUCT_LIST_PAGE = "productList";
    @Resource
    private PhoneDao phoneDao;
    @Resource
    private CartService cartService;

    @RequestMapping(method = RequestMethod.GET, path = "/{page}")
    public String showProductList(Model model, @PathVariable int page,
                                  @RequestParam(required = false) Map<String, String> params,
                                  HttpSession session) {
        int offset = PHONES_COUNT_BY_PAGE * (page - 1);
        long inStockPhonesQuantity = phoneDao.getInStockPhonesQuantity(params.get(QUERY_PARAM));
        int maxPage = (int) (inStockPhonesQuantity % PHONES_COUNT_BY_PAGE == 0 ? inStockPhonesQuantity / PHONES_COUNT_BY_PAGE :
                inStockPhonesQuantity / PHONES_COUNT_BY_PAGE + 1);
        model.addAttribute(PHONES_ATTRIBUTE, phoneDao.findPhonesInStock(params.get(QUERY_PARAM),
                params.get(SORT_FIELD_PARAM), params.get(SORT_ORDER_PARAM), offset, PHONES_COUNT_BY_PAGE));
        model.addAttribute(PAGE_NUMBERS_ATTRIBUTE, Pagination.getPageNumbers(page, maxPage, MAX_DISPLAYED_PAGES));
        model.addAttribute(MAX_PAGE_ATTRIBUTE, maxPage);
        model.addAttribute(HttpSessionCartService.CART_SESSION_ATTRIBUTE, cartService.getCart(session));
        return PRODUCT_LIST_PAGE;
    }
}
