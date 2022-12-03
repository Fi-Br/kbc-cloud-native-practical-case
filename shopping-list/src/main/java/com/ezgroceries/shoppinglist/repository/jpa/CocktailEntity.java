package com.ezgroceries.shoppinglist.repository.jpa;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name="cocktail")
public class CocktailEntity {

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

    public CocktailEntity(){}

    public CocktailEntity(String drinkId, String name, List<String> ingredients){
        this.drinkId = drinkId;
        this.name = name;
        this.ingredients = new HashSet<>(ingredients);
    }

    public void UpdateNewCocktailEntity(UUID cocktailId, String drinkId, String name, List<String> ingredients){
        this.cocktailId = cocktailId;
        this.drinkId = drinkId;
        this.name = name;
        this.ingredients = new HashSet<>(ingredients);
    }

    public void SetNewTestCocktailEnity(UUID cocktailId, String drinkId, String name, List<String> ingredients){
        this.cocktailId = cocktailId;
        this.drinkId = drinkId;
        this.name = name;
        this.ingredients = new HashSet<>(ingredients);
        this.shoppingListEntitySet = new HashSet<>();
    }
}
