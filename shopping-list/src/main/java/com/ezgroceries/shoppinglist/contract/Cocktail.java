package com.ezgroceries.shoppinglist.contract;

import java.util.List;

public class Cocktail {
    private String drinkId;
    private String name;
    private String glass;
    private String instructions;
    private String image;
    private List<String> ingredients;

    public String getDrinkId() {
        return drinkId;
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

    public Cocktail(String drinkId, String name, String glass, String instructions, String image, List<String> ingredients) {
        this.drinkId = drinkId;
        this.name = name;
        this.glass = glass;
        this.instructions = instructions;
        this.image = image;
        this.ingredients = ingredients;
    }
}
