package com.daily.jcy.printer.utils.message;

public class BusEvent {
    public static final int CREATE_ORDER = 1;

    private int message;

    public BusEvent(int message) {
        this.message = message;
    }

    public static int getCreateOrder() {
        return CREATE_ORDER;
    }

    public int getMessage() {
        return message;
    }

    public void setMessage(int message) {
        this.message = message;
    }
}
