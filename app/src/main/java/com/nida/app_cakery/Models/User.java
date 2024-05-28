package com.nida.app_cakery.Models;

import com.nida.app_cakery.Domain.CakeryDomain;

import java.util.ArrayList;

public class User extends Person {
    private ArrayList<String> ingredientsInInventory = new ArrayList<>();

    private ArrayList<Boolean> ingredientStatus = new ArrayList<>();


    private ArrayList<Recipe> myRecipeList = new ArrayList<>();
    private ArrayList<Recipe> favoriteRecipes = new ArrayList<>();
    public User(String personID, String mailAddress, String name, String surname, String password, ArrayList<Recipe> allRecipes, ArrayList<String> myRecipesData, ArrayList<String> favoriteRecipesData, ArrayList<String> ingredientsInInventory) {
        super(personID, mailAddress, name, surname, password);
        this.ingredientsInInventory = ingredientsInInventory;
        fillRecipeLists(allRecipes,myRecipesData,favoriteRecipesData);
        fillIngredientStatusList();
    }

    public User(String personID, String mailAddress, String name, String surname, String password ) { //Registration
        super(personID, mailAddress, name, surname, password);
    }

    public void fillRecipeLists(ArrayList<Recipe> allRecipes, ArrayList<String> favoriteList, ArrayList<String> myRecipesList){ //BURDA DEĞİŞİKLİK VAAR
        for(int i=0;i<allRecipes.size();i++){
            if(myRecipesList.contains(allRecipes.get(i).getRecipeID())){
                this.myRecipeList.add(allRecipes.get(i));
            }
        }
        for(int i=0;i<allRecipes.size();i++){
            if(favoriteList.contains(allRecipes.get(i).getRecipeID())){
                this.favoriteRecipes.add(allRecipes.get(i));
            }
        }
    }

    public void fillIngredientStatusList(){
        ArrayList<Ingredient> ingredients = CakeryDomain.getInstance().getIngredientList();
        for (int i = 0; i < ingredients.size(); i++) {
            if (ingredientsInInventory.contains(ingredients.get(i).getIngredientID())){
                ingredientStatus.add(true);
            } else{
                ingredientStatus.add(false);
            }
        }
    }


    public void addIngredientToInventory(String ingredientID){
        ingredientsInInventory.add(ingredientID);
        CakeryDomain.getInstance().addItemToArrayInFirestoreDb("User", getPersonID(), "ingredientsInInventory",  ingredientID );
    }

    public void removeIngredientFromInventory(String ingredientID){
        ingredientsInInventory.remove(ingredientID);
        CakeryDomain.getInstance().removeItemFromArrayInFirestoreDb("User", getPersonID(), "ingredientsInInventory",  ingredientID );
    }

    public void addToFavoriteRecipes(Recipe recipe){
        favoriteRecipes.add(recipe);
        CakeryDomain.getInstance().addItemToArrayInFirestoreDb("User", getPersonID(), "favoriteRecipes", recipe.getRecipeID());
    }

    public void removeFromFavoriteRecipes(String recipeID){
        favoriteRecipes.remove(findRecipeInTheFavRecipeList(recipeID));
        CakeryDomain.getInstance().removeItemFromArrayInFirestoreDb("User", getPersonID(), "favoriteRecipes", recipeID);
    }

    private Recipe findRecipeInTheFavRecipeList(String recipeID){
        for(Recipe favRecipe: favoriteRecipes){
            if(favRecipe.getRecipeID().equals(recipeID)){
                return favRecipe;
            }
        }
        return null;
    }

    public void addRecipeToMyRecipeList(Recipe recipe){
        CakeryDomain.getInstance().addRecipe(recipe);
        myRecipeList.add(recipe);
        CakeryDomain.getInstance().addItemToArrayInFirestoreDb("User", getPersonID(), "myRecipeList", recipe.getRecipeID());
    }

    public void addRecipeToAdminRequestList(Recipe recipe){
        CakeryDomain.getInstance().addItemToArrayInFirestoreDb("Admin", "2gEUqE9NScahNMw12814", "requestList", recipe.getRecipeID());
    }

    public ArrayList<Recipe> getMyRecipeList() {
        return myRecipeList;
    }

    public void setMyRecipeList(ArrayList<Recipe> myRecipeList) {
        this.myRecipeList = myRecipeList;
    }

    public ArrayList<Recipe> getFavoriteRecipes() {
        return favoriteRecipes;
    }

    public void setFavoriteRecipes(ArrayList<Recipe> favoriteRecipes) {
        this.favoriteRecipes = favoriteRecipes;
    }

    public ArrayList<String> getIngredientsInInventory() {
        return ingredientsInInventory;
    }

    public void setIngredientsInInventory(ArrayList<String> ingredientsInInventory) {
        this.ingredientsInInventory = ingredientsInInventory;
    }

    public ArrayList<Boolean> getIngredientStatus() {
        return ingredientStatus;
    }

    public void setIngredientStatus(ArrayList<Boolean> ingredientStatus) {
        this.ingredientStatus = ingredientStatus;
    }

    /*
    private ArrayList<String> convertRecipeToStringArr(ArrayList<Recipe> recipeList){
        ArrayList<String> recipeIDList = new ArrayList<>();
        for(Recipe recipe: recipeList){
            recipeIDList.add(recipe.getRecipeID());
        }
        return recipeIDList;
    }
     */
}
