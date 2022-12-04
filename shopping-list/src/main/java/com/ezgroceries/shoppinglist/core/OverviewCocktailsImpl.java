package com.ezgroceries.shoppinglist.core;

import com.ezgroceries.shoppinglist.contract.Cocktail;
import com.ezgroceries.shoppinglist.core.exceptions.BadRequestException;
import com.ezgroceries.shoppinglist.core.model.CocktailDBResponse;
import com.ezgroceries.shoppinglist.repository.CocktailDBClient;
import com.ezgroceries.shoppinglist.repository.CocktailRepository;
import com.ezgroceries.shoppinglist.repository.jpa.CocktailEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class OverviewCocktailsImpl implements OverviewCocktails {

    private CocktailDBClient cocktailDBClient;

    private CocktailRepository cocktailRepository;

    public OverviewCocktailsImpl(CocktailDBClient cocktailDBClient, CocktailRepository cocktailRepository){
        this.cocktailDBClient = cocktailDBClient;
        this.cocktailRepository = cocktailRepository;
    }
    @Override
    public List<Cocktail> returnCocktailList(String search){
        List<CocktailDBResponse.DrinkResource> drinkResourceList;
        try {
            drinkResourceList = cocktailDBClient.searchCocktails(search).getDrinks();
        }catch (NullPointerException e) {
            throw new BadRequestException();
        }

        List<Cocktail> cocktailList = new ArrayList<>();
        drinkResourceList.forEach(drinkResource ->
        {cocktailList.add(new Cocktail(
                drinkResource.getIdDrink(),
                drinkResource.getStrDrink(),
                drinkResource.getStrGlass(),
                drinkResource.getStrInstructions(),
                drinkResource.getStrDrinkThumb(),
                drinkResource.returnIngredientsAsList()));
            cocktailRepository.save(createOrUpdateCocktailDb(drinkResource));
        });

        return cocktailList;
    }

    private CocktailEntity createOrUpdateCocktailDb(CocktailDBResponse.DrinkResource drinkResource){
        CocktailEntity cocktailEntity = cocktailRepository.findCocktailEntityByDrinkId(drinkResource.getIdDrink());

        if (cocktailEntity == null){
            cocktailEntity = new CocktailEntity(drinkResource.getIdDrink(), drinkResource.getStrDrink(), drinkResource.returnIngredientsAsList());
        }else{
            cocktailEntity.UpdateNewCocktailEntity(cocktailEntity.getCocktailId(),drinkResource.getIdDrink(),drinkResource.getStrDrink(),drinkResource.returnIngredientsAsList());
        }

        return cocktailEntity;
    }

}

