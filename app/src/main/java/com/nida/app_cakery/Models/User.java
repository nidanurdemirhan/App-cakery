package com.nida.app_cakery.Models;

import com.nida.app_cakery.Domain.CakeryDomain;
import com.nida.app_cakery.Listeners.FirebaseListener;

import java.util.ArrayList;

public class User extends Person {
    private ArrayList<String> ingredientsInInventory = new ArrayList<>();


    private ArrayList<Recipe> myRecipeList = new ArrayList<>();
    private ArrayList<Recipe> favoriteRecipes = new ArrayList<>();
    public User(String personID, String mailAddress, String name, String surname, String password, ArrayList<Recipe> allRecipes, ArrayList<String> myRecipesData, ArrayList<String> favoriteRecipesData, ArrayList<String> ingredientsInInventory) {
        super(personID, mailAddress, name, surname, password);
        this.ingredientsInInventory = ingredientsInInventory;
        fillRecipeLists(allRecipes,myRecipesData,favoriteRecipesData);
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


    public void addIngredientToInventory(String ingredientID){
        ingredientsInInventory.add(ingredientID);
        CakeryDomain.getInstance().updateStringArrayInTheFirestore("User", "ingredientsInInventory",  ingredientsInInventory );
    }

    public void removeIngredientFromInventory(String ingredientID){
        ingredientsInInventory.remove(ingredientID);
        CakeryDomain.getInstance().updateStringArrayInTheFirestore("User", "ingredientsInInventory",  ingredientsInInventory );
    }

    public void addToFavoriteRecipes(Recipe recipe){
        favoriteRecipes.add(recipe);
        ArrayList<String> favRecipeIDList = convertRecipeToStringArr(favoriteRecipes);
        CakeryDomain.getInstance().updateStringArrayInTheFirestore("User", "favoriteRecipes", favRecipeIDList);
    }

    public void removeFromFavoriteRecipes(String recipeID){
        favoriteRecipes.remove(findRecipeInTheFavRecipeList(recipeID));
        ArrayList<String> favRecipeIDList = convertRecipeToStringArr(favoriteRecipes);
        CakeryDomain.getInstance().updateStringArrayInTheFirestore("User", "favoriteRecipes", favRecipeIDList);
    }

    private ArrayList<String> convertRecipeToStringArr(ArrayList<Recipe> recipeList){
        ArrayList<String> recipeIDList = new ArrayList<>();
        for(Recipe recipe: recipeList){
            recipeIDList.add(recipe.getRecipeID());
        }
        return recipeIDList;
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
        ArrayList<String> myRecipeIdList = convertRecipeToStringArr(myRecipeList);
        CakeryDomain.getInstance().updateStringArrayInTheFirestore("User", "myRecipes", myRecipeIdList);
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
}
