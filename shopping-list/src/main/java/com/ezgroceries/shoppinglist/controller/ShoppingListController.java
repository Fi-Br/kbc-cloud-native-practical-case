package com.ezgroceries.shoppinglist.controller;

import com.ezgroceries.shoppinglist.contract.NewCocktail;
import com.ezgroceries.shoppinglist.contract.NewShoppingList;
import com.ezgroceries.shoppinglist.contract.ShoppingList;
import com.ezgroceries.shoppinglist.core.GetShoppingLIst;
import com.ezgroceries.shoppinglist.core.OverviewCocktails;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
public class ShoppingListController {
    private static final Logger log = LoggerFactory.getLogger(CocktailController.class);

    GetShoppingLIst getShoppingLIst;

    @Autowired
    public ShoppingListController(GetShoppingLIst getShoppingLIst){
        this.getShoppingLIst = getShoppingLIst;
    }

    @PostMapping(value= "/shopping-lists")
    public ResponseEntity<Void> addShoppingList(@RequestBody NewShoppingList newShoppingList){
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequestUri()
                .path("/{shoppingListId}")
                .buildAndExpand(newShoppingList.getName())
                .toUri();
        return ResponseEntity.created(location).build();
    }

    @PostMapping(value= "/shopping-lists/{shoppingListId}/cocktails")
    public ResponseEntity<Void> addCocktail(@PathVariable long shoppingListId, @RequestBody NewCocktail newCocktail){
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequestUri()
                .path("/{cocktailId}")
                .buildAndExpand(newCocktail.getCocktailId())
                .toUri();
        return ResponseEntity.created(location).build();
    }

    @GetMapping(value = "/shopping-lists/{shoppingListId}")
    public ResponseEntity<ShoppingList> getShoppingList(@PathVariable("shoppingListId") UUID shoppingListId){
        log.debug("Get Shopping List for shoppingListId " + shoppingListId );

        return ResponseEntity.ok( getShoppingLIst.returnShoppingList(shoppingListId));

    }

    @GetMapping(value = "/shopping-lists")
    public ResponseEntity<List<ShoppingList>> getShoppingList(){
        log.debug("Return all shopping lists");

        return ResponseEntity.ok(getShoppingLIst.returnAllShoppingList());

    }
}