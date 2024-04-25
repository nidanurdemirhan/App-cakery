package com.nida.app_cakery.Model;

import java.util.ArrayList;
import java.util.List;

public class User extends Person{
    List<Recipe> myRecipes = new ArrayList<>();
    List<Recipe> favorites = new ArrayList<>();
    User(String mailAddress, String name, String surname, String password) {
        super(mailAddress, name, surname, password);
    }

    public List<Recipe> getMyRecipes() {
        return myRecipes;
    }

    public void setMyRecipes(List<Recipe> myRecipes) {
        this.myRecipes = myRecipes;
    }

    public List<Recipe> getFavorites() {
        return favorites;
    }

    public void setFavorites(List<Recipe> favorites) {
        this.favorites = favorites;
    }
}
