package com.nubank.services.impl;

import com.nubank.interfaces.impl.NuGainCalculatorImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

class NuGainCalculatorImplTest {

    NuGainCalculatorImpl underTest = null;

    @BeforeEach
    void setUp() {
        this.underTest = new NuGainCalculatorImpl();
    }

    @Test
    @DisplayName("Should calculate expected rounded average price for base case")
    void shouldCalculateRoundedAverageCorrectly() {
        BigDecimal expectedValue = BigDecimal.valueOf(16.66);
        BigDecimal averagePrice = this.underTest.calculateAveragePrice(BigDecimal.valueOf(10), BigDecimal.valueOf(5), BigDecimal.valueOf(10), BigDecimal.valueOf(20));
        System.out.println("Expected value: " + expectedValue + "\nCalculated value: " + averagePrice);
        Assertions.assertEquals(BigDecimal.valueOf(16.66), averagePrice);
    }
}