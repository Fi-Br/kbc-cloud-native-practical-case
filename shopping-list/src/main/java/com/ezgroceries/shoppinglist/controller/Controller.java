package com.ezgroceries.shoppinglist.controller;

import com.ezgroceries.shoppinglist.contract.Cocktail;
import com.ezgroceries.shoppinglist.contract.NewCocktail;
import com.ezgroceries.shoppinglist.contract.NewShoppingList;
import com.ezgroceries.shoppinglist.contract.ShoppingList;
import com.ezgroceries.shoppinglist.core.ShoppingListService;
import com.ezgroceries.shoppinglist.core.OverviewCocktails;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
public class Controller {
    private static final Logger log = LoggerFactory.getLogger(Controller.class);

    private OverviewCocktails overviewCocktails;

    private ShoppingListService shoppingListService;

    public Controller(OverviewCocktails overviewCocktails, ShoppingListService shoppingListService){
        this.shoppingListService = shoppingListService;
        this.overviewCocktails = overviewCocktails;
    }

    @GetMapping(value = "/cocktails")
    public ResponseEntity<List<Cocktail>> getCocktails(@RequestParam("search") String search){
        log.info("Get Cocktails requested with search " + search );

        return ResponseEntity.ok(overviewCocktails.returnCocktailList(search));
    }

    @PostMapping(value= "/shopping-lists")
    public ResponseEntity<Void> addShoppingList(@RequestBody NewShoppingList newShoppingList){
        log.debug("Add shopping List" );
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequestUri()
                .path("/{shoppingListName}")
                .buildAndExpand(shoppingListService.addShoppingList(newShoppingList).getName())
                .toUri();
        return ResponseEntity.created(location).build();
    }

    @PostMapping(value= "/shopping-lists/{shoppingListName}/cocktails")
    public ResponseEntity<Void> addCocktail(@PathVariable String shoppingListName, @RequestBody NewCocktail newCocktail){
        log.debug("Add cocktail to shopping list" );
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequestUri()
                .path("/{cocktailId}")
                .buildAndExpand(shoppingListService.addCocktailToShoppingList(shoppingListName, newCocktail))
                .toUri();
        return ResponseEntity.created(location).build();
    }

    @GetMapping(value = "/shopping-lists/{shoppingListId}")
    public ResponseEntity<ShoppingList> getShoppingList(@PathVariable("shoppingListId") UUID shoppingListId){
        log.debug("shopping list " );

        return ResponseEntity.ok(shoppingListService.returnShoppingList(shoppingListId));

    }

    @GetMapping(value = "/shopping-lists")
    public ResponseEntity<List<ShoppingList>> getShoppingList(){
        log.debug("Return all shopping lists");

        return ResponseEntity.ok(shoppingListService.returnAllShoppingList());

    }
}
