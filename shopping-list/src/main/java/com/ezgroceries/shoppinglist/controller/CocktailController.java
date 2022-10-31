package com.ezgroceries.shoppinglist.controller;

import com.ezgroceries.shoppinglist.contract.Cocktail;
import com.ezgroceries.shoppinglist.contract.NewCocktail;
import com.ezgroceries.shoppinglist.contract.NewShoppingList;
import com.ezgroceries.shoppinglist.contract.ShoppingList;
import com.ezgroceries.shoppinglist.core.OverviewCocktails;
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
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
public class CocktailController {
    private static final Logger log = LoggerFactory.getLogger(CocktailController.class);

    OverviewCocktails overviewCocktails;

    @Autowired
    public CocktailController(OverviewCocktails overviewCocktails){
        this.overviewCocktails = overviewCocktails;
    }

    @GetMapping(value = "/cocktails")
    public ResponseEntity<List<Cocktail>> getCocktails(@RequestParam("search") String search){
        List<Cocktail> cocktails = new ArrayList<>();
        log.debug("Get Cocktails requested with search " + search );

        if (search.equals("Russian")) {
            return ResponseEntity.ok(overviewCocktails.returnCocktailList(search));
        }
        return null;
    }
}
