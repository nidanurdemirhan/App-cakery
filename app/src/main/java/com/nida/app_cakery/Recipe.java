package com.nida.app_cakery;

import java.util.ArrayList;

public class Recipe {
    private String recipeID, name, description;
    private ArrayList<IngredientInRecipe> ingredientInRecipe;
    private double calorie;
    private int portion;
    private boolean isDefault;

    public Recipe(String recipeID,String name, String description,
                  ArrayList<IngredientInRecipe> ingredientInRecipe,
                  double calorie, int portion, boolean isDefault){
        this.recipeID = recipeID;
        this.name = name;
        this.description = description;
        //INGREDIENT İÇİN FİLL METHODU OLMASI GEREKMİYO MU
        this.calorie =calorie;
        this.portion = portion;
        this.isDefault = isDefault;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ArrayList<IngredientInRecipe> getIngredientInRecipe() {
        return ingredientInRecipe;
    }

    public void setIngredientInRecipe(ArrayList<IngredientInRecipe> ingredientInRecipe) {
        this.ingredientInRecipe = ingredientInRecipe;
    }

    public double getCalorie() {
        return calorie;
    }

    public void setCalorie(double calorie) {
        this.calorie = calorie;
    }

    public int getPortion() {
        return portion;
    }

    public void setPortion(int portion) {
        this.portion = portion;
    }

    public boolean isDefault() {
        return isDefault;
    }

    public void setDefault(boolean aDefault) {
        isDefault = aDefault;
    }

    public String getRecipeID() {
        return recipeID;
    }

    public void setRecipeID(String recipeID) {
        this.recipeID = recipeID;
    }
}
