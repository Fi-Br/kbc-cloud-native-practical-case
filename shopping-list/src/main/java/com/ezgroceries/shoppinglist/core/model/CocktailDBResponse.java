package com.ezgroceries.shoppinglist.core.model;

import java.util.ArrayList;
import java.util.List;

public class CocktailDBResponse {
    private List<DrinkResource> drinks;

    public List<DrinkResource> getDrinks(){
        return drinks;
    }

    public void setDrinks(List<DrinkResource> drinks){
        this.drinks = drinks;
    }

    public static class DrinkResource{
        private String idDrink;
        private String strDrink;
        private String strGlass;
        private String strInstructions;
        private String strDrinkThumb;
        private String strIngredient1;
        private String strIngredient2;
        private String strIngredient3;

        public DrinkResource(String idDrink, String strDrink, String strGlass, String strInstructions, String strDrinkThumb, String strIngredient1, String strIngredient2, String strIngredient3){
            this.idDrink = idDrink;
            this.strDrink = strDrink;
            this.strGlass = strGlass;
            this.strInstructions = strInstructions;
            this.strDrinkThumb = strDrinkThumb;
            this.strIngredient1 = strIngredient1;
            this.strIngredient2 = strIngredient2;
            this.strIngredient3 = strIngredient3;
        }

        public String getIdDrink() {
            return idDrink;
        }

        public String getStrDrink() {
            return strDrink;
        }

        public String getStrGlass() {
            return strGlass;
        }

        public String getStrInstructions() {
            return strInstructions;
        }

        public String getStrDrinkThumb() {
            return strDrinkThumb;
        }

        public String getStrIngredient1() {
            return strIngredient1;
        }

        public String getStrIngredient2() {
            return strIngredient2;
        }

        public String getStrIngredient3() {
            return strIngredient3;
        }

        public List<String> returnIngredientsAsList(){
            List<String> ingredients = new ArrayList<>();
            if (this.strIngredient1!=null){
                ingredients.add(this.strIngredient1);
            }

            if (this.strIngredient2!=null){
                ingredients.add(this.strIngredient2);
            }

            if (this.strIngredient3!=null){
                ingredients.add(this.strIngredient3);
            }

            return ingredients;
        }
    }
}
