package it.example.speedclick.dto;

import java.math.BigDecimal;
import java.util.List;

public class TimesInputDto {

    private String userId;
    private List<BigDecimal> times;

    public List<BigDecimal> getTimes() {
        return times;
    }

    public void setTimes(List<BigDecimal> times) {
        this.times = times;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
