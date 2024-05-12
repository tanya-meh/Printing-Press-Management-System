package org.informatics_java.data.publications.enums;

import java.math.BigDecimal;

public enum PaperType {
    STANDARD(), GLOSSY(), NEWSPRINT();

    private BigDecimal basePrice;

    PaperType() {
        this.basePrice = BigDecimal.ZERO;
    }
    PaperType(BigDecimal basePrice) {
        this.basePrice = basePrice;
    }

    public BigDecimal getBasePrice() {
        return basePrice;
    }

    public void setBasePrice(BigDecimal basePrice) {
        this.basePrice = basePrice;
    }
}
