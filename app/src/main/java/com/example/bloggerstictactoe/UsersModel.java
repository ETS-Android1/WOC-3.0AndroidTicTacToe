package com.example.bloggerstictactoe;

public class UsersModel {
    private String Name;
    private String email;
    private String mobile;
    private String userUid;
    private String status;

    private  UsersModel(){}

    private  UsersModel(String Name,String email,String mobile,String userUid,String status){
        this.Name = Name;
        this.email = email;
        this.mobile = mobile;
        this.userUid = userUid;
        this.status = status;
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

    public String getStatus() { return status; }

    public void setStatus(String status) { this.status = status; }
}
