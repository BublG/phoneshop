package com.es.core.cart;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:context/applicationContext-core.xml")
@Sql({"/db/schema.sql", "classpath:db/demodata-phones.sql"})
public class HttpSessionCartServiceTest {
    private static final long PHONE_ID_1 = 1003L;
    private static final long PHONE_ID_2 = 1006L;
    private static final long PHONE_1_PRICE = 249L;
    private static final long PHONE_2_PRICE = 270L;

    @Resource
    private CartService cartService;

    @Test
    public void testRecalculatingCart() {
        Cart cart = new Cart();
        long quantity1 = 2L;
        long quantity2 = 3L;

        cartService.addPhone(cart, PHONE_ID_1, quantity1);
        cartService.addPhone(cart, PHONE_ID_2, quantity2);
        Assert.assertEquals(quantity1 + quantity2, cart.getTotalQuantity());
        Assert.assertEquals(PHONE_1_PRICE * quantity1 + PHONE_2_PRICE * quantity2,
                cart.getTotalCost().toBigInteger().longValue());

        quantity1 = 1L;
        quantity2 = 2L;
        cartService.update(cart, PHONE_ID_1, quantity1);
        cartService.update(cart, PHONE_ID_2, quantity2);
        Assert.assertEquals(quantity1 + quantity2, cart.getTotalQuantity());
        Assert.assertEquals(PHONE_1_PRICE * quantity1 + PHONE_2_PRICE * quantity2,
                cart.getTotalCost().toBigInteger().longValue());

        cartService.remove(cart, PHONE_ID_2);
        Assert.assertEquals(quantity1, cart.getTotalQuantity());
        Assert.assertEquals(PHONE_1_PRICE * quantity1,
                cart.getTotalCost().toBigInteger().longValue());
    }
}
