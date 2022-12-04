package com.ezgroceries;

import com.ezgroceries.shoppinglist.contract.Cocktail;
import com.ezgroceries.shoppinglist.repository.jpa.CocktailEntity;
import com.ezgroceries.shoppinglist.repository.jpa.ShoppingListEntity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public final class TestVariables {

    public static final UUID SHOPPING_LIST_ID = UUID.fromString("00000000-0000-0000-0000-000000000001");
    public static final String SHOPPING_LIST_NAME = "testShoppingList";
    public static final UUID COCKTAIL_ID = UUID.fromString("10000000-0000-0000-0000-000000000000");
    public static final String DRINK_ID = "00000001";
    public static final String COCKTAIL_NAME = "Margerita";
    public static final String GLASS = "Cocktail_glass";
    public static final String INGREDIENT1 = "Tequilla";
    public static final String INGREDIENT2 = "Triple Sec";
    public static final String INGREDIENT3 = "Lime Juice";
    public static final List<String> INGREDIENTS = new ArrayList<>(Arrays.asList(INGREDIENT1,INGREDIENT2, INGREDIENT3));
    public static final String COCKTAIL_SEARCH = "RUSSIAN";
    public static final String INSTRUCTIONS = "How to make this cocktail";
    public static final String DRINK_THUMB = "Some link to a thumb";
    public static final String DRINK_NAME = "RUSSIAN WHITE";
}
