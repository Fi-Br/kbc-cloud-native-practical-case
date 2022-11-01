package com.ezgroceries.shoppinglist.controller;

import com.ezgroceries.shoppinglist.contract.Cocktail;
import com.ezgroceries.shoppinglist.core.OverviewCocktails;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@WebMvcTest(CocktailController.class)
public class CocktailControllerTest{

    @Autowired
    MockMvc mockMvc;

    @MockBean
    private OverviewCocktails overviewCocktails;

    private UUID cocktailId;
    private String name;
    private String glass;
    private String instructions;
    private String image;
    private List<String> ingredients;
    private Cocktail margerita;

    @BeforeEach
    public void createCocktails(){
        cocktailId = UUID.fromString("00000000-0000-0000-0000-000000000001");
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
    }

    @Test
    public void testSuccesfullRequest() throws Exception{
        List<Cocktail> cocktails = Arrays.asList(margerita);

        given(overviewCocktails.returnCocktailList("Russian"))
                .willReturn(cocktails);

        mockMvc.perform(get("/cocktails?search=Russian"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$[0]cocktailId").value(cocktailId.toString()))
            .andExpect(jsonPath("$[0]name").value(name))
            .andExpect(jsonPath("$[0]glass").value(glass))
            .andExpect(jsonPath("$[0]instructions").value(instructions))
            .andExpect(jsonPath("$[0]image").value(image))
            .andExpect(jsonPath("$[0]ingredients").value(ingredients));

        verify(overviewCocktails).returnCocktailList("Russian");
    }

}
