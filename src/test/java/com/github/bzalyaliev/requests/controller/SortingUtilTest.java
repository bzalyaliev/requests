package com.github.bzalyaliev.requests.controller;

import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Sort;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

class SortingUtilTest {

    @Test
    void itReturnsOrders() {
        String[] sortQueries = new String[]{"status,asc", "date,desc"};
        List<Sort.Order> actualOrders = SortingUtil.sortQueriesToOrder(sortQueries);
        assertNotNull(actualOrders);
        assertThat(actualOrders).hasSize(2);
        assertThat(actualOrders.get(0).getProperty()).isEqualTo("status");
        assertThat(actualOrders.get(0).getDirection()).isSameAs(Sort.Direction.ASC);
        assertThat(actualOrders.get(1).getProperty()).isEqualTo("date");
        assertThat(actualOrders.get(1).getDirection()).isSameAs(Sort.Direction.DESC);
    }

    @Test
    void itReturnsOrdersWhenReceivedOneQuery() {
        String[] sortQueries = new String[]{"date", "desc"};
        List<Sort.Order> actualOrders = SortingUtil.sortQueriesToOrder(sortQueries);
        assertNotNull(actualOrders);
        assertThat(actualOrders).hasSize(1);
        assertThat(actualOrders.get(0).getProperty()).isEqualTo("date");
        assertThat(actualOrders.get(0).getDirection()).isSameAs(Sort.Direction.DESC);
    }

    @Test
    void itThrowsExceptionWhenInvalidDirection() {
        String[] sortQueries = new String[]{"date", "dsc"};
        assertThatThrownBy(() -> SortingUtil.sortQueriesToOrder(sortQueries))
                .isInstanceOf(BadRequestException.class);
    }

    @Test
    void itThrowsExceptionWhenInvalidProperty() {
        String[] sortQueries = new String[]{"dte", "desc"};
        assertThatThrownBy(() -> SortingUtil.sortQueriesToOrder(sortQueries))
                .isInstanceOf(BadRequestException.class);
    }
}