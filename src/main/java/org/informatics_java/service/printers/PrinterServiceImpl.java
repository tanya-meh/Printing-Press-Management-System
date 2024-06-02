package org.informatics_java.service.printers;

import org.informatics_java.data.Paper;
import org.informatics_java.data.Printer;
import org.informatics_java.data.enums.PaperType;
import org.informatics_java.data.publications.Publication;
import org.informatics_java.exceptions.IllegalQuantityException;
import org.informatics_java.exceptions.IncompatiblePrinterException;

public class PrinterServiceImpl implements PrinterService{

    /**
     * @param printer
     * @return total number of papers loaded in the printer
     */
    private int numberOfPapersTotal(Printer printer) {
        return printer.getNumberOfPapersLoadedMap().values()
                .stream()
                .reduce(0, Integer::sum);
    }


    /**
     *
     * @param printer
     * @return number of papers that can be loaded in the printer before coming to the maximum capacity
     */
    private int freePapersSpace(Printer printer) {
        return printer.getMaxNumberOfPapers() - this.numberOfPapersTotal(printer);
    }

    /**
     * The method loads a number of papers from a certain type and size of paper in the printer (in numberOfPapersLoadedMap)
     * @param printer
     * @param numberOfPapers
     * @param paper
     * @return number of papers there is no space for
     * @throws IllegalQuantityException
     */
    @Override
    public int loadPaper(Printer printer, int numberOfPapers, Paper paper) throws IllegalQuantityException {
        if (numberOfPapers <= 0) {
            throw new IllegalQuantityException("Number of papers must be a positive integer.");
        }

        int currNumOfPapers;

        currNumOfPapers = printer.getNumberOfPapersLoadedMap().getOrDefault(paper, 0);

        if (this.numberOfPapersTotal(printer) + numberOfPapers >= printer.getMaxNumberOfPapers()){
            printer.putNumberOfPapers(paper, currNumOfPapers + this.freePapersSpace(printer));
            return numberOfPapers - this.freePapersSpace(printer);
        } else {
            printer.putNumberOfPapers(paper, currNumOfPapers + numberOfPapers);
            return 0;
        }
    }

    /**
     * @param printer
     * @param publication
     * @param numberOfCopies
     * @param paperType
     * @return boolean value showing if the printer has enough paper from the type and size wanted
     */
    private boolean hasEnoughPaper(Printer printer, Publication publication, int numberOfCopies, PaperType paperType) {
        if (publication.getNumberOfPages() * numberOfCopies < printer.getNumberOfPapersLoadedMap().get(new Paper(paperType, publication.getPageSize()))) {
            return true;
        }
        return false;
    }

    /**
     * The method decreases the number of papers of certain paper type and of size equal to the size of the publication
     * @param printer
     * @param publication
     * @param numberOfCopies
     * @param paperType
     * @return the number of papers from the paper type and size there was before retrieving a number of it
     */
    private int retrieveLoadedPaper(Printer printer, Publication publication, int numberOfCopies, PaperType paperType) {
        int currNumOfPapers = printer.getNumberOfPapersLoadedMap().get(new Paper(paperType, publication.getPageSize()));
        int newNumOfPapers = currNumOfPapers - publication.getNumberOfPages() * numberOfCopies;
        return printer.putNumberOfPapers(new Paper(paperType, publication.getPageSize()), newNumOfPapers);
    }

    /**
     * The method adds the number of copies printed to the current number in printer's publicationsNumberOfPrintsMap
     * and decreases the current number of paper in printer's numberOfPapersLoadedMap
     * @param printer
     * @param publication
     * @param numberOfCopies
     * @param paperType
     * @param coloredPrint
     * @return boolean value showing if the print was successful
     * @throws IllegalQuantityException
     * @throws IncompatiblePrinterException
     */
    @Override
    public boolean print(Printer printer, Publication publication, int numberOfCopies, PaperType paperType, boolean coloredPrint) throws IllegalQuantityException, IncompatiblePrinterException {
        if (numberOfCopies <= 0) {
            throw new IllegalQuantityException("Number of copies must be a positive integer.");
        }

        if (coloredPrint && !printer.isColored()){
            throw new IncompatiblePrinterException("This printer only prints in black ink.");
        }

        if (!coloredPrint && printer.isColored()){
            throw new IncompatiblePrinterException("This printer only prints in colored ink.");
        }

        if (hasEnoughPaper(printer, publication, numberOfCopies, paperType)) {
            if (printer.getPublicationsNumberOfPrintsMap().containsKey(publication)) {
                printer.putNumberOfPublications(publication, printer.getPublicationsNumberOfPrintsMap().get(publication) + numberOfCopies);
            } else {
                printer.putNumberOfPublications(publication, numberOfCopies);
            }

            this.retrieveLoadedPaper(printer, publication, numberOfCopies, paperType);

            return true;
        }

        return false;
    }

    /**
     * @param printer
     * @return total number of pages printed on this printer
     */
    @Override
    public int numberOfPagesPrinted(Printer printer) {
        return printer.getPublicationsNumberOfPrintsMap().entrySet()
                .stream()
                .mapToInt(entry -> entry.getKey().getNumberOfPages() * entry.getValue())
                .sum();
    }
}
