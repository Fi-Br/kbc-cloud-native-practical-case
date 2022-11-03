package com.ezgroceries.shoppinglist.contract;

import java.util.List;
import java.util.UUID;

public class Cocktail {
    private UUID cocktailId;
    private String name;
    private String glass;
    private String instructions;
    private String image;
    private List<String> ingredients;

    public UUID getCocktailId() {
        return cocktailId;
    }

    public String getName() {
        return name;
    }

    public String getGlass() {
        return glass;
    }

    public String getInstructions() {
        return instructions;
    }

    public String getImage() {
        return image;
    }

    public List<String> getIngredients() {
        return ingredients;
    }

    public Cocktail(UUID cocktailId, String name, String glass, String instructions, String image, List<String> ingredients) {
        this.cocktailId = cocktailId;
        this.name = name;
        this.glass = glass;
        this.instructions = instructions;
        this.image = image;
        this.ingredients = ingredients;
    }

}
