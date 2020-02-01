package com.example.androidlabs;

public class Message {

    private long id;
    private String message;
    private boolean isSend;


    public Message(long id, String message, boolean isSend){
        this.id = id;
        this.message = message;
        this.isSend = isSend;
    }

    public long getId() {
        return id;
    }

    public void setId(long id){

    }

    public String getMessage(){
        return message;
    }

    public void setMessage(String message){

    }

    public boolean isSend() {
        return isSend;
    }

    public void setSend(boolean send) {
        isSend = send;
    }
}


