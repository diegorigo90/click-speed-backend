package it.example.speedclick.dto;

import java.math.BigDecimal;

public class Point {
    private final BigDecimal x;
    private final BigDecimal y;

    public Point(BigDecimal x,
                 BigDecimal y) {
        this.x = x;
        this.y = y;
    }

    public BigDecimal getX() {
        return x;
    }

    public BigDecimal getY() {
        return y;
    }
}
