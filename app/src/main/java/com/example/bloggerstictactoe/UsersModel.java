package com.example.bloggerstictactoe;

public class UsersModel {
    private String Name;
    private String email;
    private String mobile;
    private String userUid;

    private  UsersModel(){}

    private  UsersModel(String Name,String email,String mobile,String userUid){
        this.Name = Name;
        this.email = email;
        this.mobile = mobile;
        this.userUid = userUid;
    }


    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getUserUid() {
        return userUid;
    }

    public void setUserUid(String userUid) {
        this.userUid = userUid;
    }
}
