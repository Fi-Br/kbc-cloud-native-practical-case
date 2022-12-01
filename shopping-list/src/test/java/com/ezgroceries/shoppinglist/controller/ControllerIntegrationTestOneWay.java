package com.ezgroceries.shoppinglist.controller;

import com.ezgroceries.shoppinglist.EzGroceriesShoppingListApplication;
import com.ezgroceries.shoppinglist.contract.Cocktail;
import com.ezgroceries.shoppinglist.contract.ShoppingList;
import com.ezgroceries.shoppinglist.core.ShoppingListService;
import com.ezgroceries.shoppinglist.core.OverviewCocktails;
import com.ezgroceries.shoppinglist.core.model.CocktailDBResponse;
import com.ezgroceries.shoppinglist.repository.CocktailDBClient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest(classes = EzGroceriesShoppingListApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ControllerIntegrationTestOneWay {

    @Autowired
    private WebApplicationContext webApplicationContext;
    private MockMvc mockMvc;

    @MockBean
    private CocktailDBClient cocktailDBClient;

    @MockBean
    private ShoppingListService shoppingListService;

    private String drinkId;
    private String name;
    private String glass;
    private String instructions;
    private String image;
    private List<String> ingredients;
    private String ingredient1 = "Tequilla";
    private String ingredient2 = "Triple sec";
    private String ingredient3 = "Lime juice";
    private String ingredient4 = "Salt";
    private CocktailDBResponse.DrinkResource whiteRussian;
    private Cocktail margerita;
    private UUID shoppingListId;
    private ShoppingList shoppingList;
    private List<ShoppingList> allShoppingLists;
    private CocktailDBResponse cocktailDBResponse;

    @Autowired
    OverviewCocktails overviewCocktails;

    @BeforeEach
    public void setup(){
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @BeforeEach
    public void createCocktails(){
        drinkId = "00000002";
        shoppingListId = UUID.fromString("00000000-0000-0000-0000-000000000001");
        name = "Margerita";
        glass = "Cocktail glass";
        instructions = "Rub the rim of the glass with the lime slice to make the salt stick to it. Take care to moisten..";
        image = "https://www.thecocktaildb.com/images/media/drink/wpxpvu1439905379.jpg";

        ingredients = new ArrayList<>();
        ingredients.add(ingredient1);
        ingredients.add(ingredient2);
        ingredients.add(ingredient3);

        margerita = new Cocktail(drinkId,name,glass,instructions,image,ingredients);
        whiteRussian = new CocktailDBResponse.DrinkResource(drinkId,name,glass,instructions,image,ingredient1,ingredient2,ingredient3);

        shoppingList = new ShoppingList(shoppingListId,name,new HashSet<>(ingredients));
        allShoppingLists = new ArrayList<>();
        allShoppingLists.add(shoppingList);

        cocktailDBResponse = new CocktailDBResponse();
        List<CocktailDBResponse.DrinkResource> cocktailList = new ArrayList<>();
        cocktailList.add(whiteRussian);
        cocktailDBResponse.setDrinks(cocktailList);

    }

    @Test
    public void testSuccesfullOverviewCocktails() throws Exception{
        given(cocktailDBClient.searchCocktails("russian"))
                .willReturn(cocktailDBResponse);

       mockMvc.perform(get("/cocktails?search=russian"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0]name").value(name))
                .andExpect(jsonPath("$[0]glass").value(glass))
                .andExpect(jsonPath("$[0]instructions").value(instructions))
                .andExpect(jsonPath("$[0]image").value(image))
                .andExpect(jsonPath("$[0]ingredients").value(ingredients));

        verify(cocktailDBClient).searchCocktails("russian");
    }

    @Test
    public void testUnSuccesfullOverviewCocktails() throws Exception{
        mockMvc.perform(get("/cocktails?search=filip"))
                .andExpect(status().isBadRequest());

    }
}
