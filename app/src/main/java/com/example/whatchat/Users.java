package com.example.whatchat;

public class Users {
    String mail,userName,password,userId,lastMessage,status;

    public Users(){

    }

    public Users(String id, String namee, String emaill, String password,String cpassword, String status) {

        this.userId = id;
        this.mail = emaill;
        this.userName = namee;
        this.password = password;
        this.status = status;
        this.password =cpassword;

    }

    public Users(String uid, String name, String email, String password, String status) {
        this.userId = uid;
        this.mail = email;
        this.userName =name ;
        this.password = password;
        this.status = status;

    }


    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getLastMessage() {
        return lastMessage;
    }

    public void setLastMessage(String lastMessage) {
        this.lastMessage = lastMessage;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
