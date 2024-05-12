package org.informatics_java.data.publications.enums;

public enum PageSize {
    A5, A4, A3, A2, A1;
    private static int priceIncreasePercent;//test 0-100

    public static int getPriceIncreasePercent() {
        return priceIncreasePercent;
    }

    public static void setPriceIncreasePercent(int priceIncreasePercent) {
        PageSize.priceIncreasePercent = priceIncreasePercent;
    }
}
