package org.example.javastreamapi.generatingnumbers;

import java.util.*;
import java.util.stream.Collectors;

public class StreamCollectorsExample {
    public static void main(String[] args) {
        // 1. Создайте список заказов с разными продуктами и их стоимостями
        List<Order> orders = List.of(
                new Order("Laptop", 1200.0),
                new Order("Smartphone", 800.0),
                new Order("Laptop", 1500.0),
                new Order("Tablet", 500.0),
                new Order("Telephone", 300.0),
                new Order("Smartphone", 900.0)
        );

        // 2. Группируйте заказы по продуктам.
        Map<String, List<Double>> groupByProduct = orders.stream()
                .collect(Collectors.groupingBy(
                        Order::getProduct,
                        Collectors.mapping(
                                Order::getCost,
                                Collectors.toList())));
        System.out.println(groupByProduct);

        // 3. Для каждого продукта найдите общую стоимость всех заказов.
        Set<Map.Entry<String, List<Double>>> summByProductSet = groupByProduct.entrySet();
        Map<String, Double> summByProduct = new HashMap<>();
        for (Map.Entry<String, List<Double>> entry : summByProductSet) {
            Double summ = entry.getValue().stream().reduce(0.0, Double::sum);
            summByProduct.put(entry.getKey(), summ);
        }
        System.out.println(summByProduct);

        // 4. Отсортируйте продукты по убыванию общей стоимости.
        var sortedList = summByProduct.entrySet().stream()
                .sorted((first, second) ->
                        second.getValue().compareTo(first.getValue()))
                .toList();
        System.out.println(sortedList);

        // 5. Выберите три самых дорогих продукта.
        var mostCostProductList = orders.stream()
                .sorted(Collections.reverseOrder(Comparator.comparingDouble(Order::getCost)))
                .limit(3)
                .toList();
        System.out.println(mostCostProductList);

        // 6. Выведите результат: список трех самых дорогих продуктов и их общая стоимость.
        double totalCost = mostCostProductList.stream().reduce(0.0, (a, b) -> a + b.getCost(), Double::sum);
        for (Order order : mostCostProductList) {
            System.out.print(order.getProduct() + ", ");
        }
        System.out.println("Общая стоимость: " + totalCost);
    }
}
