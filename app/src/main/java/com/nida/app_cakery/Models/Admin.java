package com.nida.app_cakery.Models;

import com.nida.app_cakery.Domain.CakeryDomain;
import com.nida.app_cakery.Listeners.FirebaseListener;

import java.util.ArrayList;

public class Admin extends Person {

    private ArrayList<Recipe> requestList;
    public Admin(String personID, String mailAddress, String name, String surname, String password, ArrayList<String> requestListData) {
        super(personID, mailAddress, name, surname, password);
        this.requestList = new ArrayList<>();
        fillRequestList(requestListData);
    }

    public void fillRequestList(ArrayList<String> requestListData){
        ArrayList<Recipe> allRecipes = CakeryDomain.getInstance().getAllRecipeList();
        requestList.clear();

        for(int i = 0; i< requestListData.size(); i++){
            for(int j= 0; j < allRecipes.size(); j++){
                if(requestListData.get(i).equals(allRecipes.get(j).getRecipeID())){
                    requestList.add(allRecipes.get(j));
                    break;
                }
            }
        }

    }
    public void confirmRequest(Recipe request){
        request.setStatus("shared");
        CakeryDomain.getInstance().removeItemFromArrayInFirestoreDb("Admin", "2gEUqE9NScahNMw12814", "requestList", request.getRecipeID());
        CakeryDomain.getInstance().addRecipe(request);
    }

    public void rejectRequest(Recipe request){ // red yerse request listten çıkar ve rejected olarak değiştir
        request.setStatus("rejected");
        CakeryDomain cd = CakeryDomain.getInstance();
        cd.removeItemFromArrayInFirestoreDb("Admin", "2gEUqE9NScahNMw12814", "requestList", request.getRecipeID());
        CakeryDomain.getInstance().addRecipe(request);
    }
    public ArrayList<Recipe> getRequestList() {
        return requestList;
    }

    public void setRequestList(ArrayList<Recipe> requestList) {
        this.requestList = requestList;
    }
}
