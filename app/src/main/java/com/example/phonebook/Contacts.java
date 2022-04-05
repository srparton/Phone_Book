package com.example.phonebook;
/*
 an utility class to store and retrieve Contact instances.
*/
public class Contacts {
    private int id;
    private String firstName;
    private String lastName;
    private int phoneNum;
    public Contacts(){
    }
    public Contacts(String s,String t, int q){
       firstName = s;
       lastName = t;
       phoneNum = q;
    }
    public Contacts(int id, String s,String t, int q){
       firstName = s;
       lastName = t;
       phoneNum = q;
       this.id = id;
    }
    public void setId(int i){
       id = i;
    }
    public void setFirstName(String s){
       firstName = s;
    }
    public void setLirstName(String t){
       lastName = t;
    }
    public void setPhoneNum(int q){
       phoneNum = q;
    }
    public int getId(){
       return id;
    }
    public String getFirstName(){
       return firstName;
    }
    public String getLastName(){
       return firstName;
    }
    public  int getPhoneNum(){
       return phoneNum;
    }
}