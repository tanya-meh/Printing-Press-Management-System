package org.informatics_java.service.printingOffice;

import org.informatics_java.data.Printer;
import org.informatics_java.data.enums.PaperType;
import org.informatics_java.data.Paper;
import org.informatics_java.data.publications.Publication;

import java.math.BigDecimal;
import java.util.EnumMap;
import java.util.Map;

public interface PrintingOfficeService {
    public void setPaperTypeBasePriceEnumMap(EnumMap<PaperType, BigDecimal> paperTypeBasePriceEnumMap);
    public void setPublicationPrintPriceMap(Map<Publication, BigDecimal> publicationPrintPriceMap);
    public void putPublicationPrintPrice(Publication publication, BigDecimal printPrice);
    public BigDecimal getIncome();
    public BigDecimal getExpensesTotal();
    public void buyPaper(Paper paper, int numberOfPapers);
    public boolean printOrder(Publication publication, int numberOfCopies, PaperType paperType, boolean coloredPrint);
    public boolean loadPaperInPrinter(Paper paper, int numberOfPapers, Printer printer);
    public void printPublicationsNumberOfPrints();
    public void writeStatement(String fileName);

}
