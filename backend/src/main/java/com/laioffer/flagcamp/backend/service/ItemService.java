package com.laioffer.flagcamp.backend.service;

import com.laioffer.flagcamp.backend.controller.ItemController.CreateItemReq;
import com.laioffer.flagcamp.backend.controller.ItemController.UpdateItemReq;
import com.laioffer.flagcamp.backend.entity.Item;
import com.laioffer.flagcamp.backend.repository.ItemRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.DataClassRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Types;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * ItemService bridges the controller with the database table "items".
 * It performs minimal validation (controller handles request validation)
 * and encapsulates pagination/sorting logic.
 */
@Service
public class ItemService {

        private static final RowMapper<Item> ITEM_ROW_MAPPER =
            DataClassRowMapper.newInstance(Item.class);

    private static final Map<String, String> SORT_COLUMN_MAP = Map.of(
            "itemId", "itemid",
            "itemName", "itemname",
            "productPrice", "productprice",
            "isSold", "ifsold"
    );

    private final ItemRepository itemRepository;
    private final NamedParameterJdbcTemplate jdbcTemplate;

    public ItemService(ItemRepository itemRepository, NamedParameterJdbcTemplate jdbcTemplate) {
        this.itemRepository = itemRepository;
        this.jdbcTemplate = jdbcTemplate;
    }

    @Transactional
    public Item createItem(CreateItemReq req) {
        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("item_owner_id", req.itemOwnerId, Types.BIGINT)
                .addValue("item_name", req.itemName, Types.VARCHAR)
                .addValue("product_detail", req.productDetail, Types.VARCHAR)
                .addValue("product_price", toSqlPrice(req.productPrice), Types.NUMERIC)
                .addValue("product_img", req.productImg, Types.VARCHAR)
                .addValue("is_sold", Boolean.FALSE, Types.BOOLEAN);

        String sql = "INSERT INTO items (itemownerid, itemname, productdetail, productprice, productimg, ifsold) " +
                "VALUES (:item_owner_id, :item_name, :product_detail, :product_price, :product_img, :is_sold)";

        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(sql, params, keyHolder, new String[]{"itemid"});

        Long generatedId = Optional.ofNullable(keyHolder.getKey())
                .map(Number::longValue)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to create item"));

        return getById(generatedId);
    }

    public Page<Item> search(String keyword, Boolean sold, int page, int size, Sort sort) {
        Map<String, Object> criteria = new HashMap<>();
        List<String> predicates = new ArrayList<>();

        if (keyword != null && !keyword.isBlank()) {
            criteria.put("keyword", likeKeyword(keyword));
            predicates.add("(itemname ILIKE :keyword OR productdetail ILIKE :keyword)");
        }

        if (sold != null) {
            criteria.put("sold", sold);
            predicates.add("ifsold = :sold");
        }

        String whereClause = predicates.isEmpty() ? "" : " WHERE " + String.join(" AND ", predicates);
        String orderByClause = buildOrderByClause(sort);

        Map<String, Object> pageParams = new HashMap<>(criteria);
        pageParams.put("limit", size);
        pageParams.put("offset", page * (long) size);

        String selectSql = "SELECT " + selectColumns() + " FROM items" + whereClause + orderByClause +
                " LIMIT :limit OFFSET :offset";
        List<Item> content = jdbcTemplate.query(selectSql, pageParams, ITEM_ROW_MAPPER);

        String countSql = "SELECT COUNT(*) FROM items" + whereClause;
        long total = jdbcTemplate.queryForObject(countSql, criteria, Long.class);

        return new PageImpl<>(content, PageRequest.of(page, size, sort), total);
    }

    public Item getById(Long id) {
        return itemRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Item not found: " + id));
    }

    @Transactional
    public Item updateItem(Long id, UpdateItemReq req) {
        Item existing = getById(id);
        Item updated = new Item(
                existing.itemId(),
                existing.itemOwnerId(),
                req.itemName != null ? req.itemName : existing.itemName(),
                req.productDetail != null ? req.productDetail : existing.productDetail(),
                req.productPrice != null ? toSqlPrice(req.productPrice) : existing.productPrice(),
                req.productImg != null ? req.productImg : existing.productImg(),
                req.sold != null ? req.sold : existing.isSold()
        );
        return itemRepository.save(updated);
    }

    @Transactional
    public void deleteItem(Long id) {
        if (!itemRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Item not found: " + id);
        }
        itemRepository.deleteById(id);
    }

    private static BigDecimal toSqlPrice(BigDecimal price) {
        return price == null ? null : price.setScale(2, RoundingMode.HALF_UP);
    }

    private static String likeKeyword(String keyword) {
        return "%" + keyword + "%";
    }

    private static String selectColumns() {
        return "itemid AS itemId, " +
                "itemownerid AS itemOwnerId, " +
                "itemname AS itemName, " +
                "productdetail AS productDetail, " +
                "productprice AS productPrice, " +
                "productimg AS productImg, " +
                "ifsold AS isSold";
    }

    private static String buildOrderByClause(Sort sort) {
        if (sort == null || sort.isUnsorted()) {
            return " ORDER BY itemid DESC";
        }

        List<String> clauses = new ArrayList<>();
        for (Sort.Order order : sort) {
            String dbColumn = SORT_COLUMN_MAP.get(order.getProperty());
            if (dbColumn == null) {
                dbColumn = "itemid";
            }
            clauses.add(dbColumn + " " + order.getDirection().name());
        }

        if (clauses.isEmpty()) {
            clauses.add("itemid DESC");
        }
        return " ORDER BY " + String.join(", ", clauses);
    }
}
