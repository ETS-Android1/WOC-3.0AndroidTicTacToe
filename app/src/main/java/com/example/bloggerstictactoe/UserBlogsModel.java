package com.example.bloggerstictactoe;

public class UserBlogsModel {

    private String content,title;

    private UserBlogsModel(){}

    private UserBlogsModel(String content,String title){
        this.content = content;
        this.title= title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
