package com.ezgroceries.shoppinglist.core;

import com.ezgroceries.shoppinglist.contract.Cocktail;
import com.ezgroceries.shoppinglist.core.model.CocktailDBResponse;
import com.ezgroceries.shoppinglist.repository.CocktailDBClient;
import com.ezgroceries.shoppinglist.repository.CocktailRepository;
import com.ezgroceries.shoppinglist.repository.jpa.CocktailEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class OverviewCocktailsImpl implements OverviewCocktails {
    @Autowired
    CocktailDBClient cocktailDBClient;
    @Autowired
    CocktailRepository cocktailRepository;

    @Override
    @Transactional
    public List<Cocktail> returnCocktailList(String search){
        List<CocktailDBResponse.DrinkResource> drinkResourceList = cocktailDBClient.searchCocktails(search).getDrinks();
        List<CocktailEntity> cocktailEntityList = new ArrayList<>();

        List<Cocktail> cocktailList = new ArrayList<>();
        drinkResourceList.forEach(drinkResource ->
        {cocktailList.add(new Cocktail(
                UUID.randomUUID(), //I don't use the ID from drinkResource because this isn't a valid UUID
                drinkResource.getStrDrink(),
                drinkResource.getStrGlass(),
                drinkResource.getStrInstructions(),
                drinkResource.getStrDrinkThumb(),
                drinkResource.returnIngredientsAsList()));
            cocktailEntityList.add(cocktailRepository.save(new CocktailEntity(
                    drinkResource.getIdDrink(),
                    drinkResource.getStrDrink(),
                    drinkResource.returnIngredientsAsList())));
        });

        return cocktailList;
    };



}
