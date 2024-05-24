package com.nida.app_cakery.Models;

import com.nida.app_cakery.Domain.CakeryDomain;

import java.util.ArrayList;

public class User extends Person {
    private ArrayList<String> ingredientsInInventory = new ArrayList<>();


    private ArrayList<Recipe> myRecipes = new ArrayList<>();
    private ArrayList<Recipe> favoriteRecipes = new ArrayList<>();
    public User(String personID, String mailAddress, String name, String surname, String password, ArrayList<Recipe> allRecipes, ArrayList<String> myRecipesData, ArrayList<String> favoriteRecipesData, ArrayList<String> ingredientsInInventory) {
        super(personID, mailAddress, name, surname, password);
        //databaseden geliceği iççin silinecek
        //ArrayList<String> myRecipesData = new ArrayList<>(); //BURDAKİ LİSTE DATABASEDEN ALINACAK
        //ArrayList<String> favoriteRecipesData = new ArrayList<>();  //BURDAKİ LİSTE DE DATABASEDEN ALINACAK
        this.ingredientsInInventory = ingredientsInInventory;
        fillRecipeLists(allRecipes,myRecipesData,favoriteRecipesData);
    }

    public User(String personID, String mailAddress, String name, String surname, String password ) { //Registration
        super(personID, mailAddress, name, surname, password);
    }

    public void fillRecipeLists(ArrayList<Recipe> allRecipes, ArrayList<String> favoriteList, ArrayList<String> myRecipesList){ //BURDA DEĞİŞİKLİK VAAR
        for(int i=0;i<allRecipes.size();i++){
            if(myRecipesList.contains(allRecipes.get(i).getRecipeID())){
                this.myRecipes.add(allRecipes.get(i));
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
        CakeryDomain.getInstance().updateIngredientsInInventory(ingredientsInInventory);
    }

    public void removeIngredientFromInventory(String ingredientID){
        ingredientsInInventory.remove(ingredientID);
        CakeryDomain.getInstance().updateIngredientsInInventory(ingredientsInInventory);
    }

    public ArrayList<Recipe> getMyRecipes() {
        return myRecipes;
    }

    public void setMyRecipes(ArrayList<Recipe> myRecipes) {
        this.myRecipes = myRecipes;
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
