package com.ezgroceries.shoppinglist.core;

import com.ezgroceries.shoppinglist.contract.NewCocktail;
import com.ezgroceries.shoppinglist.contract.NewShoppingList;
import com.ezgroceries.shoppinglist.contract.ShoppingList;

import java.util.List;
import java.util.UUID;

public interface ShoppingListService {
    ShoppingList returnShoppingList(UUID shoppingListId);

    List<ShoppingList> returnAllShoppingList();

    ShoppingList addShoppingList(NewShoppingList newShoppingList);

    String addCocktailToShoppingList(String shoppingListId, NewCocktail newCocktail);
}
