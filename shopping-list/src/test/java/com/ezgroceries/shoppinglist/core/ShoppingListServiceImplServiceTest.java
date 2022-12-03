package com.ezgroceries.shoppinglist.core;

import com.ezgroceries.shoppinglist.contract.NewCocktail;
import com.ezgroceries.shoppinglist.contract.NewShoppingList;
import com.ezgroceries.shoppinglist.contract.ShoppingList;
import com.ezgroceries.shoppinglist.core.exceptions.BadRequestException;
import com.ezgroceries.shoppinglist.repository.CocktailRepository;
import com.ezgroceries.shoppinglist.repository.ShoppingListRepository;
import com.ezgroceries.shoppinglist.repository.jpa.CocktailEntity;
import com.ezgroceries.shoppinglist.repository.jpa.ShoppingListEntity;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import static com.ezgroceries.TestVariables.COCKTAIL_ID;
import static com.ezgroceries.TestVariables.COCKTAIL_NAME;
import static com.ezgroceries.TestVariables.DRINK_ID;
import static com.ezgroceries.TestVariables.INGREDIENTS;
import static com.ezgroceries.TestVariables.SHOPPING_LIST_ID;
import static com.ezgroceries.TestVariables.SHOPPING_LIST_NAME;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.mockito.internal.verification.VerificationModeFactory.times;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {
        ShoppingListServiceImplService.class
})
class ShoppingListServiceImplServiceTest {
    @MockBean
    private ShoppingListRepository shoppingListRepository;

    @MockBean
    private CocktailRepository cocktailRepository;

    @Mock
    private CocktailEntity cocktailEntity;

    @Mock
    private ShoppingListEntity shoppingListEntity;

    @Autowired
    private ShoppingListServiceImplService shoppingListService;

    //*******************************
    //Return One shopping List tests
    //*******************************
    //Successful test
    @Test
    public void testSuccessfulReturnShoppingList(){
        shoppingListEntity = new ShoppingListEntity();

        cocktailEntity = new CocktailEntity();
        cocktailEntity.SetNewTestCocktailEnity(COCKTAIL_ID, DRINK_ID, COCKTAIL_NAME, INGREDIENTS);
        shoppingListEntity.setTestShoppingListID(SHOPPING_LIST_ID, SHOPPING_LIST_NAME);
        shoppingListEntity.addCocktailEntity(cocktailEntity);

        given(shoppingListRepository.findShoppingListEntityByShoppingListId(SHOPPING_LIST_ID))
            .willReturn(shoppingListEntity);
        given(cocktailRepository.findCocktailEntitiesByShoppingListEntitySet(shoppingListEntity))
            .willReturn(new HashSet<>(Arrays.asList(cocktailEntity)));

        ShoppingList shoppingListResult = shoppingListService.returnShoppingList(SHOPPING_LIST_ID);

        assertThat(shoppingListResult.getShoppingListId().equals(SHOPPING_LIST_ID)).isTrue();
        assertThat(shoppingListResult.getName().equals(SHOPPING_LIST_NAME)).isTrue();
        assertThat(shoppingListResult.getIngredients().equals(new HashSet<>(INGREDIENTS))).isTrue();

        verify(shoppingListRepository, times(1)).findShoppingListEntityByShoppingListId(SHOPPING_LIST_ID);
        verify(cocktailRepository,times(1)).findCocktailEntitiesByShoppingListEntitySet(shoppingListEntity);

    }

    //Test with not known ShoppingListId
    @Test
    public void testWithEmptyShoppingListReturnNull(){
        given(shoppingListRepository.findShoppingListEntityByShoppingListId(SHOPPING_LIST_ID))
                .willReturn(null);

        Assertions.assertThrows(BadRequestException.class, () -> shoppingListService.returnShoppingList(SHOPPING_LIST_ID));

        verify(shoppingListRepository, times(1)).findShoppingListEntityByShoppingListId(SHOPPING_LIST_ID);
    }

    //Test with not linked Cocktail
    @Test
    public void testWithNoCocktailInShoppingListReturnsShoppingListWithoutIngredients(){
        shoppingListEntity = new ShoppingListEntity();

        shoppingListEntity.setTestShoppingListID(SHOPPING_LIST_ID, SHOPPING_LIST_NAME);

        given(shoppingListRepository.findShoppingListEntityByShoppingListId(SHOPPING_LIST_ID))
                .willReturn(shoppingListEntity);
        given(cocktailRepository.findCocktailEntitiesByShoppingListEntitySet(shoppingListEntity))
                .willReturn(new HashSet<>());//empty list

        ShoppingList shoppingListResult = shoppingListService.returnShoppingList(SHOPPING_LIST_ID);

        assertThat(shoppingListResult.getShoppingListId().equals(SHOPPING_LIST_ID)).isTrue();
        assertThat(shoppingListResult.getName().equals(SHOPPING_LIST_NAME)).isTrue();
        assertThat(shoppingListResult.getIngredients().equals(new HashSet<>())).isTrue();

        verify(shoppingListRepository, times(1)).findShoppingListEntityByShoppingListId(SHOPPING_LIST_ID);
        verify(cocktailRepository, times(1)).findCocktailEntitiesByShoppingListEntitySet(shoppingListEntity);
    }

    //*******************************
    //Return all shopping Lists tests
    //*******************************
    //Successful test
    @Test
    public void testSuccessfulReturnShoppingLists(){
        shoppingListEntity = new ShoppingListEntity();

        cocktailEntity = new CocktailEntity();
        cocktailEntity.SetNewTestCocktailEnity(COCKTAIL_ID, DRINK_ID, COCKTAIL_NAME, INGREDIENTS);
        shoppingListEntity.setTestShoppingListID(SHOPPING_LIST_ID, SHOPPING_LIST_NAME);
        shoppingListEntity.addCocktailEntity(cocktailEntity);

        given(shoppingListRepository.findAll())
                .willReturn(new ArrayList<>(Arrays.asList(shoppingListEntity)));
        given(shoppingListRepository.findShoppingListEntityByShoppingListId(SHOPPING_LIST_ID))
                .willReturn(shoppingListEntity);
        given(cocktailRepository.findCocktailEntitiesByShoppingListEntitySet(shoppingListEntity))
                .willReturn(new HashSet<>(Arrays.asList(cocktailEntity)));

        List<ShoppingList> shoppingListResult = shoppingListService.returnAllShoppingList();

        assertThat(shoppingListResult.get(0).getShoppingListId().equals(SHOPPING_LIST_ID)).isTrue();
        assertThat(shoppingListResult.get(0).getName().equals(SHOPPING_LIST_NAME)).isTrue();
        assertThat(shoppingListResult.get(0).getIngredients().equals(new HashSet<>(INGREDIENTS))).isTrue();

        verify(shoppingListRepository, times(1)).findAll();
        verify(shoppingListRepository, times(1)).findShoppingListEntityByShoppingListId(SHOPPING_LIST_ID);
        verify(cocktailRepository, times(1)).findCocktailEntitiesByShoppingListEntitySet(shoppingListEntity);
    }

    //Test without shoppingList
    @Test
    public void testWithNoShoppingLists(){
        given(shoppingListRepository.findAll())
                .willReturn(null);

        Assertions.assertThrows(BadRequestException.class, () -> shoppingListService.returnAllShoppingList());

        verify(shoppingListRepository, times(1)).findAll();
    }

    //*******************************
    //Add shopping List tests
    //*******************************
    //Successful test
    @Test
    public void testSuccessfulAddShoppingLists(){
        shoppingListEntity = new ShoppingListEntity();
        shoppingListEntity.setTestShoppingListID(SHOPPING_LIST_ID,SHOPPING_LIST_NAME);

        NewShoppingList newShoppingList = new NewShoppingList();
        newShoppingList.setName(SHOPPING_LIST_NAME);

        given(shoppingListRepository.findShoppingListEntityByName(SHOPPING_LIST_NAME))
                .willReturn(null);
        given(shoppingListRepository.save(any()))
                .willReturn(shoppingListEntity);

        ShoppingList shoppingListResult = shoppingListService.addShoppingList(newShoppingList);

        assertThat(shoppingListResult.getShoppingListId().equals(SHOPPING_LIST_ID)).isTrue();
        assertThat(shoppingListResult.getName().equals(SHOPPING_LIST_NAME)).isTrue();

        verify(shoppingListRepository, times(1)).findShoppingListEntityByName(SHOPPING_LIST_NAME);
        verify(shoppingListRepository, times(1)).save(any());
    }

    //Test add already existing shopping list
    @Test
    public void testWithExistingShoppingList(){
        shoppingListEntity = new ShoppingListEntity();
        shoppingListEntity.setTestShoppingListID(SHOPPING_LIST_ID,SHOPPING_LIST_NAME);

        NewShoppingList newShoppingList = new NewShoppingList();
        newShoppingList.setName(SHOPPING_LIST_NAME);

        given(shoppingListRepository.findShoppingListEntityByName(SHOPPING_LIST_NAME))
                .willReturn(shoppingListEntity);

        Assertions.assertThrows(BadRequestException.class, () -> shoppingListService.addShoppingList(newShoppingList));

        verify(shoppingListRepository, times(1)).findShoppingListEntityByName(SHOPPING_LIST_NAME);
    }

    //*******************************
    //Add cocktail to shopping list tests
    //*******************************
    //Successful test
    @Test
    public void testSuccessfulAddCocktailToShoppingLists(){
        shoppingListEntity = new ShoppingListEntity();
        shoppingListEntity.setTestShoppingListID(SHOPPING_LIST_ID,SHOPPING_LIST_NAME);
        cocktailEntity = new CocktailEntity();
        cocktailEntity.SetNewTestCocktailEnity(COCKTAIL_ID,DRINK_ID,COCKTAIL_NAME,INGREDIENTS);

        NewCocktail newCocktail = new NewCocktail();
        newCocktail.setDrinkId(DRINK_ID);

        given(shoppingListRepository.findShoppingListEntityByName(SHOPPING_LIST_NAME))
                .willReturn(shoppingListEntity);
        given(cocktailRepository.findCocktailEntityByDrinkId(newCocktail.getDrinkId()))
                .willReturn(cocktailEntity);

        given(shoppingListRepository.save(any()))
                .willReturn(shoppingListEntity);

        String result = shoppingListService.addCocktailToShoppingList(SHOPPING_LIST_NAME,newCocktail);

        assertThat(result.equals(DRINK_ID)).isTrue();

        verify(shoppingListRepository, times(1)).findShoppingListEntityByName(SHOPPING_LIST_NAME);
        verify(cocktailRepository, times(1)).findCocktailEntityByDrinkId(newCocktail.getDrinkId());
        verify(shoppingListRepository, times(1)).save(shoppingListEntity);
    }

    //Test with not existing shopping list
    @Test
    public void testNotExistingShoppingList(){
        shoppingListEntity = new ShoppingListEntity();
        shoppingListEntity.setTestShoppingListID(SHOPPING_LIST_ID,SHOPPING_LIST_NAME);
        cocktailEntity = new CocktailEntity();
        cocktailEntity.SetNewTestCocktailEnity(COCKTAIL_ID,DRINK_ID,COCKTAIL_NAME,INGREDIENTS);

        NewCocktail newCocktail = new NewCocktail();
        newCocktail.setDrinkId(DRINK_ID);

        given(shoppingListRepository.findShoppingListEntityByName(SHOPPING_LIST_NAME))
                .willReturn(null);
        given(cocktailRepository.findCocktailEntityByDrinkId(newCocktail.getDrinkId()))
                .willReturn(cocktailEntity);

        given(shoppingListRepository.save(any()))
                .willReturn(shoppingListEntity);

        Assertions.assertThrows(BadRequestException.class, () -> shoppingListService.addCocktailToShoppingList(SHOPPING_LIST_NAME,newCocktail));

        verify(shoppingListRepository, times(1)).findShoppingListEntityByName(SHOPPING_LIST_NAME);
        verify(cocktailRepository, times(0)).findCocktailEntityByDrinkId(newCocktail.getDrinkId());
        verify(shoppingListRepository, times(0)).save(shoppingListEntity);
    }

    //Test with not existing shopping list
    @Test
    public void testNotExistingCocktailId(){
        shoppingListEntity = new ShoppingListEntity();
        shoppingListEntity.setTestShoppingListID(SHOPPING_LIST_ID,SHOPPING_LIST_NAME);
        cocktailEntity = new CocktailEntity();
        cocktailEntity.SetNewTestCocktailEnity(COCKTAIL_ID,DRINK_ID,COCKTAIL_NAME,INGREDIENTS);

        NewCocktail newCocktail = new NewCocktail();
        newCocktail.setDrinkId(DRINK_ID);

        given(shoppingListRepository.findShoppingListEntityByName(SHOPPING_LIST_NAME))
                .willReturn(shoppingListEntity);
        given(cocktailRepository.findCocktailEntityByDrinkId(newCocktail.getDrinkId()))
                .willReturn(null);

        given(shoppingListRepository.save(any()))
                .willReturn(shoppingListEntity);

        Assertions.assertThrows(BadRequestException.class, () -> shoppingListService.addCocktailToShoppingList(SHOPPING_LIST_NAME,newCocktail));

        verify(shoppingListRepository, times(1)).findShoppingListEntityByName(SHOPPING_LIST_NAME);
        verify(cocktailRepository, times(1)).findCocktailEntityByDrinkId(newCocktail.getDrinkId());
        verify(shoppingListRepository, times(0)).save(shoppingListEntity);
    }
}