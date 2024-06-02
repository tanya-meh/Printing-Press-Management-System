package org.informatics_java.service.printers;

import org.informatics_java.data.Paper;
import org.informatics_java.data.Printer;
import org.informatics_java.data.enums.PageSize;
import org.informatics_java.data.enums.PaperType;
import org.informatics_java.data.publications.Publication;
import org.informatics_java.exceptions.IllegalQuantityException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;

class PrinterServiceImplTest {

    private PrinterServiceImpl printerService;
    private Printer printer;
    private int numberOfPapersToLoad;
    private Paper paper;
    private Publication publication;
    private int numberOfCopies;
    private PaperType paperType;
    private boolean isColored;

    @BeforeEach
    void setUp() {
        printerService = new PrinterServiceImpl();

        printer = Mockito.mock(Printer.class);
        Mockito.when(printer.getMaxNumberOfPapers()).thenReturn(300);

        paper = Mockito.mock(Paper.class);
        Mockito.when(paper.getPageSize()).thenReturn(PageSize.A4);
        Mockito.when(paper.getPaperType()).thenReturn(PaperType.STANDARD);

        publication = Mockito.mock(Publication.class);
        Mockito.when(publication.getPageSize()).thenReturn(PageSize.A4);

        /*paperType = Mockito.mock(PaperType.class);
        Mockito.when(paperType.)*/
    }

    @Test
    void when_numberOfPapersIsNotPositive_then_loadPaper_throwIllegalQuantityException() {
        numberOfPapersToLoad = -20;
        assertThrows(IllegalQuantityException.class, () -> printerService.loadPaper(printer, numberOfPapersToLoad, paper));
    }

    @Test
    void when_numberOfPapersExceedsMaxNumWhenLoaded_then_loadPaper_returnPositiveValue() {
        numberOfPapersToLoad = -20;
        assertThrows(IllegalQuantityException.class, () -> printerService.loadPaper(printer, numberOfPapersToLoad, paper));
    }

    @Test
    void testPrint() {
    }

    @Test
    void testNumberOfPagesPrinted() {
    }
}