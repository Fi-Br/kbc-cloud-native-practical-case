package com.ezgroceries.shoppinglist.core;

import com.ezgroceries.shoppinglist.contract.Cocktail;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Component("OverviewCocktails")
public class OverviewCocktailsImpl implements OverviewCocktails {
    private UUID cocktailId;
    private String name;
    private String glass;
    private String instructions;
    private String image;
    private List<String> ingredients;


    public List<Cocktail> returnCocktailList(String search){

        List<Cocktail> cocktails = new ArrayList<>();
        cocktails.add(createMargerita());
        cocktails.add(createMargerita());

        return cocktails;
    };

    public Cocktail createMargerita(){
        cocktailId = UUID.randomUUID();
        name = "Margerita";
        glass = "Cocktail glass";
        instructions = "Rub the rim of the glass with the lime slice to make the salt stick to it. Take care to moisten..";
        image = "https://www.thecocktaildb.com/images/media/drink/wpxpvu1439905379.jpg";

        ingredients = new ArrayList<>();
        ingredients.add("Tequilla");
        ingredients.add("Triple sec");
        ingredients.add("Lime juice");
        ingredients.add("Salt");

        return new Cocktail(cocktailId,name,glass,instructions,image,ingredients);
    }

    public Cocktail setBlueMargerita(){
        cocktailId = UUID.randomUUID();
        name = "Blue Margerita";
        glass = "Cocktail glass";
        instructions = "Rub rim of cocktail glass with lime juice. Dip rim in coarse salt..";
        image = "https://www.thecocktaildb.com/images/media/drink/qtvvyq1439905913.jpg";

        ingredients = new ArrayList<>();
        ingredients.add("Tequilla");
        ingredients.add("Blue Curacao");
        ingredients.add("Lime juice");
        ingredients.add("Salt");

        return new Cocktail(cocktailId,name,glass,instructions,image,ingredients);
    }
}
