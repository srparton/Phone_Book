package com.example.phonebook;

import android.util.Log;

/*
 an utility class to store and retrieve Contact instances.
*/
public class Contacts {
    private int id;
    private String firstName;
    private String lastName;
    private long phoneNum;
    public Contacts(){
    }
    public Contacts(String s,String t, long q){
        Log.d("cons", "Made it into Contacts construtor: ");
       firstName = s;
       lastName = t;
       phoneNum = q;
    }
    public Contacts(int id, String s,String t, long q){
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
    public void setLastName(String t){
       lastName = t;
    }
    public void setPhoneNum(long q){
       phoneNum = q;
    }
    public int getId(){
       return id;
    }
    public String getFirstName(){
       return firstName;
    }
    public String getLastName(){
       return lastName;
    }
    public long getPhoneNum(){
       return phoneNum;
    }
}