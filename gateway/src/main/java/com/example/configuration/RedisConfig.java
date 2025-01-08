package com.example.configuration;

import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.RedisConnectionFailureException;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.util.Collection;

@Configuration
@EnableCaching
public class RedisConfig {
    public static final String REDIS_KEY = "domainCache";

    @Bean
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory connectionFactory) {
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(connectionFactory);
        template.setKeySerializer(new StringRedisSerializer());
        template.setValueSerializer(new GenericJackson2JsonRedisSerializer());
        return template;
    }


//    @Bean
//    public CacheManager cacheManager(RedisConnectionFactory redisConnectionFactory) {
//        return new CacheManager() {
//            private final CacheManager redisCacheManager = RedisCacheManager.builder(redisConnectionFactory).build();
//            private final CacheManager inMemoryCacheManager = new ConcurrentMapCacheManager(REDIS_KEY);
//
//            @Override
//            public Cache getCache(String name) {
//                try (RedisConnection connection = redisConnectionFactory.getConnection()) {
//                    connection.isClosed();
//                    return redisCacheManager.getCache(name);
//                } catch (Exception e) {
//                    return inMemoryCacheManager.getCache(name);
//                }
//            }
//
//            @Override
//            public Collection<String> getCacheNames() {
//                return redisCacheManager.getCacheNames();
//            }
//        };
//    }
}

