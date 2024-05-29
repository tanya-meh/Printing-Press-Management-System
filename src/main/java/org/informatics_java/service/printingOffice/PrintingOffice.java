package org.informatics_java.service.printingOffice;

import org.informatics_java.data.enums.PaperType;
import org.informatics_java.data.paper.Paper;
import org.informatics_java.data.publications.Publication;
import org.informatics_java.service.printers.Printer;

import java.math.BigDecimal;

public interface PrintingOffice {
    public BigDecimal getExpensesTotal();
    public int buyPaper(Paper paper, int numberOfPapers);
    public boolean printOrder(Publication publication, int numberOfCopies, PaperType paperType, boolean coloredPrint);
    public boolean loadPaperInPrinter(int numberOfPapers, Paper paper, Printer printer);

}
