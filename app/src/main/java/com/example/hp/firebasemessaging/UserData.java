package com.example.hp.firebasemessaging;

public class UserData {

    String id, userName, userNumber;

    public UserData(){}

    public UserData(String id, String userName, String userNumber){

        setId(id);
        setUserName(userName);
        setUserNumber(userNumber);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserNumber() {
        return userNumber;
    }

    public void setUserNumber(String userNumber) {
        this.userNumber = userNumber;
    }
}
