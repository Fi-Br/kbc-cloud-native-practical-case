package com.ezgroceries.shoppinglist.repository.jpa;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name="shopping_list")
public class ShoppingListEntity {
    @Id
    @Column(name = "ID")
    private UUID shoppingListId;

    @Column(name = "NAME")
    private String name;

    @ManyToMany
    @JoinTable(
            name = "cocktail_shopping_list",
            joinColumns = @JoinColumn(name = "shopping_list_id"),
            inverseJoinColumns = @JoinColumn(name = "cocktail_id"))
    Set<CocktailEntity> cocktailEntitySet;
}
