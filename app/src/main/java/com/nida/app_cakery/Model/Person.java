package com.nida.app_cakery.Model;

public class Person {
    String mailAddress;
    String name;
    String surname;
    String password;

    Person(String mailAddress, String name, String surname, String password){
        this.mailAddress = mailAddress;
        this.name = name;
        this.surname = surname;
        this.password =password;
    }

    public String getMailAddress() {
        return mailAddress;
    }

    public void setMailAddress(String mailAddress) {
        this.mailAddress = mailAddress;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
