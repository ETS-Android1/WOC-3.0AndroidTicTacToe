package com.example.bloggerstictactoe;

public class PlayModel {
    private String Receiverid,Senderid;

    private PlayModel(){}

    private PlayModel(String Receiverid,String Senderid){
        this.Receiverid = Receiverid;
        this.Senderid = Senderid;
    }

    public String getReceiverid() {
        return Receiverid;
    }

    public void setReceiverid(String receiverid) {
        Receiverid = receiverid;
    }

    public String getSenderid() {
        return Senderid;
    }

    public void setSenderid(String senderid) {
        Senderid = senderid;
    }
}
