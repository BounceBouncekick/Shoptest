package com.example.jpashop.service;

import com.example.jpashop.dto.ProductDto;
import com.example.jpashop.entity.Product;
import com.example.jpashop.repository.ProductRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class ProductService {

        private final ProductRepository productRepository;

        private final PlatformTransactionManager transactionManager;

        public void create(ProductDto productDTO) throws IOException {


            TransactionStatus status = transactionManager.getTransaction(new DefaultTransactionDefinition());

            try {
                Product product = Product.toDTO(productDTO);

                log.info("product id = {}", product.getId());
                log.info("product price = {}", product.getPrice());
                log.info("product stock = {}", product.getStockQuantity());
                log.info("product boardWriter = {}", product.getBoardwriter());
                log.info("product productname = {}", product.getProductname());
                productRepository.save(product);

                transactionManager.commit(status); // 성공 시 커밋
                } catch (Exception e) {
                 transactionManager.rollback(status); // 실패 시 롤백
                throw new IllegalStateException(e);
            }
        }

        public List<Product> findItems(){
            return productRepository.findAll();
        }

        public Product findItemByUuid(String uuid) {
            return productRepository.findByUuid(uuid);
        }

        public void update(String uuid,ProductDto productDTO) {

            TransactionStatus status = transactionManager.getTransaction(new DefaultTransactionDefinition());

            try {
                Optional<Product> optionalItem = Optional.ofNullable(productRepository.findByUuid(uuid));

                if (optionalItem.isPresent()) {
                    Product updateProduct = optionalItem.get();
                    productRepository.updateItemByUUID(uuid, productDTO.getName(),
                            productDTO.getPrice(), productDTO.getStockQuantity(),
                            productDTO.getBoardwriter(),
                            productDTO.getProductname());
                }

                transactionManager.commit(status); // 성공 시 커밋
            } catch (Exception e) {
                transactionManager.rollback(status); // 실패 시 롤백
                throw new IllegalStateException(e);
            }
        }
        @Transactional
        public void delete(String uuid){
            TransactionStatus status = transactionManager.getTransaction(new DefaultTransactionDefinition());

            try {
            Optional<Product> optionalItem = Optional.ofNullable(productRepository.findByUuid(uuid));

            if(optionalItem.isPresent()){
                Product product = optionalItem.get();
                productRepository.delete(product);
            }
                transactionManager.commit(status); // 성공 시 커밋
            } catch (Exception e) {
                transactionManager.rollback(status); // 실패 시 롤백
                throw new IllegalStateException(e);
            }
        }
}

