package com.nida.app_cakery.Model;

import java.util.ArrayList;
import java.util.List;

public class Recipe_Request {
    String recipeRequestID;
    String name;
    String description;

    List<String> ingredientsInRecipe = new ArrayList<>();
    int portion;

    //constructor
    public Recipe_Request(String recipeRequestID, String name, String description, List<String> ingredientsInRecipe, int portion) {
        this.recipeRequestID = recipeRequestID;
        this.name = name;
        this.description = description;
        this.ingredientsInRecipe = ingredientsInRecipe;
        this.portion = portion;
    }

    //setters
    public void setPortion(int portion) {
        this.portion = portion;
    }

    //getters
    public String getRecipeRequestID() {
        return recipeRequestID;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public List<String> getIngredientsInRecipe() {
        return ingredientsInRecipe;
    }

    public int getPortion() {
        return portion;
    }

}

