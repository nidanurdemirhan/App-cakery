package com.nida.app_cakery;

import java.util.ArrayList;

public class Admin extends Person{
    private ArrayList<Recipe> requestList;
    public Admin(String mailAddress, String name, String surname, String password) {
        super(mailAddress, name, surname, password);
    }

    public void fillRequestList(ArrayList<Recipe> allRecipes){
        for(int i=0;i<allRecipes.size();i++){
            if(requestList.contains(allRecipes.get(i).getRecipeID())){
                this.requestList.add(allRecipes.get(i));
            }
        }
    }
}
