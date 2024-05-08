package com.nida.app_cakery.Model;

import com.nida.app_cakery.Model.Ingredient;

public class IngredientInRecipe {
    private String recipeID;
    private Ingredient ingredient;
    private double amount;
    private String unit;

    public IngredientInRecipe(String recipeID, Ingredient ingredient, double amount, String unit) {
        this.recipeID = recipeID;
        this.ingredient = ingredient;
        this.amount = amount;
        this.unit = unit;
    }

    public String getRecipeID() {
        return recipeID;
    }

    public void setRecipeID(String recipeID) {
        this.recipeID = recipeID;
    }

    public Ingredient getIngredient() {
        return ingredient;
    }

    public String getIngredientID(){ return ingredient.getIngredientID(); }

    public void setIngredient(Ingredient ingredient) {
        this.ingredient = ingredient;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }
}
