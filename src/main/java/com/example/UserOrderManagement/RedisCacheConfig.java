package com.example.UserOrderManagement;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;

import java.time.Duration;

@Configuration
public class RedisCacheConfig {

    @Bean
    public RedisCacheManager cacheManager(RedisConnectionFactory connectionFactory) { //Spring injects RedisConnectionFactory automatically to create one RedisCacheManager

        RedisCacheConfiguration config =
                RedisCacheConfiguration.defaultCacheConfig() //spring default cache config
                        .serializeKeysWith(
                                RedisSerializationContext.SerializationPair.fromSerializer(
                                        new StringRedisSerializer() //Store Cache keys as plain strings
                                )
                        ) //
                        .serializeValuesWith(
                                RedisSerializationContext.SerializationPair.fromSerializer(
                                        RedisSerializer.json() //Convert Java objects to JSON
                                )
                        )
                        .entryTtl(Duration.ofMinutes(10)); //10 minutes - Time to Live (TTL)

        return RedisCacheManager.builder(connectionFactory)
                .cacheDefaults(config)
                .build(); //Make redis the default cache manager
    }
}



