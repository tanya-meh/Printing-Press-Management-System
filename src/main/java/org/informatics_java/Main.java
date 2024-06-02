package org.informatics_java;

import org.informatics_java.data.enums.PaperType;
import org.informatics_java.data.Paper;
import org.informatics_java.data.publications.Book;
import org.informatics_java.data.publications.Newspaper;
import org.informatics_java.data.publications.Poster;
import org.informatics_java.data.publications.Publication;
import org.informatics_java.data.enums.PageSize;
import org.informatics_java.exceptions.IllegalQuantityException;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        Publication publication1;
        Publication publication2;
        Publication publication3;

        publication1 = new Book("Kniga1", 325, PageSize.A5);
        publication2 = new Newspaper("Spisanie1", 60, PageSize.A4);
        publication3 = new Poster("Plakat1", 1, PageSize.A2);

        System.out.println(publication1 + "\n" + publication2 + "\n" + publication3);
/*
        Printer printer1 = new Printer("p1", 1000, true, 20);

        Paper paperGlossyA2 = new Paper(PaperType.GLOSSY, PageSize.A2);
        Paper paperNewsprintA4 = new Paper(PaperType.NEWSPRINT, PageSize.A4);
        Paper paperStandardA5 = new Paper(PaperType.STANDARD, PageSize.A5);


        try {
            printer1.loadPaper(10, paperGlossyA2);
            printer1.loadPaper(200, paperNewsprintA4);
            printer1.loadPaper(500, paperStandardA5);
        } catch (IllegalQuantityException e) {
            throw new RuntimeException(e);
        }

        System.out.println(printer1);

 */
    }
}