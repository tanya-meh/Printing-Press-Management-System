package org.informatics_java.service.printers;

import org.informatics_java.data.enums.PageSize;
import org.informatics_java.data.enums.PaperType;
import org.informatics_java.data.paper.Paper;
import org.informatics_java.data.publications.Publication;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Printer {
    private String id;
    private int maxNumberOfPapers;
    private boolean isColored;
    private int printedPagesPerMinute;
    private Map<Paper, Integer> numberOfPapersLoadedMap;

    //map izdanie : broi printiraniq
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

    private int numberOfPapersTotal() {
        int numberOfPagesTotal = this.numberOfPapersLoadedMap.values().stream().reduce(0, (num1, num2) -> num1 + num2);
        return numberOfPagesTotal;
    }

    private int freePapersSpace() {
        return this.getMaxNumberOfPapers() - this.numberOfPapersTotal();
    }

    //returns number of papers there is no space for
    public int loadPaper(int numberOfPapers, Paper paper) { //ne mozhe da e otricatelno //size paper & type paper
        int currNumOfPapers = this.numberOfPapersLoadedMap.get(paper);

        if (this.numberOfPapersTotal() + numberOfPapers >= this.getMaxNumberOfPapers()){
            this.numberOfPapersLoadedMap.put(paper, currNumOfPapers + freePapersSpace());
            return numberOfPapers - freePapersSpace();
        } else {
            this.numberOfPapersLoadedMap.put(paper, currNumOfPapers + numberOfPapers);
            return 0;
        }
    }

    public boolean print(Publication publication, int numberOfCopies, PaperType paperType, boolean coloredPrint) {
        //exception za paper
        //exception za colored
        //razdelqne na proverkite v otdelni metodi

        if (coloredPrint && !this.isColored()){
            //exception
            return false;
        }

        if (publication.getNumberOfPages() * numberOfCopies > this.numberOfPapersLoadedMap.get(new Paper(paperType, publication.getPageSize()))) {
            //exception?
            return false;
        }

        if (publicationsNumberOfPrintsMap.containsKey(publication)){
            publicationsNumberOfPrintsMap.put(publication, publicationsNumberOfPrintsMap.get(publication) + numberOfCopies);
        } else {
            publicationsNumberOfPrintsMap.put(publication, numberOfCopies);
        }

        int currNumOfPapers = this.numberOfPapersLoadedMap.get(new Paper(paperType, publication.getPageSize()));
        this.numberOfPapersLoadedMap.put(new Paper(paperType, publication.getPageSize()), currNumOfPapers - publication.getNumberOfPages() * numberOfCopies);

        return true;
    }

    public int numberOfPagesPrinted() {
        return publicationsNumberOfPrintsMap.entrySet()
                .stream()
                .mapToInt(entry -> entry.getKey().getNumberOfPages() * entry.getValue())
                .sum();
    }

}
