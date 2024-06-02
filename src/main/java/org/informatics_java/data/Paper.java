package org.informatics_java.data;

import org.informatics_java.data.enums.PageSize;
import org.informatics_java.data.enums.PaperType;

import java.util.Objects;

public class Paper implements Comparable<Paper> {
    private PaperType paperType;
    private PageSize pageSize;

    public Paper(PaperType paperType, PageSize pageSize) {
        this.paperType = paperType;
        this.pageSize = pageSize;
    }

    public PaperType getPaperType() {
        return paperType;
    }

    public PageSize getPageSize() {
        return pageSize;
    }

    @Override
    public String toString() {
        return "Paper{" +
                "paperType=" + paperType +
                ", pageSize=" + pageSize +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Paper paper = (Paper) o;
        return paperType == paper.paperType && pageSize == paper.pageSize;
    }

    @Override
    public int hashCode() {
        return Objects.hash(paperType, pageSize);
    }

    @Override
    public int compareTo(Paper o) {
        if(this.pageSize.compareTo(o.getPageSize()) == 0) {
            return this.paperType.compareTo(o.getPaperType());
        }
        return this.pageSize.compareTo(o.getPageSize());
    }

}
