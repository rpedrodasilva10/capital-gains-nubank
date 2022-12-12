package com.nubank.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

@Service
public class ReadJsonService {
    @SneakyThrows
    public <T> List<List<T>> readInput(Scanner scanner, Class<T> elementClass) {

        String lineWithJsonList = "";
        List<List<T>> inputEntries = new ArrayList<>();

        while (scanner.hasNextLine()) {
            lineWithJsonList = scanner.nextLine();

            if (lineWithJsonList.isEmpty()) break;

            inputEntries.add(jsonArrayToList(lineWithJsonList, elementClass));
        }
        scanner.close();

        return inputEntries;
    }

    @SneakyThrows
    public <T> List<T> jsonArrayToList(String json, Class<T> elementClass) {
        ObjectMapper objectMapper = new ObjectMapper();
        CollectionType listType =
                objectMapper.getTypeFactory().constructCollectionType(ArrayList.class, elementClass);
        return objectMapper.readValue(json, listType);
    }
}
