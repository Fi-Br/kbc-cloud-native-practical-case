package com.ezgroceries.shoppinglist.core;

import com.ezgroceries.shoppinglist.contract.Cocktail;
import com.ezgroceries.shoppinglist.core.exceptions.BadRequestException;
import com.ezgroceries.shoppinglist.core.model.CocktailDBResponse;
import com.ezgroceries.shoppinglist.repository.CocktailDBClient;
import com.ezgroceries.shoppinglist.repository.CocktailRepository;
import com.ezgroceries.shoppinglist.repository.ShoppingListRepository;
import com.ezgroceries.shoppinglist.repository.jpa.CocktailEntity;
import com.ezgroceries.shoppinglist.repository.jpa.ShoppingListEntity;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.ezgroceries.TestVariables.COCKTAIL_ID;
import static com.ezgroceries.TestVariables.COCKTAIL_NAME;
import static com.ezgroceries.TestVariables.COCKTAIL_SEARCH;
import static com.ezgroceries.TestVariables.DRINK_ID;
import static com.ezgroceries.TestVariables.DRINK_NAME;
import static com.ezgroceries.TestVariables.DRINK_THUMB;
import static com.ezgroceries.TestVariables.GLASS;
import static com.ezgroceries.TestVariables.INGREDIENT1;
import static com.ezgroceries.TestVariables.INGREDIENT2;
import static com.ezgroceries.TestVariables.INGREDIENT3;
import static com.ezgroceries.TestVariables.INGREDIENTS;
import static com.ezgroceries.TestVariables.INSTRUCTIONS;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.mockito.internal.verification.VerificationModeFactory.times;

@ExtendWith(MockitoExtension.class)
class OverviewCocktailServiceImplServiceTest {
    @Mock
    private ShoppingListRepository shoppingListRepository;

    @Mock
    private CocktailRepository cocktailRepository;

    @Mock
    private CocktailEntity cocktailEntity;

    @Mock
    private ShoppingListEntity shoppingListEntity;

    @Mock
    private CocktailDBClient cocktailDBClient;

    private CocktailDBResponse cocktailDBResponse;

    private OverviewCocktailsImpl overviewCocktails;

    @BeforeEach
    public void setUp() {
        overviewCocktails = new OverviewCocktailsImpl(cocktailDBClient,cocktailRepository);
        cocktailDBResponse = new CocktailDBResponse();
    }

    //*******************************
    //Return overview Cocktails
    //*******************************
    //Successful test
    @Test
    public void testSuccessfulReturnOverviewCocktailsList(){
        cocktailEntity = new CocktailEntity();
        cocktailEntity.SetNewTestCocktailEnity(COCKTAIL_ID, DRINK_ID, COCKTAIL_NAME, INGREDIENTS);

        CocktailDBResponse.DrinkResource drinkResource = new CocktailDBResponse.DrinkResource(DRINK_ID,DRINK_NAME,GLASS,INSTRUCTIONS,DRINK_THUMB,INGREDIENT1,INGREDIENT2,INGREDIENT3);
        cocktailDBResponse.setDrinks(new ArrayList<>(Arrays.asList(drinkResource)));

        given(cocktailDBClient.searchCocktails(COCKTAIL_SEARCH))
            .willReturn(cocktailDBResponse);
        given(cocktailRepository.save(any()))
            .willReturn(cocktailEntity);

        List<Cocktail> cocktailList = overviewCocktails.returnCocktailList(COCKTAIL_SEARCH);
        assertThat(cocktailList.get(0).getDrinkId().equals(DRINK_ID)).isTrue();
        assertThat(cocktailList.get(0).getName().equals(DRINK_NAME)).isTrue();
        assertThat(cocktailList.get(0).getGlass().equals(GLASS)).isTrue();
        assertThat(cocktailList.get(0).getImage().equals(DRINK_THUMB)).isTrue();
        assertThat(cocktailList.get(0).getInstructions().equals(INSTRUCTIONS)).isTrue();
        assertThat(cocktailList.get(0).getIngredients().equals(INGREDIENTS)).isTrue();

        verify(cocktailDBClient, times(1)).searchCocktails(COCKTAIL_SEARCH);
        verify(cocktailRepository,times(1)).save(any());
    }

    //Test with not known ShoppingListId
    @Test
    public void testWithEmptyShoppingListReturnNull(){
        given(cocktailDBClient.searchCocktails(COCKTAIL_SEARCH))
                .willReturn(null);

        Assertions.assertThrows(BadRequestException.class, () -> overviewCocktails.returnCocktailList(COCKTAIL_SEARCH));

        verify(cocktailDBClient, times(1)).searchCocktails(COCKTAIL_SEARCH);
    }
}