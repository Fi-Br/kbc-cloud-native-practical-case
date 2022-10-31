package com.ezgroceries.shoppinglist.contract;

import java.util.List;
import java.util.UUID;

public class ShoppingList {
    private UUID shoppingListId;
    private String name;
    private List<String> ingredients;

    public ShoppingList(UUID shoppingListId, String name, List<String> ingredients) {
        this.shoppingListId = shoppingListId;
        this.name = name;
        this.ingredients = ingredients;
    }

    public UUID getShoppingListId() {
        return shoppingListId;
    }

    public String getName() {
        return name;
    }

    public List<String> getIngredients() {
        return ingredients;
    }

}
