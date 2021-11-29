package com.es.core.model.phone;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:context/applicationContext-core.xml")
@Sql({"/db/schema.sql", "classpath:db/demodata-phones.sql"})
public class JdbcPhoneDaoIntTest {
    private static final long GETTING_PHONE_ID = 1011L;
    private static final String GETTING_PHONE_BRAND = "ARCHOS";
    private static final int GETTING_PHONE_COLORS_COUNT = 3;
    private static final long SAVING_PHONE_ID = 8764L;
    private static final String SAVING_PHONE_NEW_MODEL = "new Model";
    private static final String SAVING_PHONE_NEW_BRAND = "new Brand";
    private static final long NOT_IN_STOCK_SAMSUNG_ID = 7040L;
    private static final long IN_STOCK_SAMSUNG_ID = 7078L;
    @Resource
    private PhoneDao phoneDao;

    @Test
    public void shouldReturnCorrectPhoneWhenPassingExistingIdInGetMethod() {
        Optional<Phone> optionalPhone = phoneDao.get(GETTING_PHONE_ID);
        Assert.assertTrue(optionalPhone.isPresent());
        Phone phone = optionalPhone.get();
        Assert.assertEquals(GETTING_PHONE_BRAND, phone.getBrand());
        Assert.assertEquals(GETTING_PHONE_COLORS_COUNT, phone.getColors().size());
    }

    @Test
    public void shouldCorrectlySavePhoneWhenInvokeSaveMethod() {
        Optional<Phone> optionalPhone = phoneDao.get(GETTING_PHONE_ID);
        Assert.assertTrue(optionalPhone.isPresent());
        Phone phone = optionalPhone.get();
        phone.setId(SAVING_PHONE_ID);
        phone.setModel(SAVING_PHONE_NEW_MODEL);
        phone.setBrand(SAVING_PHONE_NEW_BRAND);
        phoneDao.save(phone);
        Optional<Phone> newOptionalPhone = phoneDao.get(SAVING_PHONE_ID);
        Assert.assertTrue(newOptionalPhone.isPresent());
        Phone newPhone = newOptionalPhone.get();
        Assert.assertEquals(phone.getDescription(), newPhone.getDescription());
        Assert.assertEquals(phone.getDeviceType(), newPhone.getDeviceType());
        Assert.assertEquals(phone.getBluetooth(), newPhone.getBluetooth());
        Assert.assertEquals(phone.getColors().size(), newPhone.getColors().size());
    }

    @Test
    public void shouldReturnOnlyPhonesInStockAndNotNullPriceWhenInvokeFindAllInStockMethod() {
        List<Phone> phonesInStock = phoneDao.findAllInStock("samsung", null, null);
        Set<Long> inStockPhonesIds = phonesInStock.stream()
                .map(Phone::getId)
                .collect(Collectors.toSet());
        Assert.assertFalse(inStockPhonesIds.contains(NOT_IN_STOCK_SAMSUNG_ID));
        Assert.assertTrue(inStockPhonesIds.contains(IN_STOCK_SAMSUNG_ID));
        for (Phone phone : phonesInStock) {
            Assert.assertNotNull(phone.getPrice());
        }
    }
}
