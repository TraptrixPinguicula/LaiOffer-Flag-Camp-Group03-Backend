/**
 * ItemController.java
 *
 * Author: Hantao Yu
 * Date: 2025-09-28
 * Description:
 *   REST controller for managing Items.
 *   Provides CRUD operations and supports pagination and keyword search.
 *
 * Notes:
 *   - Depends on ItemService for business logic.
 *   - Includes field whitelist validation for sorting.
 *   - Returns REST-style responses with proper HTTP status codes.
 */

package com.laioffer.flagcamp.backend.controller;

import com.laioffer.flagcamp.backend.entity.Item;
import com.laioffer.flagcamp.backend.service.ItemService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URI;
import java.util.Set;

@RestController
@RequestMapping("/api/items")
@Validated
public class ItemController {

    private static final int DEFAULT_PAGE = 0;
    private static final int DEFAULT_SIZE = 12;
    private static final int MAX_SIZE = 50;
    private static final String DEFAULT_SORT = "itemId,desc";
    private static final Set<String> SORT_WHITELIST = Set.of(
            "itemId", "itemName", "productPrice", "ifSold"
    );

    private final ItemService itemService;

    public ItemController(ItemService itemService) {
        this.itemService = itemService;
    }

    /**
     * Create a new Item.
     * Returns 201 Created with Location header pointing to the new resource.
     */
    @PostMapping
    public ResponseEntity<Item> create(@Valid @RequestBody CreateItemReq req) {
        normalizeCreate(req);
        Item saved = itemService.createItem(req);
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(URI.create("/api/items/" + saved.getItemId()));
        return new ResponseEntity<>(saved, headers, HttpStatus.CREATED);
    }

    /**
     * List items with pagination and optional filters.
     * Params:
     * - q: keyword (matches name/detail, case-insensitive)
     * - sold: filter by sold flag
     * - page/size: pagination (size is clamped to MAX_SIZE)
     * - sort: "field,direction" with a whitelist of fields
     */
    @GetMapping
    public Page<Item> list(@RequestParam(required = false) String q,
                           @RequestParam(required = false) Boolean sold,
                           @RequestParam(defaultValue = "" + DEFAULT_PAGE) @Min(0) int page,
                           @RequestParam(defaultValue = "" + DEFAULT_SIZE) @Min(1) int size,
                           @RequestParam(defaultValue = DEFAULT_SORT) String sort) {

        // Clamp page size to avoid excessive memory usage
        if (size > MAX_SIZE) size = MAX_SIZE;

        // Parse & whitelist sort input
        Sort springSort = parseSortSafely(sort);

        // Normalize keyword
        String keyword = (q == null || q.isBlank()) ? null : q.trim();

        return itemService.search(keyword, sold, page, size, springSort);
    }

    /**
     * Get a single item by id.
     * NotFound is mapped by a global exception handler in the service layer.
     */
    @GetMapping("/{id}")
    public Item get(@PathVariable Long id) {
        return itemService.getById(id);
    }

    /**
     * Partially update an item.
     * Only non-null fields in the request will be applied.
     */
    @PutMapping("/{id}")
    public Item update(@PathVariable Long id, @Valid @RequestBody UpdateItemReq req) {
        normalizeUpdate(req);
        return itemService.updateItem(id, req);
    }

    /**
     * Delete an item by id.
     * Returns 204 No Content on success.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        itemService.deleteItem(id);
        return ResponseEntity.noContent().build();
    }

    // ===== Helpers =====

    /**
     * Parse "field,direction" and enforce a whitelist for the field name.
     * Falls back to DEFAULT_SORT if invalid.
     */
    private Sort parseSortSafely(String sort) {
        String input = (sort == null || sort.isBlank()) ? DEFAULT_SORT : sort.trim();
        String[] parts = input.split(",", 2);
        String field = parts[0].trim();

        if (!SORT_WHITELIST.contains(field)) {
            // Fallback to default when the field is not allowed
            field = DEFAULT_SORT.split(",", 2)[0];
        }

        Sort.Direction dir = Sort.Direction.DESC;
        if (parts.length > 1) {
            String d = parts[1].trim().toLowerCase();
            if ("asc".equals(d)) dir = Sort.Direction.ASC;
            else if ("desc".equals(d)) dir = Sort.Direction.DESC;
        }
        return Sort.by(dir, field);
    }

    /**
     * Normalize string fields and round price to 2 decimals (HALF_UP) for create requests.
     */
    private void normalizeCreate(CreateItemReq req) {
        if (req.itemName != null) req.itemName = req.itemName.trim();
        if (req.productDetail != null) req.productDetail = req.productDetail.trim();
        if (req.productImg != null) req.productImg = req.productImg.trim();
        if (req.productPrice != null) {
            req.productPrice = req.productPrice.setScale(2, RoundingMode.HALF_UP);
        }
    }

    /**
     * Normalize string fields and round price to 2 decimals (HALF_UP) for update requests.
     */
    private void normalizeUpdate(UpdateItemReq req) {
        if (req.itemName != null) req.itemName = req.itemName.trim();
        if (req.productDetail != null) req.productDetail = req.productDetail.trim();
        if (req.productImg != null) req.productImg = req.productImg.trim();
        if (req.productPrice != null) {
            req.productPrice = req.productPrice.setScale(2, RoundingMode.HALF_UP);
        }
    }

    // ===== DTOs =====

    /**
     * Request body for creating an Item.
     * itemOwnerId must be provided until authentication is wired (then derive from security context).
     */
    public static class CreateItemReq {
        /** Owner user id (temporary until auth is integrated). */
        @NotNull
        public Long itemOwnerId;

        /** Item name (required, non-blank). */
        @NotBlank
        public String itemName;

        /** Optional detailed description. */
        public String productDetail;

        /** Price with 2 decimals, must be >= 0.00. */
        @NotNull
        @DecimalMin(value = "0.00")
        public BigDecimal productPrice;

        /** Optional image URL/path (backend stores path only). */
        public String productImg;
    }

    /**
     * Request body for partially updating an Item.
     * Only non-null fields will be applied.
     */
    public static class UpdateItemReq {
        /** New item name (optional). */
        public String itemName;

        /** New description (optional). */
        public String productDetail;

        /** New price (optional), must be >= 0.00 if provided. */
        @DecimalMin(value = "0.00")
        public BigDecimal productPrice;

        /** New image URL/path (optional). */
        public String productImg;

        /** New sold flag (optional). Null means no change. */
        public Boolean sold;
    }
}
