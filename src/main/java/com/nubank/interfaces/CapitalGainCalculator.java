package com.nubank.interfaces;

import com.nubank.models.OperationEntry;
import com.nubank.models.TaxEntry;

import java.math.BigDecimal;
import java.util.List;

public interface CapitalGainCalculator {
    BigDecimal calculateAveragePrice(BigDecimal currentQuantity, BigDecimal quantityBought, BigDecimal unitCost, BigDecimal averagePrice);

    List<TaxEntry> calculateTaxes(List<OperationEntry> entries, BigDecimal taxPercentage);

}
