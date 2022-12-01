package com.ezgroceries.shoppinglist.contract;

import java.util.Set;
import java.util.UUID;

public class ShoppingList {
    private UUID shoppingListId;
    private String name;
    private Set<String> ingredients;

    public ShoppingList(){}

    public ShoppingList(UUID shoppingListId, String name) {
        this.shoppingListId = shoppingListId;
        this.name = name;
    }

    public ShoppingList(UUID shoppingListId, String name, Set<String> ingredients) {
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

    public Set<String> getIngredients() {
        return ingredients;
    }

}
