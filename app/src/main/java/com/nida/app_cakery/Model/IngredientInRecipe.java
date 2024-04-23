package com.nida.app_cakery.Model;

public class IngredientInRecipe {
    int recipeId;
    int IngredientId;
    int amount;
    String unit;

    public IngredientInRecipe(int recipeId, int ingredientId, int amount, String unit) {
        this.recipeId = recipeId;
        IngredientId = ingredientId;
        this.amount = amount;
        this.unit = unit;
    }

    //setter
    public void setAmount(int amount) {
        this.amount = amount;
    }

    //getters
    public int getRecipeId() {
        return recipeId;
    }

    public int getIngredientId() {
        return IngredientId;
    }

    public int getAmount() {
        return amount;
    }

    public String getUnit() {
        return unit;
    }
}
