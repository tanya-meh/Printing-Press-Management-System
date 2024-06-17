package org.informatics_java.service.printers;

import org.informatics_java.data.Paper;
import org.informatics_java.data.Printer;
import org.informatics_java.data.enums.PageSize;
import org.informatics_java.data.enums.PaperType;
import org.informatics_java.data.publications.Publication;
import org.informatics_java.exceptions.IllegalQuantityException;
import org.informatics_java.exceptions.IncompatiblePrinterException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class PrinterServiceImplTest {

    private PrinterServiceImpl printerService;
    private Printer printer;
    private int numberOfPapersToLoad;
    private Paper paper;
    private Publication publication;
    private int numberOfCopies;
    private PaperType paperType;
    private boolean coloredPrint;

    @BeforeEach
    void setUp() {
        printerService = new PrinterServiceImpl();

        printer = Mockito.mock(Printer.class);
        Mockito.when(printer.getMaxNumberOfPapers()).thenReturn(300);
        Mockito.when(printer.getNumberOfPapersLoadedMap()).thenReturn(Map.of(new Paper(PaperType.STANDARD, PageSize.A4), 100, new Paper(PaperType.GLOSSY, PageSize.A4), 100));
        Mockito.when(printer.isColored()).thenReturn(true);

        paper = Mockito.mock(Paper.class);
        Mockito.when(paper.getPageSize()).thenReturn(PageSize.A4);
        Mockito.when(paper.getPaperType()).thenReturn(PaperType.STANDARD);

        publication = Mockito.mock(Publication.class);
        Mockito.when(publication.getPageSize()).thenReturn(PageSize.A4);
        Mockito.when(publication.getNumberOfPages()).thenReturn(10);

        numberOfCopies = 20;
        paperType = PaperType.STANDARD;
        coloredPrint = true;
    }

    @Test
    void when_thereIsPaperLoaded_correctness_of_numberOfPapersTotal() {
        int expected = 200;
        assertEquals(expected, printerService.numberOfPapersTotal(printer));
    }

    @Test
    void when_thereAreNoPapers_correctness_of_numberOfPapersTotal() {
        Mockito.when(printer.getNumberOfPapersLoadedMap()).thenReturn(Map.of());
        int expected = 0;
        assertEquals(expected, printerService.numberOfPapersTotal(printer));
    }

    @Test
    void when_thereAreNoPapers_freePaperSpace() {
        Mockito.when(printer.getNumberOfPapersLoadedMap()).thenReturn(Map.of());
        int expected = 300;
        assertEquals(expected, printerService.freePapersSpace(printer));
    }

    @Test
    void when_thereAreSomePapers_freePaperSpace() {
        int expected = 100;
        assertEquals(expected, printerService.freePapersSpace(printer));
    }

    @Test
    void when_thereAreMaxNumPapers_freePaperSpace() {
        Mockito.when(printer.getNumberOfPapersLoadedMap()).thenReturn(Map.of(new Paper(PaperType.STANDARD, PageSize.A4), 200, new Paper(PaperType.GLOSSY, PageSize.A4), 100));
        int expected = 0;
        assertEquals(expected, printerService.freePapersSpace(printer));
    }

    @Test
    void when_numberOfPapersIsNotPositive_then_loadPaper_throwIllegalQuantityException() {
        numberOfPapersToLoad = -20;
        assertThrows(IllegalQuantityException.class, () -> printerService.loadPaper(printer, numberOfPapersToLoad, paper));
    }

    @Test
    void when_numberOfPapersExceedsMaxNumWhenLoaded_then_loadPaper_returnPositiveValue() {
        numberOfPapersToLoad = 150;
        int expected = 50;
        assertEquals(expected, printerService.loadPaper(printer, numberOfPapersToLoad, paper));
    }

    @Test
    void when_numberOfPapersDoesNotExceedMaxNumWhenLoaded_then_loadPaper_returnZero() {
        numberOfPapersToLoad = 80;
        int expected = 0;
        assertEquals(expected, printerService.loadPaper(printer, numberOfPapersToLoad, paper));
    }

    @Test
    void when_thereIsNoneOfTheParticularPaper() {
        paperType = PaperType.NEWSPRINT;
        assertFalse(printerService.hasEnoughPaper(printer, publication, numberOfCopies, paperType));
    }

    @Test
    void when_thereIsNotEnoughPaper() {
        assertFalse(printerService.hasEnoughPaper(printer, publication, numberOfCopies, paperType));
    }

    @Test
    void when_thereIsEnoughPaper() {
        numberOfCopies = 5;
        assertTrue(printerService.hasEnoughPaper(printer, publication, numberOfCopies, paperType));
    }

    @Test
    void when_numberOfCopiesIsNotPositiveInteger_then_print_throwIllegalQuantityException() {
        numberOfCopies = -20;
        assertThrows(IllegalQuantityException.class, () -> printerService.print(printer, publication, numberOfCopies, paperType, coloredPrint));
    }

    @Test
    void when_coloredPrintTrueButPrinterIsNotColored_then_print_throwIncompatiblePrinterException() {
        Mockito.when(printer.isColored()).thenReturn(false);
        assertThrows(IncompatiblePrinterException.class, () -> printerService.print(printer, publication, numberOfCopies, paperType, coloredPrint));
    }

    @Test
    void when_coloredPrintFalseButPrinterIsColored_then_print_throwIncompatiblePrinterException() {
        Mockito.when(printer.isColored()).thenReturn(true);
        coloredPrint = false;
        assertThrows(IncompatiblePrinterException.class, () -> printerService.print(printer, publication, numberOfCopies, paperType, coloredPrint));
    }

    @Test
    void when_ValidNumberOfCopies_and_compatiblePrinter_but_printerDoesNotHaveTheParticularPaper_then_print_throwIncompatiblePrinterException() {
        numberOfCopies = 5;
        paperType = PaperType.NEWSPRINT;
        assertFalse(printerService.print(printer, publication, numberOfCopies, paperType, coloredPrint));
    }

    @Test
    void when_ValidNumberOfCopies_and_compatiblePrinter_but_printerDoesNotHaveEnoughPaper_then_print_throwIncompatiblePrinterException() {
        numberOfCopies = 20;
        assertFalse(printerService.print(printer, publication, numberOfCopies, paperType, coloredPrint));
    }

    @Test
    void when_ValidNumberOfCopies_and_compatiblePrinter_but_PaperNotInPrinter_then_print_throwIncompatiblePrinterException() {
        numberOfCopies = 5;
        paperType = PaperType.NEWSPRINT;
        assertFalse(printerService.print(printer, publication, numberOfCopies, paperType, coloredPrint));
    }

    @Test
    void when_ValidNumberOfCopies_and_compatiblePrinter_and_printerHasEnoughPaper_then_print_throwIncompatiblePrinterException() {
        numberOfCopies = 5;
        assertTrue(printerService.print(printer, publication, numberOfCopies, paperType, coloredPrint));
    }

    @Test
    void when_zeroPages_then_NumberOfPagesPrinted_returnZero() {
        assertEquals(0, printerService.numberOfPagesPrinted(printer));
    }

    @Test
    void when_printOnPrinter_then_NumberOfPagesPrinted_returnPositiveInteger() {
        numberOfCopies = 5;
        Mockito.when(printer.getPublicationsNumberOfPrintsMap()).thenReturn(Map.of(publication, numberOfCopies));
        assertEquals(50, printerService.numberOfPagesPrinted(printer));
    }
}