package com.example.jpashop.controller;

import com.example.jpashop.dto.CartItemDto;
import com.example.jpashop.service.CartService;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cart")
@Slf4j
public class CartController {

    @Autowired
    private CartService cartService;

    @PostMapping("/add")
    public ResponseEntity<CartItemDto> addItemToCart(@RequestBody CartItemDto cartItemDto) {
        CartItemDto addedItem = cartService.addItemToCart(cartItemDto);
        log.info("df");
        return ResponseEntity.ok(addedItem);

    }

    @GetMapping
    public ResponseEntity<List<CartItemDto>> getCartItems() {
        List<CartItemDto> cartItems = cartService.getCartItems();
        log.info("df");
        return ResponseEntity.ok(cartItems);
    }

    @DeleteMapping("/remove/{id}")
    public ResponseEntity<Void> removeItemFromCart(@PathVariable Long id) {
        cartService.removeItemFromCart(id);
        log.info("df");
        return ResponseEntity.ok().build();
    }

}