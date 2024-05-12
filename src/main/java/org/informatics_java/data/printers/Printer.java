package org.informatics_java.data.printers;

import org.informatics_java.data.publications.Publication;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

public class Printer {
    private int maxNumberOfPages;
    private boolean isColored;
    private int printedPagesPerMinute;
    private int numberOfPages;

    //map izdanie : broi printiraniq
    private Map<Publication, Integer> publications;

    public Printer(int maxNumberOfPages, boolean isColored, int printedPagesPerMinute) {
        this.maxNumberOfPages = maxNumberOfPages;
        this.isColored = isColored;
        this.printedPagesPerMinute = printedPagesPerMinute;
        this.numberOfPages = 0;
        this.publications = new Map<Publication, Integer>() {
            @Override
            public int size() {
                return 0;
            }

            @Override
            public boolean isEmpty() {
                return false;
            }

            @Override
            public boolean containsKey(Object key) {
                return false;
            }

            @Override
            public boolean containsValue(Object value) {
                return false;
            }

            @Override
            public Integer get(Object key) {
                return null;
            }

            @Override
            public Integer put(Publication key, Integer value) {
                return null;
            }

            @Override
            public Integer remove(Object key) {
                return null;
            }

            @Override
            public void putAll(Map<? extends Publication, ? extends Integer> m) {

            }

            @Override
            public void clear() {

            }

            @Override
            public Set<Publication> keySet() {
                return null;
            }

            @Override
            public Collection<Integer> values() {
                return null;
            }

            @Override
            public Set<Entry<Publication, Integer>> entrySet() {
                return null;
            }
        };
    }

    public int getMaxNumberOfPages() {
        return maxNumberOfPages;
    }

    public boolean isColored() {
        return isColored;
    }

    public int getPrintedPagesPerMinute() {
        return printedPagesPerMinute;
    }

    public int getNumberOfPages() {
        return numberOfPages;
    }

    public Map<Publication, Integer> getPublications() {
        return publications;
    }

    @Override
    public String toString() {
        return "Printer{" +
                "maxNumberOfPages=" + maxNumberOfPages +
                ", isColored=" + isColored +
                ", printedPagesPerMinute=" + printedPagesPerMinute +
                ", numberOfPages=" + numberOfPages +
                ", publications=" + publications +
                '}';
    }

    public void LoadPaper(int loadedPages){ //ne mozhe da e otricatelno
        if (this.numberOfPages + loadedPages >= maxNumberOfPages){
            this.numberOfPages = maxNumberOfPages;
        } else {
            this.numberOfPages += loadedPages;
        }
    }

    public boolean print(Publication publication, int numberOfCopies, boolean coloredPrint) {
        //exception za colored
        //razdelqne na proverkite v otdelni metodi

        if (coloredPrint && !this.isColored){
            //exception
            return false;
        }

        if (publication.getNumberOfPages() * numberOfCopies > this.numberOfPages) {
            //exception?
            return false;
        }

        if (publications.containsKey(publication)){
            publications.put(publication, publications.get(publication) + numberOfCopies);
        } else {
            publications.put(publication, numberOfCopies);
        }

        this.numberOfPages -= publication.getNumberOfPages() * numberOfCopies;

        return true;
    }

    public int numberOfPagesPrinted() {
        int numberOfPages = 0;
        for (Map.Entry<Publication, Integer> entry: publications.entrySet()) {
            numberOfPages += entry.getKey().getNumberOfPages() * entry.getValue();
        }

        return numberOfPages;
    }



}
