package org.informatics_java.data;

import org.informatics_java.data.publications.Publication;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Printer implements Comparable<Printer> {
    private String id;
    private int maxNumberOfPapers;
    private boolean isColored;
    private int printedPagesPerMinute;
    private Map<Paper, Integer> numberOfPapersLoadedMap;

    //map publication : number of prints
    private Map<Publication, Integer> publicationsNumberOfPrintsMap;

    public Printer(String id, int maxNumberOfPapers, boolean isColored, int printedPagesPerMinute) {
        this.id = id;
        this.maxNumberOfPapers = maxNumberOfPapers;
        this.isColored = isColored;
        this.printedPagesPerMinute = printedPagesPerMinute;
        this.numberOfPapersLoadedMap = new HashMap<>();
        this.publicationsNumberOfPrintsMap = new HashMap<>();
    }

    public String getId() {
        return id;
    }

    public int getMaxNumberOfPapers() {
        return maxNumberOfPapers;
    }

    public boolean isColored() {
        return isColored;
    }

    public int getPrintedPagesPerMinute() {
        return printedPagesPerMinute;
    }

    public Map<Paper, Integer> getNumberOfPapersLoadedMap() {
        return numberOfPapersLoadedMap;
    }

    public Map<Publication, Integer> getPublicationsNumberOfPrintsMap() {
        return publicationsNumberOfPrintsMap;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Printer printer = (Printer) o;
        return Objects.equals(id, printer.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public int compareTo(Printer o) {
        return this.id.compareTo(o.id);
    }

    @Override
    public String toString() {
        return "Printer{" +
                "id='" + id + '\'' +
                ", maxNumberOfPapers=" + maxNumberOfPapers +
                ", isColored=" + isColored +
                ", printedPagesPerMinute=" + printedPagesPerMinute +
                ", numberOfPapersLoadedMap=" + numberOfPapersLoadedMap +
                ", publicationsNumberOfPrintsMap=" + publicationsNumberOfPrintsMap +
                '}';
    }

    public void putNumberOfPapers(Paper paper, int numOfPapers) {
        this.numberOfPapersLoadedMap.put(paper, numOfPapers);
    }

    public void putNumberOfPublications(Publication publication, int numOfPrints) {
        this.publicationsNumberOfPrintsMap.put(publication, numOfPrints);
    }
}

