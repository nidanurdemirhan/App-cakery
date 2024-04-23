package com.nida.app_cakery.Model;

import java.util.ArrayList;
import java.util.List;

public class Recipe {
    int recipeID;
    String name;
    String description;

    List<IngredientInRecipe> ingredientsInRecipe = new ArrayList<>();
    double calorie;
    int portion;
    boolean shared;
    String owner; //system's recipe-> system, user's recipe -> user's mail

    //constructor
    public Recipe(int recipeID, String name, String description, List<IngredientInRecipe> ingredientsInRecipe, double calorie, int portion, boolean shared, String owner) {
        this.recipeID = recipeID;
        this.name = name;
        this.description = description;
        this.ingredientsInRecipe = ingredientsInRecipe;
        this.calorie = calorie;
        this.portion = portion;
        this.shared = shared;
        this.owner = owner;
    }

    //setters
    public void setPortion(int portion) {
        this.portion = portion;
    }

    public void setShared(boolean shared) {
        this.shared = shared;
    }

    //getters
    public int getRecipeID() {
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

    public boolean isShared() {
        return shared;
    }

    public String getOwner() {
        return owner;
    }
}
