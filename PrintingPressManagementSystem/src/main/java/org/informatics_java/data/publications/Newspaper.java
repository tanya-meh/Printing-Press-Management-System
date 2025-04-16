package org.informatics_java.data.publications;

import org.informatics_java.data.enums.PageSize;

public class Newspaper extends Publication{
    public Newspaper(String title, int numberOfPages, PageSize pageSize) {
        super(title, numberOfPages, pageSize);
    }

    @Override
    public String toString() {
        return "Newspaper{} " + super.toString();
    }
}
