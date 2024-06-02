package org.informatics_java.data.publications;

import org.informatics_java.data.enums.PageSize;

public class Poster extends Publication{
    public Poster(String title, int numberOfPages, PageSize pageSize) {
        super(title, numberOfPages, pageSize);
    }

    @Override
    public String toString() {
        return "Poster{} " + super.toString();
    }
}
