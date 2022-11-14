package com.ezgroceries.shoppinglist.controller;

import com.ezgroceries.shoppinglist.EzGroceriesShoppingListApplication;
import com.ezgroceries.shoppinglist.contract.Cocktail;
import com.ezgroceries.shoppinglist.contract.ShoppingList;
import com.ezgroceries.shoppinglist.core.GetShoppingLIst;
import com.ezgroceries.shoppinglist.core.OverviewCocktails;
import com.ezgroceries.shoppinglist.core.model.CocktailDBResponse;
import com.ezgroceries.shoppinglist.repository.CocktailDBClient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest(classes = EzGroceriesShoppingListApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class ControllerTest {

    TestRestTemplate testRestTemplate = new TestRestTemplate();

    @Autowired
    MockMvc mockMvc;

    @MockBean
    private CocktailDBClient cocktailDBClient;

    @MockBean
    private GetShoppingLIst getShoppingList;

    private UUID cocktailId;
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
    public void createCocktails(){
        cocktailId = UUID.fromString("00000000-0000-0000-0000-000000000001");
        shoppingListId = UUID.fromString("00000000-0000-0000-0000-000000000001");
        name = "Margerita";
        glass = "Cocktail glass";
        instructions = "Rub the rim of the glass with the lime slice to make the salt stick to it. Take care to moisten..";
        image = "https://www.thecocktaildb.com/images/media/drink/wpxpvu1439905379.jpg";

        ingredients = new ArrayList<>();
        ingredients.add(ingredient1);
        ingredients.add(ingredient2);
        ingredients.add(ingredient3);

        margerita = new Cocktail(cocktailId,name,glass,instructions,image,ingredients);
        whiteRussian = new CocktailDBResponse.DrinkResource(cocktailId.toString(),name,glass,instructions,image,ingredient1,ingredient2,ingredient3);

        shoppingList = new ShoppingList(shoppingListId,name,ingredients);
        allShoppingLists = new ArrayList<>();
        allShoppingLists.add(shoppingList);

        cocktailDBResponse = new CocktailDBResponse();
        List<CocktailDBResponse.DrinkResource> cocktailList = new ArrayList<>();
        cocktailList.add(whiteRussian);
        cocktailDBResponse.setDrinks(cocktailList);
        List<CocktailDBResponse.DrinkResource> cocktailListOut = cocktailDBResponse.getDrinks();

        CocktailDBResponse.DrinkResource filip = cocktailListOut.get(0);
        System.out.println(filip.getIdDrink());
    }

    @Test
    public void testSuccesfullOverviewCocktails() throws Exception{
        given(cocktailDBClient.searchCocktails("russian"))
                .willReturn(cocktailDBResponse);

       mockMvc.perform(get("/cocktails?search=russian"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
//                .andExpect(jsonPath("$[0]cocktailId").value(cocktailId.toString()))
                .andExpect(jsonPath("$[0]name").value(name))
                .andExpect(jsonPath("$[0]glass").value(glass))
                .andExpect(jsonPath("$[0]instructions").value(instructions))
                .andExpect(jsonPath("$[0]image").value(image))
                .andExpect(jsonPath("$[0]ingredients").value(ingredients));

        verify(cocktailDBClient).searchCocktails("russian");
    }

    @Test
    public void testWithWrongInputOverviewCocktails() throws Exception{
        List<Cocktail> cocktails = Arrays.asList(margerita);

        given(cocktailDBClient.searchCocktails("russian"))
                .willReturn(cocktailDBResponse);

        mockMvc.perform(get("/cocktails?search=a"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").doesNotExist());
    }

    @Test
    public void testSuccesfullAddShoppingListRequest() throws Exception{

        mockMvc.perform(post("/shopping-lists")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\" : \"Filip\"}"))
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", "http://localhost/shopping-lists/Filip"));
        ;
    }

    @Test
    public void testFailAddShoppingListRequest() throws Exception{

        mockMvc.perform(post("/shopping-lists")
                .contentType(MediaType.APPLICATION_JSON)
                .content(""))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testSuccesfullAddCocktailRequest() throws Exception{

        mockMvc.perform(post("/shopping-lists/1/cocktails")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"cocktailId\" : \"Filip\"}"))
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", "http://localhost/shopping-lists/1/cocktails/Filip"));
    }

    @Test
    public void testFailWithStringAddCocktailRequest() throws Exception{

        mockMvc.perform(post("/shopping-lists/Filip/cocktails")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"cocktailId\" : \"Filip\"}"))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testFailWithEmptyJsonAddCocktailRequest() throws Exception{

        mockMvc.perform(post("/shopping-lists/Filip/cocktails")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{}"))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testSuccesfullShoppingList() throws Exception{

        given(getShoppingList.returnShoppingList(any()))
                .willReturn(shoppingList);

        mockMvc.perform(get("/shopping-lists/123e4567-e89b-12d3-a456-426614174000"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("shoppingListId").value(shoppingListId.toString()))
                .andExpect(jsonPath("name").value(name))
                .andExpect(jsonPath("ingredients").value(ingredients));

        verify(getShoppingList).returnShoppingList(any());
    }

    @Test
    public void testWithWrongInput() throws Exception{

        given(getShoppingList.returnShoppingList(any()))
                .willReturn(shoppingList);

        mockMvc.perform(get("/shopping-lists/a"))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testSuccesfullAllShoppingList() throws Exception{

        given(getShoppingList.returnAllShoppingList())
                .willReturn(allShoppingLists);

        mockMvc.perform(get("/shopping-lists"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0]shoppingListId").value(shoppingListId.toString()))
                .andExpect(jsonPath("$[0]name").value(name))
                .andExpect(jsonPath("$[0]ingredients").value(ingredients));

        verify(getShoppingList).returnAllShoppingList();
    }
}
