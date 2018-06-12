package com.configuration;

import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by alejandrosantamaria on 10/06/18.
 */
@Configuration
@EnableCaching
public class CachingConfig {

    @Bean
    public CacheManager eththxCache() {
        return new ConcurrentMapCacheManager("eththx");
    }

    @Bean
    public CacheManager ethusdCache() {
        return new ConcurrentMapCacheManager("ethusd");
    }
}
