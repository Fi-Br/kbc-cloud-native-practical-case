package com.ezgroceries.shoppinglist.controller;

import com.ezgroceries.shoppinglist.contract.Cocktail;
import com.ezgroceries.shoppinglist.contract.NewCocktail;
import com.ezgroceries.shoppinglist.contract.NewShoppingList;
import com.ezgroceries.shoppinglist.contract.ShoppingList;
import com.ezgroceries.shoppinglist.core.GetShoppingLIst;
import com.ezgroceries.shoppinglist.core.OverviewCocktails;
import com.ezgroceries.shoppinglist.core.exceptions.BadRequestException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    OverviewCocktails overviewCocktails;
    @Autowired
    GetShoppingLIst getShoppingLIst;

    @GetMapping(value = "/cocktails")
    public ResponseEntity<List<Cocktail>> getCocktails(@RequestParam("search") String search){
        log.info("Get Cocktails requested with search " + search );

        if (search.equals("russian")) {
            return ResponseEntity.ok(overviewCocktails.returnCocktailList(search));
        }
        throw new BadRequestException();
    }

    @PostMapping(value= "/shopping-lists")
    public ResponseEntity<Void> addShoppingList(@RequestBody NewShoppingList newShoppingList){
        log.debug("Get shopping List" );
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequestUri()
                .path("/{shoppingListId}")
                .buildAndExpand(newShoppingList.getName())
                .toUri();
        return ResponseEntity.created(location).build();
    }

    @PostMapping(value= "/shopping-lists/{shoppingListId}/cocktails")
    public ResponseEntity<Void> addCocktail(@PathVariable long shoppingListId, @RequestBody NewCocktail newCocktail){
        log.debug("shopping list - cocktails" );
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequestUri()
                .path("/{cocktailId}")
                .buildAndExpand(newCocktail.getCocktailId())
                .toUri();
        return ResponseEntity.created(location).build();
    }

    @GetMapping(value = "/shopping-lists/{shoppingListId}")
    public ResponseEntity<ShoppingList> getShoppingList(@PathVariable("shoppingListId") UUID shoppingListId){
        log.debug("shopping list " );

        return ResponseEntity.ok(getShoppingLIst.returnShoppingList(shoppingListId));

    }

    @GetMapping(value = "/shopping-lists")
    public ResponseEntity<List<ShoppingList>> getShoppingList(){
        log.debug("Return all shopping lists");

        return ResponseEntity.ok(getShoppingLIst.returnAllShoppingList());

    }
}
