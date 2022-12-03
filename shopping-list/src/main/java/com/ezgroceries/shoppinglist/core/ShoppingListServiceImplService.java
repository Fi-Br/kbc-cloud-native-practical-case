package com.ezgroceries.shoppinglist.core;

import com.ezgroceries.shoppinglist.contract.NewCocktail;
import com.ezgroceries.shoppinglist.contract.NewShoppingList;
import com.ezgroceries.shoppinglist.contract.ShoppingList;
import com.ezgroceries.shoppinglist.core.exceptions.BadRequestException;
import com.ezgroceries.shoppinglist.repository.CocktailRepository;
import com.ezgroceries.shoppinglist.repository.ShoppingListRepository;
import com.ezgroceries.shoppinglist.repository.jpa.CocktailEntity;
import com.ezgroceries.shoppinglist.repository.jpa.ShoppingListEntity;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Component("ShoppingListService")
public class ShoppingListServiceImplService implements ShoppingListService {

    private ShoppingListRepository shoppingListRepository;

    private CocktailRepository cocktailRepository;

    public ShoppingListServiceImplService(ShoppingListRepository shoppingListRepository, CocktailRepository cocktailRepository){
        this.cocktailRepository = cocktailRepository;
        this.shoppingListRepository = shoppingListRepository;
    }

    public ShoppingList returnShoppingList(UUID shoppingListId){

        ShoppingListEntity shoppingListEntity = shoppingListRepository.findShoppingListEntityByShoppingListId(shoppingListId);
        if (shoppingListEntity == null){
            throw new BadRequestException();
        }

        Set<CocktailEntity> cocktailEntityList = cocktailRepository.findCocktailEntitiesByShoppingListEntitySet(shoppingListEntity);

        Set<String> ingredients = new HashSet<>();
        cocktailEntityList.forEach(cocktailEntity ->
            ingredients.addAll(cocktailEntity.getIngredients()));

        return new ShoppingList(shoppingListEntity.getShoppingListId(), shoppingListEntity.getName(), ingredients);
    }

    public List<ShoppingList> returnAllShoppingList(){
        List<ShoppingList> allShoppingLists = new ArrayList<>();

        List<ShoppingListEntity> shoppingListEntityList = shoppingListRepository.findAll();
        if (shoppingListEntityList == null){
            throw new BadRequestException();
        }
        shoppingListEntityList.forEach(shoppingListEntity1 ->
            allShoppingLists.add(returnShoppingList(shoppingListEntity1.getShoppingListId())));

        return allShoppingLists;
    }

    public ShoppingList addShoppingList(NewShoppingList newShoppingList){
        ShoppingListEntity shoppingListEntity = shoppingListRepository.findShoppingListEntityByName(newShoppingList.getName());

        if (shoppingListEntity == null){
            shoppingListEntity = shoppingListRepository.save(new ShoppingListEntity(newShoppingList.getName()));
            return new ShoppingList(shoppingListEntity.getShoppingListId(),shoppingListEntity.getName());
        }else{
            throw new BadRequestException();
        }
    }

    public String addCocktailToShoppingList(String shoppingListName, NewCocktail newCocktail){
        ShoppingListEntity shoppingListEntity = shoppingListRepository.findShoppingListEntityByName(shoppingListName);

        if (shoppingListEntity != null){
            CocktailEntity cocktailEntity = cocktailRepository.findCocktailEntityByDrinkId(newCocktail.getDrinkId());
            if (cocktailEntity != null){
                shoppingListEntity.addCocktailEntity(cocktailEntity);
                shoppingListRepository.save(shoppingListEntity);
                return cocktailEntity.getDrinkId();
            }else{
                throw new BadRequestException();
            }
        }else{
            throw new BadRequestException();
        }
    }

}
