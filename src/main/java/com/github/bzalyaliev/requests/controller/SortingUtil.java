package com.github.bzalyaliev.requests.controller;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;

public class SortingUtil {

    private static final List<String> requiredProperties = List.of("date", "status", "deadline");

    public static List<Order> sortQueriesToOrder(String[] sortQueries) {

        List<Order> orders = new ArrayList<>();

        if (sortQueries[0].contains(",")) {
            for (String sortQuery : sortQueries) {
                String[] splitSortQuery = sortQuery.split(",");
                String propertyValue = splitSortQuery[0];
                String directionValue = splitSortQuery[1];
                checkCorrectPropertyValue(propertyValue);
                checkCorrectDirectionValue(directionValue);
                orders.add(new Order(Sort.Direction.fromString(directionValue), propertyValue));
            }
        } else {
            String propertyValue = sortQueries[0];
            String directionValue = sortQueries[1];
            checkCorrectPropertyValue(propertyValue);
            checkCorrectDirectionValue(directionValue);
            orders.add(new Order(Sort.Direction.fromString(directionValue), propertyValue));
        }
        return orders;
    }

    private static void checkCorrectDirectionValue(String directionValue) {
        if (Arrays.stream(Sort.Direction.values()).map(Enum::name).noneMatch(d -> d.equalsIgnoreCase(directionValue))) {
            throw new BadRequestException("Invalid direction. Expected asc or desc");
        }
    }

    private static void checkCorrectPropertyValue(String propertyValue) {
        if (requiredProperties.stream().noneMatch(property -> property.equals(propertyValue))) {
            throw new BadRequestException("Invalid property. Expected date, status or deadline");
        }
    }
}



