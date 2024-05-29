package org.informatics_java.data.publications;

import org.informatics_java.data.enums.PageSize;

import java.util.Objects;

public class Publication implements Comparable<Publication>{
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Publication that = (Publication) o;
        return numberOfPages == that.numberOfPages && Objects.equals(title, that.title) && pageSize == that.pageSize;
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, numberOfPages, pageSize);
    }

    @Override
    public int compareTo(Publication o) {
        return this.getTitle().compareTo(o.getTitle());
    }
}
