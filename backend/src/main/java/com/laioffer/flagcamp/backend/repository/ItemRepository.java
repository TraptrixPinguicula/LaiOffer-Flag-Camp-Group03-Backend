/**
 * ItemRepository.java
 *
 * Author: Hantao Yu
 * Date: 2025-10-05
 * Description:
 *   Repository interface for managing Item entities.
 *   Provides CRUD operations and custom query methods for Item data.
 *
 * Notes:
 *   - Uses Spring Data JDBC for database access.
 *   - Automatically implements standard CRUD methods (save, findById, deleteById, etc.).
 *   - Can define custom queries using @Query if needed in future extensions.
 */

package com.laioffer.flagcamp.backend.repository;

import com.laioffer.flagcamp.backend.entity.Item;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ItemRepository extends CrudRepository<Item, Long> {

    /**
     * Find all items by owner user ID.
     * @param itemOwnerId The ID of the user who owns the item.
     * @return List of items belonging to the specified user.
     */
    List<Item> findByItemOwnerId(Long itemOwnerId);

    /**
     * Find items by sale status.
     * @param ifSold true if sold, false otherwise.
     * @return List of items filtered by sold status.
     */
    List<Item> findByIfSold(Boolean ifSold);

    /**
     * Search items by keyword in item name (case-insensitive).
     * @param keyword Partial name of the item.
     * @return List of matching items.
     */
    List<Item> findByItemNameContainingIgnoreCase(String keyword);

    /**
     * Find one item by ID (for explicit Optional return).
     * @param itemId The ID of the item.
     * @return Optional containing the item if found.
     */
    Optional<Item> findByItemId(Long itemId);
}
