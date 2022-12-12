package com.nubank.enumerators;


import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum OperationTypeEnum {
    SELL("sell"),
    BUY("buy");

    final String code;
}
