package com.es.phoneshop.web.controller.pages;

import com.es.core.cart.Cart;
import com.es.core.cart.CartService;
import com.es.core.exceptions.PhoneOutOfStockException;
import com.es.core.model.phone.Phone;
import com.es.core.model.phone.PhoneDao;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Controller
@RequestMapping("/quickAdding")
public class QuickAddingController {
    private static final String QUICK_ADDING_PAGE = "quickAddingToCart";

    private static final String QUANTITY_ERRORS_ATTRIBUTE = "quantityErrors";
    private static final String PHONE_MODEL_ERRORS_ATTRIBUTE = "phoneModelErrors";
    private static final String SUCCESS_MESSAGES_ATTRIBUTE = "successMessages";
    private static final String ERROR_MESSAGE_ATTRIBUTE = "error";

    private static final String PHONE_NOT_FOUND_ERROR = "Phone not found";
    private static final String NOT_A_VALID_NUMBER_ERROR = "Not a valid number";
    private static final String OUT_OF_STOCK_ERROR = "Quantity is out of stock";

    private static final String ERROR_MESSAGE = "There were errors";
    private static final String SUCCESS_MESSAGE = "Added to cart successfully";
    @Resource
    private PhoneDao phoneDao;
    @Resource
    private CartService cartService;

    @RequestMapping(method = RequestMethod.GET)
    public String showQuickAddingPage() {
        return QUICK_ADDING_PAGE;
    }

    @RequestMapping(method = RequestMethod.POST)
    public String addToCart(@RequestParam String[] model, @RequestParam String[] quantity, HttpSession session,
                            Model pageModel) {
        Cart cart = cartService.getCart(session);
        Map<Integer, String> phoneModelErrors = new HashMap<>();
        Map<Integer, String> quantityErrors = new HashMap<>();
        Map<String, String> successMessages = new HashMap<>();
        boolean hasErrors = false;
        for (int i = 0; i < model.length; i++) {
            String phoneModel = model[i];
            if (!StringUtils.isBlank(phoneModel)) {
                Phone phone = phoneDao.getPhoneByModel(phoneModel);
                if (phone != null) {
                    long phoneQuantity;
                    try {
                        phoneQuantity = Long.parseLong(quantity[i]);
                        if (phoneQuantity < 1) {
                            throw new NumberFormatException();
                        }
                        try {
                            cartService.addPhone(cart, phone.getId(), phoneQuantity);
                            successMessages.put(phoneModel, SUCCESS_MESSAGE);
                        } catch (PhoneOutOfStockException e) {
                            quantityErrors.put(i, OUT_OF_STOCK_ERROR);
                            hasErrors = true;
                        }
                    } catch (NumberFormatException e) {
                        quantityErrors.put(i, NOT_A_VALID_NUMBER_ERROR);
                        hasErrors = true;
                    }
                } else {
                    phoneModelErrors.put(i, PHONE_NOT_FOUND_ERROR);
                    hasErrors = true;
                }
            }
        }
        pageModel.addAttribute(QUANTITY_ERRORS_ATTRIBUTE, quantityErrors);
        pageModel.addAttribute(PHONE_MODEL_ERRORS_ATTRIBUTE, phoneModelErrors);
        pageModel.addAttribute(SUCCESS_MESSAGES_ATTRIBUTE, successMessages);
        if (hasErrors) {
            pageModel.addAttribute(ERROR_MESSAGE_ATTRIBUTE, ERROR_MESSAGE);
        }
        return QUICK_ADDING_PAGE;
    }
}
