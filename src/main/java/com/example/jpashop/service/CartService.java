package com.example.jpashop.service;


import com.example.jpashop.dto.CartItemDto;
import com.example.jpashop.entity.CartItem;
import com.example.jpashop.repository.CartRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class CartService {

    @Autowired
    private CartRepository cartRepository;

    public CartItemDto addItemToCart(CartItemDto cartItemDto) {
        log.info("dfa");
        CartItem cartItem = new CartItem(
                cartItemDto.getProductName(),
                cartItemDto.getQuantity(),
                cartItemDto.getPrice()
        );
        log.info("df");
        CartItem savedItem = cartRepository.save(cartItem);
        return toDto(savedItem);
    }

    public List<CartItemDto> getCartItems() {
        log.info("df");
        List<CartItem> cartItems = cartRepository.findAll();
        log.info("df");
        return cartItems.stream().map(this::toDto).collect(Collectors.toList());
    }

    public void removeItemFromCart(Long id) {
        log.info("df");
        cartRepository.deleteById(id);
    }

    private CartItemDto toDto(CartItem cartItem) {
        log.info("df");
        return CartItemDto.builder()
                .id(cartItem.getId())
                .productName(cartItem.getProductName())
                .quantity(cartItem.getQuantity())
                .price(cartItem.getPrice())
                .build();
    }

}

