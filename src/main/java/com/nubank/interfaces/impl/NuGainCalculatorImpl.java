package com.nubank.interfaces.impl;

import com.nubank.enumerators.OperationTypeEnum;
import com.nubank.interfaces.CapitalGainCalculator;
import com.nubank.models.OperationEntry;
import com.nubank.models.TaxEntry;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

public class NuGainCalculatorImpl implements CapitalGainCalculator {

    @Override
    public List<TaxEntry> calculateTaxes(List<OperationEntry> entries, BigDecimal taxPercentage) {

        List<TaxEntry> operationTaxes = new ArrayList<>();
        BigDecimal averagePrice = BigDecimal.ZERO;
        BigDecimal currentQuantity = BigDecimal.ZERO;
        BigDecimal currentProfit = BigDecimal.ZERO;

        // Calcular PM a cada operação nova
        for (OperationEntry operation : entries) {
            BigDecimal tax = BigDecimal.ZERO.setScale(2, RoundingMode.HALF_UP);
            BigDecimal operationValue = operation.getQuantity().multiply(operation.getUnitCost()).setScale(2, RoundingMode.HALF_UP);

            // Trata vendas
            if (OperationTypeEnum.SELL.getCode().equalsIgnoreCase(operation.getOperation())) {
                currentQuantity = currentQuantity.subtract(operation.getQuantity());
                BigDecimal currentAveragePriceOperationValue = operation.getQuantity().multiply(averagePrice);

                if (operationValue.compareTo(currentAveragePriceOperationValue) != 0) {
                    BigDecimal lossOrGainValue = operationValue.subtract(currentAveragePriceOperationValue);
                    currentProfit = currentProfit.add(lossOrGainValue);
                }

                if (this.shouldTaxOperation(currentProfit, operationValue)) {
                    // Taxa e zera lucro
                    tax = currentProfit.multiply(taxPercentage).setScale(2, RoundingMode.HALF_UP);
                    currentProfit = BigDecimal.ZERO;
                }
            } else {
                averagePrice = this.calculateAveragePrice(currentQuantity, operation.getQuantity(), operation.getUnitCost(), averagePrice);
                currentQuantity = currentQuantity.add(operation.getQuantity());

            }
            operationTaxes.add(new TaxEntry(tax));
        }

        return operationTaxes;
    }

    private boolean shouldTaxOperation(BigDecimal currentProfit, BigDecimal operationValue) {
        return currentProfit.compareTo(BigDecimal.ZERO) > 0 && operationValue.compareTo(BigDecimal.valueOf(20_000)) >= 0;
    }

    @Override

    public BigDecimal calculateAveragePrice(BigDecimal currentQuantity, BigDecimal quantityBought, BigDecimal unitCost, BigDecimal currentAveragePrice) {
        /* Calcula o preço médio conforme fórmula:
         pm = ((quantidade-de-acoes-atual * media-ponderadaatual)
         + (quantidade-de-acoes-compradas * valor-de-compra))
         / (quantidade-de-acoes-atual + quantidade-de-acoes-compradas)
        */

        BigDecimal newQuantityAverage = currentQuantity.multiply(currentAveragePrice);
        BigDecimal newAverage = quantityBought.multiply(unitCost);
        BigDecimal totalQuantity = currentQuantity.add(quantityBought);

        // Ao invés de arredondar, trunca valor em duas casas, conforme e-mail enviado para tirar a dúvida
        BigDecimal averagePrice = (newQuantityAverage.add(newAverage)).divide(totalQuantity, 2, RoundingMode.FLOOR);
        return averagePrice;
    }

}
