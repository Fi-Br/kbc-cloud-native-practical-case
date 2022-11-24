package com.ezgroceries.shoppinglist.repository;

import com.ezgroceries.shoppinglist.repository.jpa.CocktailEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface CocktailRepository extends CrudRepository<CocktailEntity, UUID> {

        public CocktailEntity findCocktailEntityByDrinkId(String drinkId);

}
