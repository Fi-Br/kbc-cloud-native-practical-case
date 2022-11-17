package com.ezgroceries.shoppinglist.core;

import com.ezgroceries.shoppinglist.contract.Cocktail;
import com.ezgroceries.shoppinglist.core.model.CocktailDBResponse;
import com.ezgroceries.shoppinglist.repository.CocktailDBClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Component("OverviewCocktails")
public class OverviewCocktailsImpl implements OverviewCocktails {
    CocktailDBClient cocktailDBClient;

    @Autowired
    public OverviewCocktailsImpl(CocktailDBClient cocktailDBClient){
        this.cocktailDBClient = cocktailDBClient;
    }

    public List<Cocktail> returnCocktailList(String search){
        return mapCocktailDBListOnCocktailList(cocktailDBClient.searchCocktails(search).getDrinks());
    };

    private List<Cocktail> mapCocktailDBListOnCocktailList(List<CocktailDBResponse.DrinkResource> drinkResourceList){

        List<Cocktail> cocktailList = new ArrayList<>();
        drinkResourceList.forEach(drinkResource ->
                {cocktailList.add(new Cocktail(
                        UUID.randomUUID(), //I don't use the ID from drinkResource because this isn't a valid UUID
                        drinkResource.getStrDrink(),
                        drinkResource.getStrGlass(),
                        drinkResource.getStrInstructions(),
                        drinkResource.getStrDrinkThumb(),
                        drinkResource.returnIngredientsAsList()));
                });

        return cocktailList;
    };

}
