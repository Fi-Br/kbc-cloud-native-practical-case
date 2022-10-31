package com.ezgroceries.shoppinglist.core;

import com.ezgroceries.shoppinglist.contract.Cocktail;
import com.ezgroceries.shoppinglist.contract.ShoppingList;

import java.util.List;
import java.util.UUID;

public interface GetShoppingLIst {
    public ShoppingList returnShoppingList(UUID shoppingListId);

    public List<ShoppingList> returnAllShoppingList();
}
