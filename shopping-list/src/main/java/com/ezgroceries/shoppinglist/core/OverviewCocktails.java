package com.ezgroceries.shoppinglist.core;

import com.ezgroceries.shoppinglist.contract.Cocktail;

import java.util.List;

public interface OverviewCocktails {
    public List<Cocktail> returnCocktailList(String search);
}
