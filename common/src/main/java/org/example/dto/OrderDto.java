package org.example.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class OrderDto {
    private long id;
    private String name;
    private int cost;
    private boolean isPaymentSuccess = false;
    private boolean isShippingSuccess = false;
}
