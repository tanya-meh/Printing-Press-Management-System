package org.informatics_java.service.calculations;

import org.informatics_java.data.enums.PaperType;
import org.informatics_java.data.paper.Paper;
import org.informatics_java.service.printers.Printer;

import java.math.BigDecimal;
import java.util.EnumMap;
import java.util.Map;
import java.util.Set;

public class PaperExpensesCalculator {

    public BigDecimal paperExpenses(EnumMap<PaperType, BigDecimal> paperTypeBasePriceEnumMap, Map<Paper, Integer> papersInStockMap, BigDecimal paperPriceIncreasePercent) {
        BigDecimal expenses = BigDecimal.ZERO;

        Set<Map.Entry<Paper, Integer>> entrySet = papersInStockMap.entrySet();
        for (Map.Entry<Paper, Integer> mapEntry: entrySet) {
            expenses = expenses
                    .add(pricePerPaperByTypeAndSize(paperTypeBasePriceEnumMap, mapEntry.getKey(), paperPriceIncreasePercent)
                            .multiply(BigDecimal
                                    .valueOf(mapEntry.getValue())));
        }

        return expenses;
    }

    private BigDecimal pricePerPaperByTypeAndSize(EnumMap<PaperType, BigDecimal> paperTypeBasePriceEnumMap, Paper paper, BigDecimal paperPriceIncreasePercent) {
        BigDecimal price = paperTypeBasePriceEnumMap.get(paper.getPaperType());

        for (int i = 0; i < paper.getPageSize().ordinal(); i++){
            price = price
                    .add(price
                            .multiply(paperPriceIncreasePercent
                                    .divide(BigDecimal.valueOf(100))));
        }

        return price;
    }

}
