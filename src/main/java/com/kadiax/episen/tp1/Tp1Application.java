package com.kadiax.episen.tp1;

import com.kadiax.episen.tp1.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;

@SpringBootApplication
public class Tp1Application {

    @Bean
    @Autowired
    RedisTemplate<String, Product> redisTemplate(RedisConnectionFactory redisConnectionFactory) {

        if (null == redisConnectionFactory) {
            System.out.println("Redis Template Service is not available");
            return null;
        }
        RedisTemplate<String, Product> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(redisConnectionFactory);
        return redisTemplate;
    }
    public static void main(String[] args) {
        SpringApplication.run(Tp1Application.class, args);
    }

}
