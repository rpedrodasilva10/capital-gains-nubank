package com.nubank.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nubank.interfaces.impl.NuGainCalculatorImpl;
import com.nubank.models.OperationEntry;
import com.nubank.models.TaxEntry;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class CapitalGainsService {
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final NuGainCalculatorImpl calculator = new NuGainCalculatorImpl();

    public List<List<TaxEntry>> calculateCapitalGains(List<List<OperationEntry>> inputEntries) {
        List<List<TaxEntry>> calculatedTaxes = new ArrayList<>();

        inputEntries.forEach(operations -> {
            List<TaxEntry> capitalGainTaxes = this.calculator.calculateTaxes(operations, BigDecimal.valueOf(0.2));
            calculatedTaxes.add(capitalGainTaxes);

            printObject(capitalGainTaxes);
        });

        return calculatedTaxes;

    }

    @SneakyThrows
    private void printObject(Object object) {
        System.out.println(this.objectMapper.writeValueAsString(object));
    }
}
