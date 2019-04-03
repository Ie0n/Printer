package com.daily.jcy.printer.model.data.bean;

import io.objectbox.annotation.Entity;
import io.objectbox.annotation.Id;

@Entity
public class Login {
    @Id(assignable = true)
    public long logId;
    private boolean isOpen;
    private String password;
    public static final int LOGIN_ID = 1001;

    public Login(long logId, boolean isOpen,String password) {
        this.logId = logId;
        this.isOpen = isOpen;
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public long getLogId() {
        return logId;
    }

    public void setLogId(long logId) {
        this.logId = logId;
    }

    public boolean isOpen() {
        return isOpen;
    }

    public void setOpen(boolean open) {
        isOpen = open;
    }

    @Override
    public String toString() {
        return "[ logId: " + getLogId()
                + " isOpen: " + isOpen()
                + " password: " + getPassword();
    }
}
