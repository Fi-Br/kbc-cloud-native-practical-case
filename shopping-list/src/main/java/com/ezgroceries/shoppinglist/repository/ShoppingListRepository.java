package com.ezgroceries.shoppinglist.repository;

import com.ezgroceries.shoppinglist.repository.jpa.ShoppingListEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
public interface ShoppingListRepository extends CrudRepository<ShoppingListEntity, UUID>{
    ShoppingListEntity findShoppingListEntityByShoppingListId(UUID shoppingListId);

    ShoppingListEntity findShoppingListEntityByName(String name);

    ShoppingListEntity save(ShoppingListEntity shoppingListEntity);

    List<ShoppingListEntity> findAll();
}
