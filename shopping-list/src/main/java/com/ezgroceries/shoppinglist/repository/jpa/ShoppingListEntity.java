package com.ezgroceries.shoppinglist.repository.jpa;

import org.hsqldb.lib.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import java.util.UUID;

@Entity
@Table(name="shopping_list")
public class ShoppingListEntity {
    @Id
    @Column(name = "ID")
    private UUID shoppingListId;

    @Column(name = "TEXT")
    private String text;

    @ManyToMany
    @JoinTable(
            name = "cocktail_shopping_list",
            joinColumns = @JoinColumn(name = "shopping_list_id"),
            inverseJoinColumns = @JoinColumn(name = "cocktail_id"))
    Set<CocktailEntity> cocktailEntitySet;
}
