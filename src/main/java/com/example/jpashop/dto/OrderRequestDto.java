package com.example.jpashop.dto;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderRequestDto {
    private OrderItemDto orderItem;
    private DeliveryDto delivery;


}

