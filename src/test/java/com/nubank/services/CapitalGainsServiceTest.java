package com.nubank.services;

import com.nubank.enumerators.OperationTypeEnum;
import com.nubank.models.OperationEntry;
import com.nubank.models.TaxEntry;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.io.File;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class CapitalGainsServiceTest {
    CapitalGainsService underTest = null;
    ReadJsonService readInputService = null;

    @BeforeEach
    void setUp() {
        underTest = new CapitalGainsService();
        readInputService = new ReadJsonService();
    }

    @DisplayName("Should calculate expected taxes for base cases, based on JSON input")
    @ValueSource(strings = {"caseOne", "caseTwo", "caseThree", "caseFour", "caseFive", "caseSix", "caseSeven", "caseEight", "caseOneMixedWithTwo", "allCasesAtOnce"})
    @ParameterizedTest(name = "{index}-{0}")
    @SneakyThrows
    void shouldCalculateCorrectlyForBaseCases(String baseCaseName) {
        String testMassFolderPath = "./json";
        String operationsInputFilepath = String.format("%s/%s.txt", testMassFolderPath, baseCaseName);
        String expectedTaxesFilePath = String.format("%s/%sExpected.txt", testMassFolderPath, baseCaseName);


        List<List<OperationEntry>> multipleOperations = this.readInputService.readInput(new Scanner(new File(operationsInputFilepath)), OperationEntry.class);
        List<List<TaxEntry>> multipleExpectedTaxes = this.readInputService.readInput(new Scanner(new File(expectedTaxesFilePath)), TaxEntry.class);
        List<List<TaxEntry>> calculatedTaxes = underTest.calculateCapitalGains(multipleOperations);

        Assertions.assertEquals(multipleExpectedTaxes, calculatedTaxes);
    }

    @Test
    @DisplayName("Should calculate ZERO taxes when all operations are buys")
    void shouldCalculateZeroTaxesWhenOnlyBuyOperations() {
        List<List<OperationEntry>> operationEntries = new ArrayList<>();
        operationEntries.add(List.of(OperationEntry.of(OperationTypeEnum.BUY.getCode(), BigDecimal.valueOf(120), new BigDecimal(200)), OperationEntry.of(OperationTypeEnum.BUY.getCode(), BigDecimal.valueOf(180), new BigDecimal(200))));

        List<List<TaxEntry>> expected = new ArrayList<>();
        expected.add(List.of(new TaxEntry(BigDecimal.ZERO.setScale(2, RoundingMode.HALF_UP)), new TaxEntry(BigDecimal.ZERO.setScale(2, RoundingMode.HALF_UP))));
        List<List<TaxEntry>> calculatedTaxes = this.underTest.calculateCapitalGains(operationEntries);

        Assertions.assertEquals(expected, calculatedTaxes);
    }

    @Test
    @DisplayName("Should calculate ZERO taxes when all operations are below the taxable minimum")
    void shouldCalculateZeroTaxesWhenAboveTaxableMinimum() {
        List<List<OperationEntry>> operationEntries = new ArrayList<>();
        operationEntries.add(List.of(OperationEntry.of(OperationTypeEnum.BUY.getCode(), BigDecimal.valueOf(120), new BigDecimal(20)), OperationEntry.of(OperationTypeEnum.SELL.getCode(), BigDecimal.valueOf(180), new BigDecimal(20))));

        List<List<TaxEntry>> expected = new ArrayList<>();
        expected.add(List.of(new TaxEntry(BigDecimal.ZERO.setScale(2, RoundingMode.HALF_UP)), new TaxEntry(BigDecimal.ZERO.setScale(2, RoundingMode.HALF_UP))));
        List<List<TaxEntry>> calculatedTaxes = this.underTest.calculateCapitalGains(operationEntries);

        Assertions.assertEquals(expected, calculatedTaxes);
    }


}