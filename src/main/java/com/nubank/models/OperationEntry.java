package com.nubank.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor(staticName = "of")
@NoArgsConstructor
public class OperationEntry {
    private String operation;
    @JsonProperty("unit-cost")
    private BigDecimal unitCost;
    private BigDecimal quantity;
}
