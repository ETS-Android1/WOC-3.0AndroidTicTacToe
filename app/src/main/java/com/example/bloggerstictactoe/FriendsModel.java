package com.example.bloggerstictactoe;

public class FriendsModel {
   private String userIDoffriend;

   private FriendsModel(){}
   private FriendsModel(String userIDoffriend){
       this.userIDoffriend = userIDoffriend;
   }

    public String getUserIDoffriend() {
        return userIDoffriend;
    }

    public void setUserIDoffriend(String userIDoffriend) {
        this.userIDoffriend = userIDoffriend;
    }
}
