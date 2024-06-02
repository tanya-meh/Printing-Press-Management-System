package org.informatics_java.service.printingOffice;

import org.informatics_java.data.Printer;
import org.informatics_java.data.enums.PaperType;
import org.informatics_java.data.Paper;
import org.informatics_java.data.publications.Publication;
import org.informatics_java.exceptions.IllegalQuantityException;
import org.informatics_java.exceptions.IncompatiblePrinterException;

import java.math.BigDecimal;

public interface PrintingOfficeService {
    public BigDecimal getExpensesTotal();
    public int buyPaper(Paper paper, int numberOfPapers) throws IllegalQuantityException;
    public boolean printOrder(Publication publication, int numberOfCopies, PaperType paperType, boolean coloredPrint) throws IllegalQuantityException, IncompatiblePrinterException;
    public boolean loadPaperInPrinter(Paper paper, int numberOfPapers, Printer printer) throws IllegalQuantityException;

}
