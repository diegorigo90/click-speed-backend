package it.example.speedclick.dto;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;

public class UserBestTime {

    private int time;

    private String user;

    private String userId;

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
