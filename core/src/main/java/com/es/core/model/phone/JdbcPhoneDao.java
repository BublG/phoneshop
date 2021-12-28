package com.es.core.model.phone;

import org.apache.commons.lang3.StringUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class JdbcPhoneDao implements PhoneDao {
    private static final String SELECT_PHONE_BY_ID_QUERY = "select * from phones where id = ?";
    private static final String SELECT_PHONE_BY_MODEL_QUERY = "select * from phones where model = ?";
    private static final String SELECT_COLOR_ID_BY_PHONE_ID_QUERY = "select colorId from phone2color" +
            " where phoneId = ?";
    private static final String SELECT_COLOR_BY_ID_QUERY = "select * from colors where id = ?";
    private static final String INSERT_PHONE_QUERY = "insert into phones values(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?," +
            " ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
    private static final String INSERT_INTO_PHONE2COLOR_QUERY = "insert into phone2color values (?, ?)";
    private static final String SELECT_ALL_QUERY_TEMPLATE = "select * from phones offset %d limit %d";
    private static final String SELECT_ALL_IN_STOCK_AND_NOT_NULL_PRICE_QUERY = "select * from phones, stocks" +
            " where phones.id = stocks.phoneId and stocks.stock > 0 and phones.price is not null";
    private static final String SEARCH_QUERY_PART = " and lower(phones.model) like '%";
    private static final String SORT_QUERY_PART = " order by ";
    private static final String SELECT_QUANTITY_IN_STOCK_QUERY_TEMPLATE = "select stock from stocks where phoneId = %d";
    private static final String UPDATE_PHONE_STOCK_QUERY = "UPDATE stocks set stock = ? where phoneId = ?";
    private static final String SELECT_IN_STOCK_PHONES_COUNT_QUERY = "select count(*) from phones, stocks" +
            " where phones.id = stocks.phoneId and stocks.stock > 0 and phones.price is not null";
    private static final String LIMIT_QUERY_PART = " limit ";
    private static final String OFFSET_QUERY_PART = " offset ";

    @Resource
    private JdbcTemplate jdbcTemplate;

    public Optional<Phone> get(final Long key) {
        Optional<Phone> phone = Optional.ofNullable(jdbcTemplate
                .queryForObject(SELECT_PHONE_BY_ID_QUERY, new Object[]{key},
                        BeanPropertyRowMapper.newInstance(Phone.class)));
        phone.ifPresent(this::setPhoneColors);
        return phone;
    }

    @Override
    public Phone getPhoneByModel(String model) {
        List<Phone> phones = jdbcTemplate.query(SELECT_PHONE_BY_MODEL_QUERY, new Object[]{model},
                new BeanPropertyRowMapper<>(Phone.class));
        if (phones.size() > 0) {
            Phone phone = phones.get(0);
            setPhoneColors(phone);
            return phone;
        }
        return null;
    }

    public void save(final Phone phone) {
        jdbcTemplate.update(INSERT_PHONE_QUERY,
                phone.getId(), phone.getBrand(), phone.getModel(), phone.getPrice(), phone.getDisplaySizeInches(),
                phone.getWeightGr(), phone.getLengthMm(), phone.getWidthMm(), phone.getHeightMm(),
                phone.getAnnounced(), phone.getDeviceType(), phone.getOs(), phone.getDisplayResolution(),
                phone.getPixelDensity(), phone.getDisplayTechnology(), phone.getBackCameraMegapixels(),
                phone.getFrontCameraMegapixels(), phone.getRamGb(), phone.getInternalStorageGb(),
                phone.getBatteryCapacityMah(), phone.getTalkTimeHours(), phone.getStandByTimeHours(),
                phone.getBluetooth(), phone.getPositioning(), phone.getImageUrl(), phone.getDescription());
        jdbcTemplate.batchUpdate(INSERT_INTO_PHONE2COLOR_QUERY, phone.getColors(), phone.getColors().size(),
                (preparedStatement, color) -> {
                    preparedStatement.setLong(1, phone.getId());
                    preparedStatement.setLong(2, color.getId());
                });
    }

    public List<Phone> findAll(int offset, int limit) {
        return jdbcTemplate.query(String.format(SELECT_ALL_QUERY_TEMPLATE, offset, limit),
                new BeanPropertyRowMapper<>(Phone.class));
    }

    public List<Phone> findPhonesInStock(String query, String sortField, String sortOrder, int offset, int limit) {
        List<Phone> phonesInStock = jdbcTemplate.query(getDBQueryForFindPhonesInStock(query, sortField,
                sortOrder, offset, limit), new BeanPropertyRowMapper<>(Phone.class));
        for (Phone phone : phonesInStock) {
            setPhoneColors(phone);
        }
        return phonesInStock;
    }

    @Override
    public long getInStockQuantity(long phoneId) {
        return jdbcTemplate.queryForObject(String.format(SELECT_QUANTITY_IN_STOCK_QUERY_TEMPLATE,
                phoneId), Long.class);
    }

    @Override
    public void decreaseStock(Long phoneId, Long quantity) {
        long newStock = getInStockQuantity(phoneId) - quantity;
        jdbcTemplate.update(UPDATE_PHONE_STOCK_QUERY, newStock, phoneId);
    }

    @Override
    public long getInStockPhonesQuantity(String query) {
        StringBuilder selectInStockCountQuery = new StringBuilder(SELECT_IN_STOCK_PHONES_COUNT_QUERY);
        if (!StringUtils.isBlank(query)) {
            selectInStockCountQuery.append(SEARCH_QUERY_PART).append(query.trim().toLowerCase()).append("%'");
        }
        return jdbcTemplate.queryForObject(selectInStockCountQuery.toString(), Long.class);
    }

    private String getDBQueryForFindPhonesInStock(String query, String sortField, String sortOrder,
                                                  int offset, int limit) {
        StringBuilder DBQuery = new StringBuilder(SELECT_ALL_IN_STOCK_AND_NOT_NULL_PRICE_QUERY);
        if (!StringUtils.isBlank(query)) {
            DBQuery.append(SEARCH_QUERY_PART).append(query.trim().toLowerCase()).append("%'");
        }
        if (!StringUtils.isEmpty(sortField)) {
            DBQuery.append(SORT_QUERY_PART).append(sortField).append(" ").append(sortOrder);
        }
        return DBQuery.append(LIMIT_QUERY_PART).append(limit).append(OFFSET_QUERY_PART).append(offset).toString();
    }

    private void setPhoneColors(Phone phone) {
        List<Long> colorIds = jdbcTemplate.queryForList(SELECT_COLOR_ID_BY_PHONE_ID_QUERY,
                new Object[]{phone.getId()}, Long.class);
        Set<Color> colors = colorIds.stream()
                .map(colorId -> jdbcTemplate.queryForObject(SELECT_COLOR_BY_ID_QUERY,
                        new Object[]{colorId}, BeanPropertyRowMapper.newInstance(Color.class)))
                .collect(Collectors.toSet());
        phone.setColors(colors);
    }
}
