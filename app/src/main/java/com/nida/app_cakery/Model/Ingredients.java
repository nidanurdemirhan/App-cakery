package com.nida.app_cakery.Model;

public class Ingredients {
    String ingredientID;
    String name;
    boolean isVegan;
    String alternative;
    String category;

    public Ingredients(String name) {
        this.name = name;
    }

    //getters
    public String getIngredientID() {
        return ingredientID;
    }

    public String getName() {
        return name;
    }

    public boolean isVegan() {
        return isVegan;
    }

    public String getAlternative() {
        return alternative;
    }

    public String getCategory() {
        return category;
    }
}
