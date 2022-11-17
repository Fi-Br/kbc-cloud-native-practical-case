package com.ezgroceries.shoppinglist.core;

import com.ezgroceries.shoppinglist.contract.ShoppingList;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Component("GetShoppingList")
public class GetShoppingListImpl implements GetShoppingLIst {

    public ShoppingList returnShoppingList(UUID shoppingListId){
        return CreateStephaniesBirthdayhoppingList();
    };

    public List<ShoppingList> returnAllShoppingList(){
        List<ShoppingList> allShoppingList = new ArrayList<>();

        allShoppingList.add(CreateStephaniesBirthdayhoppingList());
        allShoppingList.add(CreateMyBirthdayhoppingList());

        return allShoppingList;
    }

    public ShoppingList CreateStephaniesBirthdayhoppingList(){
        UUID shoppingListId = UUID.randomUUID();
        String name = "Stephanie's birthday";

        List<String> ingredients = new ArrayList<>();
        ingredients.add("Tequilla");
        ingredients.add("Triple sec");
        ingredients.add("Lime juice");
        ingredients.add("Salt");
        ingredients.add("Blue Curacao");

        return new ShoppingList(shoppingListId,name,ingredients);
    }

    public ShoppingList CreateMyBirthdayhoppingList(){
        UUID shoppingListId = UUID.randomUUID();
        String name = "My Birthday";

        List<String> ingredients = new ArrayList<>();
        ingredients.add("Tequilla");
        ingredients.add("Triple sec");
        ingredients.add("Lime juice");
        ingredients.add("Salt");
        ingredients.add("Blue Curacao");

        return new ShoppingList(shoppingListId,name,ingredients);
    }

}
