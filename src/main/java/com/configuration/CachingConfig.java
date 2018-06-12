package com.configuration;

import com.google.common.cache.CacheBuilder;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.guava.GuavaCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by alejandrosantamaria on 10/06/18.
 */
@Configuration
@EnableCaching
public class CachingConfig {

    @Value("${max.cache.size}")
    private int maxCacheSize;

    @Bean
    public CacheManager cacheManager() {
        GuavaCacheManager cacheManager = new GuavaCacheManager("cacheManager");
        cacheManager.setCacheNames(Lists.newArrayList("ethtx", "ethusd"));

        CacheBuilder<Object, Object> cacheBuilder = CacheBuilder.newBuilder()
                .maximumSize(maxCacheSize);

        cacheManager.setCacheBuilder(cacheBuilder);
        return cacheManager;
    }
}
