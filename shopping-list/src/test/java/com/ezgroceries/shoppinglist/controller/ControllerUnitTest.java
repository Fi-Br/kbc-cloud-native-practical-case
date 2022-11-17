package com.ezgroceries.shoppinglist.controller;

import com.ezgroceries.shoppinglist.contract.Cocktail;
import com.ezgroceries.shoppinglist.contract.ShoppingList;
import com.ezgroceries.shoppinglist.core.GetShoppingLIst;
import com.ezgroceries.shoppinglist.core.OverviewCocktails;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
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

@WebMvcTest(Controller.class)
public class ControllerUnitTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    private OverviewCocktails overviewCocktails;

    @MockBean
    private GetShoppingLIst getShoppingList;

    private UUID cocktailId;
    private String name;
    private String glass;
    private String instructions;
    private String image;
    private List<String> ingredients;
    private Cocktail margerita;
    private UUID shoppingListId;
    private ShoppingList shoppingList;
    private List<ShoppingList> allShoppingLists;

    @BeforeEach
    public void createCocktails(){
        cocktailId = UUID.fromString("00000000-0000-0000-0000-000000000001");
        shoppingListId = UUID.fromString("00000000-0000-0000-0000-000000000001");
        name = "Margerita";
        glass = "Cocktail glass";
        instructions = "Rub the rim of the glass with the lime slice to make the salt stick to it. Take care to moisten..";
        image = "https://www.thecocktaildb.com/images/media/drink/wpxpvu1439905379.jpg";

        ingredients = new ArrayList<>();
        ingredients.add("Tequilla");
        ingredients.add("Triple sec");
        ingredients.add("Lime juice");
        ingredients.add("Salt");

        margerita = new Cocktail(cocktailId,name,glass,instructions,image,ingredients);

        shoppingList = new ShoppingList(shoppingListId,name,ingredients);
        allShoppingLists = new ArrayList<>();
        allShoppingLists.add(shoppingList);

    }

    @Test
    public void testSuccesfullOverviewCocktails() throws Exception{
        List<Cocktail> cocktails = Arrays.asList(margerita);

        given(overviewCocktails.returnCocktailList("russian"))
                .willReturn(cocktails);

        mockMvc.perform(get("/cocktails?search=russian"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0]cocktailId").value(cocktailId.toString()))
                .andExpect(jsonPath("$[0]name").value(name))
                .andExpect(jsonPath("$[0]glass").value(glass))
                .andExpect(jsonPath("$[0]instructions").value(instructions))
                .andExpect(jsonPath("$[0]image").value(image))
                .andExpect(jsonPath("$[0]ingredients").value(ingredients));

        verify(overviewCocktails).returnCocktailList("russian");
    }

    @Test
    public void testWithWrongInputOverviewCocktails() throws Exception{
        List<Cocktail> cocktails = Arrays.asList(margerita);

        given(overviewCocktails.returnCocktailList("russian"))
                .willReturn(cocktails);

        mockMvc.perform(get("/cocktails?search=a"))
                .andExpect(status().isBadRequest());
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
