package com.example.jpashop.service;

import com.example.jpashop.dto.ProductDto;
import com.example.jpashop.entity.Product;
import com.example.jpashop.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductService {

        private final ProductRepository productRepository;


        @Transactional(rollbackFor = Exception.class)
        public void create(ProductDto productDTO) throws IOException {

            log.info("df");

            try {
                Product product = Product.toDTO(productDTO);

                log.info("product id = {}", product.getId());
                log.info("product price = {}", product.getPrice());
                log.info("product stock = {}", product.getStockQuantity());
                log.info("product boardWriter = {}", product.getBoardwriter());
                log.info("product productname = {}", product.getProductname());
                productRepository.save(product);
                log.info("df");
                } catch (Exception e) {
                throw new IllegalStateException(e);
            }
        }

        @Transactional(readOnly = true)
        public List<Product> findItems(){
            log.info("df");
            return productRepository.findAll();
        }

        @Transactional(readOnly = true)
        public Product findItemByUuid(String uuid) {
            log.info("df");
            return productRepository.findByUuid(uuid);
        }

        @Transactional(rollbackFor = Exception.class)
        public void update(String uuid,ProductDto productDTO) {
            log.info("df");

            try {
                Optional<Product> optionalItem = Optional.ofNullable(productRepository.findByUuid(uuid));

                if (optionalItem.isPresent()) {
                    Product updateProduct = optionalItem.get();
                    productRepository.updateItemByUUID(uuid, productDTO.getName(),
                            productDTO.getPrice(), productDTO.getStockQuantity(),
                            productDTO.getBoardwriter(),
                            productDTO.getProductname());
                }

                log.info("df");
            } catch (Exception e) {
                log.info("df");
                throw new IllegalStateException(e);
            }
        }
        @Transactional(rollbackFor = Exception.class)
        public void delete(String uuid){
            log.info("df");
            try {
            Optional<Product> optionalItem = Optional.ofNullable(productRepository.findByUuid(uuid));

            if(optionalItem.isPresent()){
                Product product = optionalItem.get();
                productRepository.delete(product);
            }
                log.info("df");
            } catch (Exception e) {
                throw new IllegalStateException(e);
            }
            log.info("df");
        }

}

