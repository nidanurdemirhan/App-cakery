package com.nida.app_cakery.Model;

public class Ingredients {
    int ingredientID;
    String name;
    boolean isVegan;
    String alternative;
    String category;

    public Ingredients(int ingredientID, String name, boolean isVegan, String alternative, String category) {
        this.ingredientID = ingredientID;
        this.name = name;
        this.isVegan = isVegan;
        this.alternative = alternative;
        this.category = category;
    }

    //getters
    public int getIngredientID() {
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
