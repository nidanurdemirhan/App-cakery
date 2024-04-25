package com.nida.app_cakery.Model;

import java.util.ArrayList;
import java.util.List;

public class Recipe {
    String recipeID;
    String name;
    String description;

    List<IngredientInRecipe> ingredientsInRecipe = new ArrayList<>();
    double calorie;
    int portion;
    String status;

    //constructor
    public Recipe(String recipeID, String name, String description, List<IngredientInRecipe> ingredientsInRecipe, double calorie, int portion, String status) {
        this.recipeID = recipeID;
        this.name = name;
        this.description = description;
        this.ingredientsInRecipe = ingredientsInRecipe;
        this.calorie = calorie;
        this.portion = portion;
        this.status = status;
    }

    //setters
    public void setPortion(int portion) {
        this.portion = portion;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    //getters
    public String getRecipeID() {
        return recipeID;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public List<IngredientInRecipe> getIngredientsInRecipe() {
        return ingredientsInRecipe;
    }

    public double getCalorie() {
        return calorie;
    }

    public int getPortion() {
        return portion;
    }

    public String getStatus() {
        return status;
    }

}
