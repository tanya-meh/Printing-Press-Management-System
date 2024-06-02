package org.informatics_java.service.printers;

import org.informatics_java.data.Paper;
import org.informatics_java.data.Printer;
import org.informatics_java.data.enums.PaperType;
import org.informatics_java.data.publications.Publication;
import org.informatics_java.exceptions.IllegalQuantityException;
import org.informatics_java.exceptions.IncompatiblePrinterException;

public interface PrinterService {
    public int loadPaper(Printer printer, int numberOfPapers, Paper paper);
    public boolean print(Printer printer, Publication publication, int numberOfCopies, PaperType paperType, boolean coloredPrint);
    public int numberOfPagesPrinted(Printer printer);
}
