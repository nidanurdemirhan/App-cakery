package com.nida.app_cakery.Models;

import com.nida.app_cakery.Domain.CakeryDomain;

import java.util.ArrayList;

public class Admin extends Person {
    private ArrayList<Recipe> requestList;
    public Admin(String personID, String mailAddress, String name, String surname, String password, ArrayList<String> requestListData) {
        super(personID, mailAddress, name, surname, password);
        fillRequestList(requestListData);
    }

    public void fillRequestList(ArrayList<String> requestListData){
        ArrayList<Recipe> allRecipes = CakeryDomain.getInstance().getRecipeList();

        for(int i = 0; i< requestListData.size(); i++){
            for(int j= 0; j < allRecipes.size(); j++){
                if(requestListData.get(i).equals(allRecipes.get(j).getRecipeID())){
                    this.requestList.add(allRecipes.get(i));
                    break;
                }
            }
        }
    }
}
