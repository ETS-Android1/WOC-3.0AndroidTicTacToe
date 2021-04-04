package com.example.bloggerstictactoe;

public class PlayModel {
    private String Receiverid,Senderid,approve;

    private PlayModel(){}

    private PlayModel(String Receiverid,String Senderid,String approve){
        this.Receiverid = Receiverid;
        this.Senderid = Senderid;
        this.approve = approve;
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

    public String getApprove() { return approve; }

    public void setApprove(String approve) { this.approve = approve; }
}
