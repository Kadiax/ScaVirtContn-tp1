package com.kadiax.episen.tp1.dao;

import com.kadiax.episen.tp1.model.Product;

import java.util.List;
import java.util.Map;

public interface ProductDao  {
    public Map<Integer, Product> findAll();
    public Product findById(int id);
    public Product save(Product product);
}
