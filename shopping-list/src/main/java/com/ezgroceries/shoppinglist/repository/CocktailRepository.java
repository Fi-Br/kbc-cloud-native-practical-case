package com.ezgroceries.shoppinglist.repository;

import com.ezgroceries.shoppinglist.repository.jpa.CocktailEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import java.util.UUID;

@Repository
public interface CocktailRepository extends CrudRepository<CocktailEntity, UUID> {
        public CocktailEntity findCocktailEntityByDrinkId(String drinkId);

        public CocktailEntity save(CocktailEntity cocktailEntity);
}
