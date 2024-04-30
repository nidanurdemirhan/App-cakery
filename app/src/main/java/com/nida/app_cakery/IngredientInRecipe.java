package com.nida.app_cakery;

public class IngredientInRecipe {

    private String recipeID;
    private String ingredientID;
    private double amount;
    private String unit;

    public IngredientInRecipe(String recipeID,
                              String ingredientID, double amount, String unit) {
        this.recipeID = recipeID;
        this.ingredientID = ingredientID;
        this.amount = amount;
        this.unit = unit;
    }

    public String getRecipeID() {
        return recipeID;
    }

    public void setRecipeID(String recipeID) {
        this.recipeID = recipeID;
    }

    public String getIngredientID() {
        return ingredientID;
    }

    public void setIngredientID(String ingredientID) {
        this.ingredientID = ingredientID;
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
