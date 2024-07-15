package com.example.jpashop.service;

import com.example.jpashop.dto.CartItemDto;
import com.example.jpashop.entity.CartItem;
import com.example.jpashop.repository.CartRepository;
import com.example.jpashop.service.CartService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertTrue;

@Slf4j
@SpringBootTest
@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
public class CartServiceTest {

    @Autowired
    private CartService cartService;

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private JdbcTemplate jdbcTemplate; // JdbcTemplate 추가

    @Test
    @Transactional
    @Rollback(false)
    public void testAddItem() {
        // 기존 코드
        CartItemDto cartItemDto = CartItemDto.builder()
                .productName("Laptop")
                .quantity(1)
                .price(999.99)
                .build();

        CartItem cartItem = CartItem.builder()
                .productName(cartItemDto.getProductName())
                .quantity(cartItemDto.getQuantity())
                .price(cartItemDto.getPrice())
                .build();

        CartItem savedCartItem = cartRepository.save(cartItem);
        CartItemDto addedItem = cartService.addItemToCart(cartItemDto);

        log.info("Before deletion: " + cartRepository.findAll());


    }

    @Test
    @Transactional
    @Rollback(false)
    public void testDeleteItem() {
        // 항목 추가
        CartItemDto cartItemDto = CartItemDto.builder()
                .productName("Laptop")
                .quantity(1)
                .price(999.99)
                .build();

        CartItem cartItem = CartItem.builder()
                .productName(cartItemDto.getProductName())
                .quantity(cartItemDto.getQuantity())
                .price(cartItemDto.getPrice())
                .build();

        CartItem savedCartItem = cartRepository.save(cartItem);
        CartItemDto addedItem = cartService.addItemToCart(cartItemDto);

        log.info("Before deletion: " + cartRepository.findAll());

        // 항목 삭제
        cartService.removeItemFromCart(addedItem.getId());

        log.info("After deletion: " + cartRepository.findAll());

        // 항목이 삭제되었는지 확인
        assertTrue(cartRepository.findById(addedItem.getId()).isEmpty());
    }



}