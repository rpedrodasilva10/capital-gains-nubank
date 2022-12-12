package com.nubank;

import com.nubank.models.OperationEntry;
import com.nubank.services.CapitalGainsService;
import com.nubank.services.ReadJsonService;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;
import java.util.Scanner;

@SpringBootApplication
public class CapitalGainsApplication {

    public static void main(String[] args) {
        final CapitalGainsService capitalGainsService = new CapitalGainsService();
        final ReadJsonService readJsonService = new ReadJsonService();

        List<List<OperationEntry>> operationsLists = readJsonService.readInput(new Scanner(System.in), OperationEntry.class);

        capitalGainsService.calculateCapitalGains(operationsLists);
    }
}
