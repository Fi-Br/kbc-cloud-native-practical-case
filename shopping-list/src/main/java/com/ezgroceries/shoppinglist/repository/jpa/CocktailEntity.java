package com.ezgroceries.shoppinglist.repository.jpa;

import org.hsqldb.lib.Set;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import java.util.UUID;

@Entity
@Table(name="cocktail")
public class CocktailEntity {

    @Id
    @Column(name = "ID")
    private UUID cocktailId;

    @Column(name = "ID_DRINK")
    private String drinkId;

    @Column(name = "NAME")
    private String name;

    @Column(name = "INGREDIENTS")
    @Convert(converter = StringSetConverter.class)
    private Set<String> ingredients;

    @ManyToMany(mappedBy = "cocktailEntitySet")
    Set<ShoppingListEntity> shoppingListEntitySet;

    public UUID getCocktailId() {
        return cocktailId;
    }

    public String getDrinkId() {
        return drinkId;
    }

    public String getName() {
        return name;
    }

    public Set<String> getIngredients() {
        return ingredients;
    }

    public Set<ShoppingListEntity> getShoppingListEntitySet() {
        return shoppingListEntitySet;
    }

    public CocktailEntity(String drinkId,String Name){
        this.drinkId = drinkId;
        this.name = name;
    }
}
