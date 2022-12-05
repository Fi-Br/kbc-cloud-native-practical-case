package com.ezgroceries.shoppinglist.controller;

import com.ezgroceries.shoppinglist.EzGroceriesShoppingListApplication;
import com.ezgroceries.shoppinglist.contract.ShoppingList;
import com.ezgroceries.shoppinglist.core.ShoppingListService;
import com.ezgroceries.shoppinglist.core.OverviewCocktails;
import com.ezgroceries.shoppinglist.core.model.CocktailDBResponse;
import com.ezgroceries.shoppinglist.repository.CocktailDBClient;
import com.ezgroceries.shoppinglist.repository.CocktailRepository;
import com.ezgroceries.shoppinglist.repository.ShoppingListRepository;
import com.ezgroceries.shoppinglist.repository.jpa.CocktailEntity;
import com.ezgroceries.shoppinglist.repository.jpa.ShoppingListEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import static com.ezgroceries.TestVariables.COCKTAIL_ID;
import static com.ezgroceries.TestVariables.DRINK_ID;
import static com.ezgroceries.TestVariables.DRINK_NAME;
import static com.ezgroceries.TestVariables.DRINK_THUMB;
import static com.ezgroceries.TestVariables.GLASS;
import static com.ezgroceries.TestVariables.INGREDIENT1;
import static com.ezgroceries.TestVariables.INGREDIENT2;
import static com.ezgroceries.TestVariables.INGREDIENT3;
import static com.ezgroceries.TestVariables.INGREDIENTS;
import static com.ezgroceries.TestVariables.INSTRUCTIONS;
import static com.ezgroceries.TestVariables.SHOPPING_LIST_ID;
import static com.ezgroceries.TestVariables.SHOPPING_LIST_NAME;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(
        classes = EzGroceriesShoppingListApplication.class,
        webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
@TestPropertySource(
        locations = "classpath:application-hsqldb.properties"
)
public class ControllerIntegrationTestAnotherWay {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CocktailDBClient cocktailDBClient;

    @Autowired
    ShoppingListService shoppingListService;

    @Resource
    ShoppingListRepository shoppingListRepository;

    @Autowired
    OverviewCocktails overviewCocktails;

    @Autowired
    CocktailRepository cocktailRepository;

    private CocktailDBResponse.DrinkResource whiteRussian;
    private ShoppingList shoppingList;
    private List<ShoppingList> allShoppingLists;
    private CocktailDBResponse cocktailDBResponse;
    private CocktailEntity cocktailEntity;
    private ShoppingListEntity shoppingListEntity;

    @BeforeEach
    public void createCocktails(){
        whiteRussian = new CocktailDBResponse.DrinkResource(DRINK_ID,DRINK_NAME,GLASS,INSTRUCTIONS,DRINK_THUMB,INGREDIENT1,INGREDIENT2,INGREDIENT3);

        shoppingList = new ShoppingList(SHOPPING_LIST_ID,SHOPPING_LIST_NAME,new HashSet<>(INGREDIENTS));
        allShoppingLists = new ArrayList<>();
        allShoppingLists.add(shoppingList);

        cocktailDBResponse = new CocktailDBResponse();
        List<CocktailDBResponse.DrinkResource> cocktailList = new ArrayList<>();
        cocktailList.add(whiteRussian);
        cocktailDBResponse.setDrinks(cocktailList);
        cocktailEntity = new CocktailEntity();
        shoppingListEntity = new ShoppingListEntity();

        //clean h2 database before each test
        shoppingListRepository.deleteAll();
        cocktailRepository.deleteAll();
    }

    //*******************************
    //Test Overview Cocktails
    //*******************************
    //Successful test
    @Test
    public void testSuccessfulOverviewCocktails() throws Exception{
        given(cocktailDBClient.searchCocktails(DRINK_NAME))
                .willReturn(cocktailDBResponse);

       mockMvc.perform(get("/cocktails?search=" + DRINK_NAME))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0]name").value(DRINK_NAME))
                .andExpect(jsonPath("$[0]glass").value(GLASS))
                .andExpect(jsonPath("$[0]instructions").value(INSTRUCTIONS))
                .andExpect(jsonPath("$[0]image").value(DRINK_THUMB))
                .andExpect(jsonPath("$[0]ingredients.[0]").value(INGREDIENT1))
                .andExpect(jsonPath("$[0]ingredients.[1]").value(INGREDIENT2))
                .andExpect(jsonPath("$[0]ingredients.[2]").value(INGREDIENT3));

        verify(cocktailDBClient).searchCocktails(DRINK_NAME);
    }

    //unsuccessful test
    @Test
    public void testUnSuccessfulOverviewCocktails() throws Exception{
        given(cocktailDBClient.searchCocktails(DRINK_NAME))
                .willReturn(null);

        mockMvc.perform(get("/cocktails?search=" + DRINK_NAME))
                .andExpect(status().isBadRequest());

        verify(cocktailDBClient).searchCocktails(DRINK_NAME);
    }

    //*******************************
    //Add shopping list
    //*******************************
    //Successful test
    @Test
    public void testSuccessfulAddShoppingList() throws Exception{

        mockMvc.perform(post("/shopping-lists")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\" : \"" + SHOPPING_LIST_NAME + "\"}"))
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", "http://localhost/shopping-lists/" + SHOPPING_LIST_NAME));
    }

    //Unsuccessful test
    @Test
    public void testUnSuccessfulAddShoppingList() throws Exception{
        shoppingListEntity.setTestShoppingListID(SHOPPING_LIST_ID, SHOPPING_LIST_NAME);
        shoppingListRepository.save(shoppingListEntity);

        mockMvc.perform(post("/shopping-lists")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\" : \"" + SHOPPING_LIST_NAME + "\"}"))
                .andExpect(status().isBadRequest());
    }

    //*******************************
    //Add Cocktail to shopping list
    //*******************************
    //Successful test
    @Test
    public void testSuccessfulAddCocktailToShoppingList() throws Exception{
        shoppingListEntity.setTestShoppingListID(SHOPPING_LIST_ID, SHOPPING_LIST_NAME);
        shoppingListRepository.save(shoppingListEntity);

        cocktailEntity.SetNewTestCocktailEnity(COCKTAIL_ID,DRINK_ID,DRINK_NAME,INGREDIENTS);
        cocktailRepository.save(cocktailEntity);
        mockMvc.perform(post("/shopping-lists/" + SHOPPING_LIST_NAME + "/cocktails")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"drinkId\" : \"" + DRINK_ID + "\"}"))
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", "http://localhost/shopping-lists/" + SHOPPING_LIST_NAME + "/cocktails/" + DRINK_ID));
    }

    //Unsuccessful test with unknown shopping list
    @Test
    public void testAddCocktailToUnknownShoppingList() throws Exception{
        mockMvc.perform(post("/shopping-lists/" + SHOPPING_LIST_NAME + "/cocktails")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"drinkId\" : \"" + DRINK_ID + "\"}"))
                .andExpect(status().isBadRequest());
    }

    //Unsuccessful test with unknown cocktail
    @Test
    public void testAddUnknownCocktailToKnownShoppingList() throws Exception{
        shoppingListEntity.setTestShoppingListID(SHOPPING_LIST_ID, SHOPPING_LIST_NAME);
        shoppingListRepository.save(shoppingListEntity);

        mockMvc.perform(post("/shopping-lists/" + SHOPPING_LIST_NAME + "/cocktails")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"drinkId\" : \"" + DRINK_ID + "\"}"))
                .andExpect(status().isBadRequest());
    }

    //*******************************
    //Test get one shopping list
    //*******************************
    //Successful test
    @Test
    public void testSuccessfulGetOneShoppingList() throws Exception{
        shoppingListEntity.setTestShoppingListID(SHOPPING_LIST_ID, SHOPPING_LIST_NAME);
        shoppingListEntity = shoppingListRepository.save(shoppingListEntity);

        cocktailEntity.SetNewTestCocktailEnity(COCKTAIL_ID,DRINK_ID,DRINK_NAME,INGREDIENTS);
        cocktailEntity = cocktailRepository.save(cocktailEntity);

        shoppingListEntity.addCocktailEntity(cocktailEntity);
        shoppingListEntity = shoppingListRepository.save(shoppingListEntity);

        mockMvc.perform(get("/shopping-lists/" + shoppingListEntity.getShoppingListId()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("name").value(SHOPPING_LIST_NAME))
                .andExpect(jsonPath("ingredients").isArray())
                .andExpect(jsonPath("ingredients").isNotEmpty());
    }

    //unsuccessful test
    @Test
    public void testUnSuccessfulGetOneShoppingList() throws Exception{
        shoppingListEntity.setTestShoppingListID(SHOPPING_LIST_ID, SHOPPING_LIST_NAME);
        shoppingListEntity = shoppingListRepository.save(shoppingListEntity);

        cocktailEntity.SetNewTestCocktailEnity(COCKTAIL_ID,DRINK_ID,DRINK_NAME,INGREDIENTS);
        cocktailEntity = cocktailRepository.save(cocktailEntity);

        shoppingListEntity.addCocktailEntity(cocktailEntity);
        shoppingListEntity = shoppingListRepository.save(shoppingListEntity);

        mockMvc.perform(get("/shopping-lists/" + SHOPPING_LIST_ID))
                .andExpect(status().isBadRequest());
    }

    //unsuccessful test2
    @Test
    public void testShoppingListWithoutCocktail() throws Exception{
        shoppingListEntity.setTestShoppingListID(SHOPPING_LIST_ID, SHOPPING_LIST_NAME);
        shoppingListEntity = shoppingListRepository.save(shoppingListEntity);

        mockMvc.perform(get("/shopping-lists/" + shoppingListEntity.getShoppingListId()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("name").value(SHOPPING_LIST_NAME))
                .andExpect(jsonPath("ingredients").isArray())
                .andExpect(jsonPath("ingredients").isEmpty());
    }

    //*******************************
    //Test get all shopping lists
    //*******************************
    //Successful test
    @Test
    public void testSuccessfulGetAllShoppingLists() throws Exception{
        shoppingListEntity.setTestShoppingListID(SHOPPING_LIST_ID, SHOPPING_LIST_NAME);
        shoppingListEntity = shoppingListRepository.save(shoppingListEntity);

        cocktailEntity.SetNewTestCocktailEnity(COCKTAIL_ID,DRINK_ID,DRINK_NAME,INGREDIENTS);
        cocktailEntity = cocktailRepository.save(cocktailEntity);

        shoppingListEntity.addCocktailEntity(cocktailEntity);
        shoppingListEntity = shoppingListRepository.save(shoppingListEntity);

        mockMvc.perform(get("/shopping-lists"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].name").value(SHOPPING_LIST_NAME))
                .andExpect(jsonPath("$[0].ingredients").isArray())
                .andExpect(jsonPath("$[0].ingredients").isNotEmpty());
    }

    //Successful test
    @Test
    public void testUnSuccessfulGetAllShoppingLists() throws Exception{
        mockMvc.perform(get("/shopping-lists"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }
}
