package com.daily.jcy.printer.utils.message;

public class MessageEvent {

    public static final String INIT = "init";
    public static final String UPDATE = "update";
    public static final String PUT_SUCCESS = "Put Success";
    private String message;

    public MessageEvent(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
