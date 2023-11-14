package com.mssecurity.mssecurity.Models;

public class Message {
    private String data;
    private boolean flag;

    public Message(String data, boolean flag) {
        this.data = data;
        this.flag = flag;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }


}
