package com.nida.app_cakery.Model;

import java.util.ArrayList;
import java.util.List;

public class Admin extends Person{
    List<Recipe> requestList = new ArrayList<>();
    Admin(String mailAddress, String name, String surname, String password) {
        super(mailAddress, name, surname, password);
    }

    public List<Recipe> getRequestList() {
        return requestList;
    }

    public void setRequestList(List<Recipe> requestList) {
        this.requestList = requestList;
    }
}
