package org.informatics_java.data.publications;

import org.informatics_java.data.publications.enums.PageSize;
import org.informatics_java.data.publications.enums.PaperType;

import java.math.BigDecimal;

public class Publication implements Printable {
    private String title;
    private int numberOfPages;
    private PageSize pageSize;

    public Publication(String title, int numberOfPages, PageSize pageSize) {
        this.title = title;
        this.numberOfPages = numberOfPages;
        this.pageSize = pageSize;
    }

    public String getTitle() {
        return title;
    }

    public int getNumberOfPages() {
        return numberOfPages;
    }

    public PageSize getPageSize() {
        return pageSize;
    }

    @Override
    public String toString() {
        return "Publication{" +
                "title='" + title + '\'' +
                ", numberOfPages=" + numberOfPages +
                ", pageSize=" + pageSize +
                '}';
    }

    private BigDecimal pricePerPage(PaperType paperType) {
        BigDecimal pricePerPage = BigDecimal.ZERO;
        for (int i = 0; i < pageSize.ordinal(); i++){
            pricePerPage = pricePerPage.multiply(BigDecimal.valueOf(PageSize.getPriceIncreasePercent() / 100));
        }

        return pricePerPage;
    }
    @Override
    public BigDecimal print(PaperType paperType) {
        BigDecimal pricePerPage = pricePerPage(paperType);
        return paperType.getBasePrice().multiply(pricePerPage);
    }
}
