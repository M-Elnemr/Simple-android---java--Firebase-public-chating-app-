package com.example.hp.firebasemessaging;

public class Item {
    private String message, date, userName;

    public Item(){}
    public Item(String message, String date, String userName){
        setMessage(message);
        setDate(date);
        setUserName(userName);
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
