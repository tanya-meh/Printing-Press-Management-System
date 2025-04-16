package org.informatics_java.service.calculations;

import org.informatics_java.data.Paper;
import org.informatics_java.data.enums.PaperType;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.EnumMap;
import java.util.Map;
import java.util.Set;

public class PaperExpensesCalculator {

    /**
     * The method calculates the total expenses, related to the paper
     * The number of papers ever bought for every paper type and size is multiplied by its price and summed up
     * @param paperBoughtMap
     * @param paperTypeBasePriceEnumMap
     * @param paperPriceIncreasePercent
     * @return the sum of expenses for every paper type and size that the Printing office has bought
     */
    public BigDecimal paperExpenses(Map<Paper, Integer> paperBoughtMap, EnumMap<PaperType, BigDecimal> paperTypeBasePriceEnumMap, double paperPriceIncreasePercent) {
        Set<Map.Entry<Paper, Integer>> entrySet = paperBoughtMap.entrySet();
        BigDecimal expenses = entrySet.stream()
                .map(mapEntry -> pricePerPaperByTypeAndSize(paperTypeBasePriceEnumMap, mapEntry.getKey(), paperPriceIncreasePercent)
                        .multiply(BigDecimal.valueOf(mapEntry.getValue())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);


        return expenses;
    }

    /**
     * The method calculates the price of one piece of paper, based on its size and type
     * There is a paperPriceIncreasePercent, that is used to compute the price of every next size,
     * starting from the smallest, the price of witch is the base price in paperTypeBasePriceEnumMap,
     * continuing until the page size we need the price for
     * @param paperTypeBasePriceEnumMap
     * @param paper
     * @param paperPriceIncreasePercent
     * @return the price of one piece of paper, based on its size and type
     */
    public BigDecimal pricePerPaperByTypeAndSize(EnumMap<PaperType, BigDecimal> paperTypeBasePriceEnumMap, Paper paper, double paperPriceIncreasePercent) {
        BigDecimal price = paperTypeBasePriceEnumMap.get(paper.getPaperType());

        for (int i = 0; i < paper.getPageSize().ordinal(); i++){
            price = price
                    .add((price
                            .multiply(BigDecimal.valueOf(paperPriceIncreasePercent/100)))
                            .setScale(2, RoundingMode.HALF_UP));
        }

        return price;
    }

}
