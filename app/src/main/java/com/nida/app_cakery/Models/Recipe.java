package com.nida.app_cakery.Models;

import com.nida.app_cakery.Domain.CakeryDomain;

import java.util.ArrayList;

public class Recipe {
    private String recipeID, name, description;
    private ArrayList<IngredientInRecipe> ingredientInRecipe;
    private double calorie;
    private long portion;
    private String status;

    private String imageUrl;
    public Recipe(String recipeID,String name, String description,
                  ArrayList<IngredientInRecipe> ingredientInRecipe,
                  double calorie, Long portion, String status, String imageUrl){
        this.recipeID = recipeID;
        this.name = name;
        this.description = description;
        this.calorie =calorie;
        this.portion = portion;
        this.status = status;
        this.ingredientInRecipe = ingredientInRecipe;
        this.imageUrl = imageUrl;
    }
    public void fillIngredientList(ArrayList<IngredientInRecipe> allIngredientInRecipe){
       for(int i=0;i<allIngredientInRecipe.size();i++){
           if(allIngredientInRecipe.get(i).getRecipeID().equals(this.recipeID)){
               ingredientInRecipe.add(allIngredientInRecipe.get(i));
           }
       }

    }
    public Boolean isIngredientsAvailable(){
       /*ArrayList<String> ingredientsInInventory = ((User)(CakeryDomain.getInstance().getPerson())).getIngredientsInInventory();
        for(int i = 0; i < ingredientInRecipe.size(); i++ ){
            if(!ingredientsInInventory.contains(ingredientInRecipe.get(i).getIngredient().getIngredientID())){
                return false;
            }
        }
        return true;*/

        // %80 matching
        int availableIngredientCount = 0;
        ArrayList<String> ingredientsInInventory = ((User)(CakeryDomain.getInstance().getPerson())).getIngredientsInInventory();
        for(int i = 0; i < ingredientInRecipe.size(); i++ ){
            if(ingredientsInInventory.contains(ingredientInRecipe.get(i).getIngredient().getIngredientID())){
                availableIngredientCount++;
            }
        }

        if((ingredientInRecipe.size() != 0) && Math.floor((availableIngredientCount*100)/ingredientInRecipe.size())>79){
            return true;
        }
        else{
            return false;
        }
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

    public Long getPortion() {
        return portion;
    }

    public void setPortion(Long portion) {
        this.portion = portion;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getRecipeID() {
        return recipeID;
    }

    public void setRecipeID(String recipeID) {
        this.recipeID = recipeID;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
