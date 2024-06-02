package org.informatics_java.service.calculations;

import org.informatics_java.data.Paper;
import org.informatics_java.data.enums.PageSize;
import org.informatics_java.data.enums.PaperType;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.EnumMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class PaperExpensesCalculatorTest {

    private PaperExpensesCalculator paperExpensesCalculator;
    private Map<Paper, Integer> paperBoughtMap;
    private EnumMap<PaperType, BigDecimal> paperTypeBacePriceEnumMap;
    private double paperPriceIncreasePercent;
    @BeforeEach
    void setUp() {
        paperExpensesCalculator = new PaperExpensesCalculator();

        Paper paper1 = Mockito.mock(Paper.class);
        Paper paper2 = Mockito.mock(Paper.class);
        Paper paper3 = Mockito.mock(Paper.class);
        Paper paper4 = Mockito.mock(Paper.class);
        Paper paper5 = Mockito.mock(Paper.class);

        Mockito.when(paper1.getPaperType()).thenReturn(PaperType.STANDARD);
        Mockito.when(paper2.getPaperType()).thenReturn(PaperType.STANDARD);
        Mockito.when(paper3.getPaperType()).thenReturn(PaperType.GLOSSY);
        Mockito.when(paper4.getPaperType()).thenReturn(PaperType.NEWSPRINT);
        Mockito.when(paper5.getPaperType()).thenReturn(PaperType.NEWSPRINT);

        Mockito.when(paper1.getPageSize()).thenReturn(PageSize.A5);
        Mockito.when(paper2.getPageSize()).thenReturn(PageSize.A4);
        Mockito.when(paper3.getPageSize()).thenReturn(PageSize.A3);
        Mockito.when(paper4.getPageSize()).thenReturn(PageSize.A2);
        Mockito.when(paper5.getPageSize()).thenReturn(PageSize.A1);

        paperBoughtMap = Map.of(paper1, 10, paper2, 10, paper3, 10, paper4, 10, paper5, 10);

        paperTypeBacePriceEnumMap = new EnumMap<>(PaperType.class);
        paperTypeBacePriceEnumMap.put(PaperType.STANDARD, BigDecimal.valueOf(0.10));
        paperTypeBacePriceEnumMap.put(PaperType.GLOSSY, BigDecimal.valueOf(0.20));
        paperTypeBacePriceEnumMap.put(PaperType.NEWSPRINT, BigDecimal.valueOf(0.05));

        paperPriceIncreasePercent = 50;
    }

    @Test
    void pricePerStandardA1Paper() {
        Paper paper = Mockito.mock(Paper.class);
        Mockito.when(paper.getPaperType()).thenReturn(PaperType.STANDARD);
        Mockito.when(paper.getPageSize()).thenReturn(PageSize.A1);

        BigDecimal expected = BigDecimal.valueOf(0.53).setScale(2, RoundingMode.HALF_UP);
        assertEquals(expected, paperExpensesCalculator.pricePerPaperByTypeAndSize(paperTypeBacePriceEnumMap, paper, paperPriceIncreasePercent));
    }

    @Test
    void pricePerGlossyA1Paper() {
        Paper paper = Mockito.mock(Paper.class);
        Mockito.when(paper.getPaperType()).thenReturn(PaperType.GLOSSY);
        Mockito.when(paper.getPageSize()).thenReturn(PageSize.A1);

        BigDecimal expected = BigDecimal.valueOf(1.02).setScale(2, RoundingMode.HALF_UP);
        assertEquals(expected, paperExpensesCalculator.pricePerPaperByTypeAndSize(paperTypeBacePriceEnumMap, paper, paperPriceIncreasePercent));
    }

    @Test
    void pricePerNewsprintA1Paper() {
        Paper paper = Mockito.mock(Paper.class);
        Mockito.when(paper.getPaperType()).thenReturn(PaperType.NEWSPRINT);
        Mockito.when(paper.getPageSize()).thenReturn(PageSize.A1);

        BigDecimal expected = BigDecimal.valueOf(0.27).setScale(2, RoundingMode.HALF_UP);
        assertEquals(expected, paperExpensesCalculator.pricePerPaperByTypeAndSize(paperTypeBacePriceEnumMap, paper, paperPriceIncreasePercent));
    }

    @Test
    void paperExpenses_test() {
        BigDecimal expected = BigDecimal.valueOf(11.5).setScale(2);
        assertEquals(expected, paperExpensesCalculator.paperExpenses(paperBoughtMap, paperTypeBacePriceEnumMap, paperPriceIncreasePercent));
    }

}