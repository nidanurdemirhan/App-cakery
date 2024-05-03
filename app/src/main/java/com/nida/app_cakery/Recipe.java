package com.nida.app_cakery;

import java.util.ArrayList;

public class Recipe {
    private String recipeID, name, description;
    private ArrayList<IngredientInRecipe> ingredientInRecipe;
    private double calorie;
    private long portion;      //Long yaptım çünkü firebase long kullanıyormuş sadece
    private String status;    //DEĞİŞTİRDİM ÇÜNKÜ: default, shared, unshared şeklinde olmayacak mı?? sor kızlara değilse değiştir

    public Recipe(String recipeID,String name, String description,
                  ArrayList<IngredientInRecipe> ingredientInRecipe,
                  double calorie, Long portion, String status){
        this.recipeID = recipeID;
        this.name = name;
        this.description = description;
        this.calorie =calorie;
        this.portion = portion;
        this.status = status;
        this.ingredientInRecipe = ingredientInRecipe;  //ADDED
        //ArrayList<IngredientInRecipe> allIngredientInRecipe = new ArrayList<>();//BUNU PARAMETRE OLARAK VERİCEZ GLB HATA VERMESİN DİYE YAZDIM EMİN DEĞİLİM
        //fillIngredientList(allIngredientInRecipe);
    }
    public void fillIngredientList(ArrayList<IngredientInRecipe> allIngredientInRecipe){
       for(int i=0;i<allIngredientInRecipe.size();i++){
           if(allIngredientInRecipe.get(i).getRecipeID().equals(this.recipeID)){
               ingredientInRecipe.add(allIngredientInRecipe.get(i));
           }
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
}
