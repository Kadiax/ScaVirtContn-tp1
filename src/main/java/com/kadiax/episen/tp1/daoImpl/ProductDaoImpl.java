package com.kadiax.episen.tp1.daoImpl;

import com.kadiax.episen.tp1.dao.ProductDao;
import com.kadiax.episen.tp1.model.Product;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Repository
public class ProductDaoImpl implements ProductDao {


    private RedisTemplate<String, Product> redisTemplate;

    private HashOperations hashOperations;

    public ProductDaoImpl(RedisTemplate<String, Product> redisTemplate) {
        this.redisTemplate = redisTemplate;

        hashOperations = redisTemplate.opsForHash();
    }
    @Override
    public Map<Integer, Product> findAll() {
        return hashOperations.entries("Product");
    }

    @Override
    public Product findById(int id) {
        return (Product) hashOperations.get("Product", id);
    }

    @Override
    public Product save(Product product) {
        hashOperations.put("Product", product.getId(), product);
        return product;
    }

}
