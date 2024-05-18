package com.nida.app_cakery.Models;

public class Ingredient {
    private String ingredientID;
    private String name;
    private boolean isVegan;
    private String alternative;
    private String category;

    private String url;

    public Ingredient(String ingredientID, String name,
                      boolean isVegan, String alternative, String category, String url) {
        this.ingredientID = ingredientID;
        this.name = name;
        this.isVegan = isVegan;
        this.alternative = alternative;
        this.category = category;
        this.url = url;
    }

    /*
        public Ingredient(String name) {
        this.name = name;
    }

     */

    public String getIngredientID() {
        return ingredientID;
    }

    public void setIngredientID(String ingredientID) {
        this.ingredientID = ingredientID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isVegan() {
        return isVegan;
    }

    public void setVegan(boolean vegan) {
        isVegan = vegan;
    }

    public String getAlternative() {
        return alternative;
    }

    public void setAlternative(String alternative) {
        this.alternative = alternative;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getUrl() { return url;}

    public void setUrl(String url) { this.url = url;}
}
