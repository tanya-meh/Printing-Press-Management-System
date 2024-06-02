package org.informatics_java.data.publications;

import org.informatics_java.data.enums.PageSize;

public class Book extends Publication{
    public Book(String title, int numberOfPages, PageSize pageSize) {
        super(title, numberOfPages, pageSize);
    }

    @Override
    public String toString() {
        return "Book{} " + super.toString();
    }
}
