package it.example.speedclick.dto;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public class Data {
    List<BigDecimal> x ;
    List<BigDecimal> y ;

    Map<String, String> info;

    public List<BigDecimal> getX() {
        return x;
    }

    public void setX(List<BigDecimal> x) {
        this.x = x;
    }

    public List<BigDecimal> getY() {
        return y;
    }

    public void setY(List<BigDecimal> y) {
        this.y = y;
    }

    public Map<String, String> getInfo() {
        return info;
    }

    public void setInfo(Map<String, String> info) {
        this.info = info;
    }
}
