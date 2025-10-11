/**
 * Item.java
 *
 * Author: Hantao Yu
 * Date: 2025-10-05
 * Description:
 *   Entity class representing an Item in the system.
 *   Maps to the "items" table in the database.
 *
 * Notes:
 *   - Uses Spring Data JDBC annotations (@Table, @Id) for mapping.
 *   - Field names align with the database schema (itemId, itemOwnerId, itemName, productDetail, productPrice, productImg, ifSold).
 *   - Serves as the data model for ItemController and ItemService.
 */

package com.laioffer.flagcamp.backend.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;
import org.springframework.data.relational.core.mapping.Column;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

@Table("items")
public class Item {

    // Primary key 
    @Id
    @Column("itemid")  
    private Long itemId;

    // Owner user id (FK to users.userId)
    @NotNull
    @Column("itemownerid")
    private Long itemOwnerId;

    // Item name
    @NotBlank
    @Column("itemname")
    private String itemName;

    // Optional detail/description
    @Column("productdetail")
    private String productDetail;

    // Price, non-negative, use BigDecimal for precision
    @NotNull
    @DecimalMin("0.00")
    @Column("productprice")
    private BigDecimal productPrice;

    // Image path or URL (DB stores path only)
    @Column("productimg")
    private String productImg;

    // Whether the item has been sold
    @NotNull
    @Column("ifsold")
    private Boolean ifSold = false;

    // ---- Constructors ----
    public Item() { }

    public Item(Long itemId,
                Long itemOwnerId,
                String itemName,
                String productDetail,
                BigDecimal productPrice,
                String productImg,
                Boolean ifSold) {
        this.itemId = itemId;
        this.itemOwnerId = itemOwnerId;
        this.itemName = itemName;
        this.productDetail = productDetail;
        this.productPrice = productPrice;
        this.productImg = productImg;
        this.ifSold = (ifSold != null ? ifSold : false);
    }

    // ---- Getters / Setters ----
    public Long getItemId() { return itemId; }
    public void setItemId(Long itemId) { this.itemId = itemId; }

    public Long getItemOwnerId() { return itemOwnerId; }
    public void setItemOwnerId(Long itemOwnerId) { this.itemOwnerId = itemOwnerId; }

    public String getItemName() { return itemName; }
    public void setItemName(String itemName) { this.itemName = itemName; }

    public String getProductDetail() { return productDetail; }
    public void setProductDetail(String productDetail) { this.productDetail = productDetail; }

    public BigDecimal getProductPrice() { return productPrice; }
    public void setProductPrice(BigDecimal productPrice) { this.productPrice = productPrice; }

    public String getProductImg() { return productImg; }
    public void setProductImg(String productImg) { this.productImg = productImg; }

    public Boolean getIfSold() { return ifSold; }
    public void setIfSold(Boolean ifSold) { this.ifSold = (ifSold != null ? ifSold : false); }
}
