package com.ezgroceries.shoppinglist.repository.jpa;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name="shopping_list")
public class ShoppingListEntity {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator",
            parameters = {
                    @Parameter(
                            name = "uuid_gen_strategy_class",
                            value = "org.hibernate.id.uuid.CustomVersionOneStrategy"
                    )
            }
    )
    @Column(name = "ID", updatable = false, nullable = false)
    private UUID shoppingListId;

    @Column(name = "NAME")
    private String name;

    @ManyToMany
    @JoinTable(
            name = "cocktail_shopping_list",
            joinColumns = @JoinColumn(name = "shopping_list_id"),
            inverseJoinColumns = @JoinColumn(name = "cocktail_id"))
    Set<CocktailEntity> cocktailEntitySet;

    public ShoppingListEntity(){}

    public ShoppingListEntity(String name){
        this.name = name;
    }

    public UUID getShoppingListId() {
        return shoppingListId;
    }

    public String getName() {
        return name;
    }

    public Set<CocktailEntity> getCocktailEntitySet() {
        return cocktailEntitySet;
    }

    public void addCocktailEntity(CocktailEntity cocktailEntity){
        cocktailEntitySet.add(cocktailEntity);
        cocktailEntity.getShoppingListEntitySet().add(this);
    }

    public void setTestShoppingListID(UUID shoppingListId, String name){
        this.shoppingListId = shoppingListId;
        this.name = name;
        this.cocktailEntitySet = new HashSet<>();
    }

    public void addCocktailEntityList(Set<CocktailEntity> cocktailEntityList){
        this.cocktailEntitySet = cocktailEntityList;
    }
}
