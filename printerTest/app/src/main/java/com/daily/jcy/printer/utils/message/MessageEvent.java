package com.daily.jcy.printer.utils.message;

public class MessageEvent {

    public static final String INIT = "init";
    public static final String UPDATE = "update";
    public static final String PUT_SUCCESS = "Put Success";
    private int message;
    public static final int CREATE_CLIENT = 0;
    public static final int UPDATE_CLIENT = 1;
    public static final int CREATE_FOOD = 0;
    public static final int UPDATE_FOOD = 1;

    public MessageEvent(int message) {
        this.message = message;
    }


    public int getMessage() {
        return message;
    }

    public void setMessage(int message) {
        this.message = message;
    }

}
