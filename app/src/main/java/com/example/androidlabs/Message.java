package com.example.androidlabs;

public class Message {
    private String message;
    private boolean isSend;
    private long id;

    public Message(long id, String message, boolean isSend) {
        this.message = message;
        this.isSend = isSend;
        this.id = id;
    }

    public Message() {
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isSend() {
        return isSend;
    }

    public void setSend(boolean send) {
        isSend = send;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

}
