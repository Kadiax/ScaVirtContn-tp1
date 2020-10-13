package com.kadiax.episen.dao;

import com.kadiax.episen.model.Product;

import java.util.Map;

public interface ProductDao  {
    public Map<Integer, Product> findAll();
    public Product findById(int id);
    public Product save(Product product);
}
