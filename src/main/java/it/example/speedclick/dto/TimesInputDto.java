package it.example.speedclick.dto;

import java.math.BigDecimal;
import java.util.List;

public class TimesInputDto {
    private List<BigDecimal> times;

    public List<BigDecimal> getTimes() {
        return times;
    }

    public void setTimes(List<BigDecimal> times) {
        this.times = times;
    }
}
