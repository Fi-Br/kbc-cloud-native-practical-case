package com.ezgroceries.shoppinglist.repository;

import com.ezgroceries.shoppinglist.repository.jpa.CocktailEntity;
import com.ezgroceries.shoppinglist.repository.jpa.ShoppingListEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;
import java.util.UUID;

@Repository
public interface CocktailRepository extends CrudRepository<CocktailEntity, UUID> {
        CocktailEntity findCocktailEntityByDrinkId(String drinkId);

        CocktailEntity findFirstByName(String name);

        CocktailEntity save(CocktailEntity cocktailEntity);

        Set<CocktailEntity> findCocktailEntitiesByShoppingListEntitySet(ShoppingListEntity shoppingListEntity);
}
