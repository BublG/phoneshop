package com.es.core.cart;

import com.es.core.model.phone.Phone;
import com.es.core.model.phone.PhoneDao;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.math.BigDecimal;
import java.util.Optional;

import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class HttpSessionCartServiceTest {
    private static final long PHONE_ID_1 = 1003L;
    private static final long PHONE_ID_2 = 1006L;
    private static final long PHONE_1_PRICE = 249L;
    private static final long PHONE_2_PRICE = 270L;
    private static final long IN_STOCK_QUANTITY = 20L;
    private static final long TEST_PHONE_1_QUANTITY = 2L;
    private static final long TEST_PHONE_2_QUANTITY = 3L;
    private static final long UPDATED_TEST_PHONE_1_QUANTITY = 1L;
    private static final long UPDATED_TEST_PHONE_2_QUANTITY = 2L;

    private HttpSessionCartService cartService;
    private Cart cart;

    @Mock
    private Phone testPhone1;
    @Mock
    private Phone testPhone2;
    @Mock
    private PhoneDao phoneDao;

    @Before
    public void setup() {
        cartService = new HttpSessionCartService();
        cart = new Cart();
        cartService.setPhoneDao(phoneDao);
        when(testPhone1.getId()).thenReturn(PHONE_ID_1);
        when(testPhone1.getPrice()).thenReturn(new BigDecimal(PHONE_1_PRICE));
        when(testPhone2.getId()).thenReturn(PHONE_ID_2);
        when(testPhone2.getPrice()).thenReturn(new BigDecimal(PHONE_2_PRICE));
        when(phoneDao.getInStockQuantity(PHONE_ID_1)).thenReturn(IN_STOCK_QUANTITY);
        when(phoneDao.getInStockQuantity(PHONE_ID_2)).thenReturn(IN_STOCK_QUANTITY);
        when(phoneDao.get(PHONE_ID_1)).thenReturn(Optional.of(testPhone1));
        when(phoneDao.get(PHONE_ID_2)).thenReturn(Optional.of(testPhone2));
    }

    @Test
    public void shouldCorrectRecalculateCartWhenAddingToCart() {
        cartService.addPhone(cart, PHONE_ID_1, TEST_PHONE_1_QUANTITY);
        cartService.addPhone(cart, PHONE_ID_2, TEST_PHONE_2_QUANTITY);
        Assert.assertEquals(TEST_PHONE_1_QUANTITY + TEST_PHONE_2_QUANTITY, cart.getTotalQuantity());
        Assert.assertEquals(PHONE_1_PRICE * TEST_PHONE_1_QUANTITY + PHONE_2_PRICE * TEST_PHONE_2_QUANTITY,
                cart.getTotalCost().toBigInteger().longValue());
    }

    @Test
    public void shouldCorrectRecalculateCartWhenUpdatingCart() {
        cartService.addPhone(cart, PHONE_ID_1, TEST_PHONE_1_QUANTITY);
        cartService.addPhone(cart, PHONE_ID_2, TEST_PHONE_2_QUANTITY);
        cartService.update(cart, PHONE_ID_1, UPDATED_TEST_PHONE_1_QUANTITY);
        cartService.update(cart, PHONE_ID_2, UPDATED_TEST_PHONE_2_QUANTITY);
        Assert.assertEquals(UPDATED_TEST_PHONE_1_QUANTITY + UPDATED_TEST_PHONE_2_QUANTITY, cart.getTotalQuantity());
        Assert.assertEquals(PHONE_1_PRICE * UPDATED_TEST_PHONE_1_QUANTITY + PHONE_2_PRICE * UPDATED_TEST_PHONE_2_QUANTITY,
                cart.getTotalCost().toBigInteger().longValue());
    }

    @Test
    public void shouldCorrectRecalculateCartWhenDeletingFromCart() {
        cartService.addPhone(cart, PHONE_ID_1, TEST_PHONE_1_QUANTITY);
        cartService.addPhone(cart, PHONE_ID_2, TEST_PHONE_2_QUANTITY);
        cartService.remove(cart, PHONE_ID_2);
        Assert.assertEquals(TEST_PHONE_1_QUANTITY, cart.getTotalQuantity());
        Assert.assertEquals(PHONE_1_PRICE * TEST_PHONE_1_QUANTITY,
                cart.getTotalCost().toBigInteger().longValue());
    }
}
