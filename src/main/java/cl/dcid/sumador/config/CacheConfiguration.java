package cl.dcid.sumador.config;

import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@Configuration
@EnableCaching
@EnableScheduling
public class CacheConfiguration {

    public static final String PERCENT_CACHE_NAME = "percentage";

    @Bean
    public CacheManager cacheManager() {
        return new ConcurrentMapCacheManager(PERCENT_CACHE_NAME);
    }

    @CacheEvict(allEntries = true, cacheNames = {PERCENT_CACHE_NAME})
    @Scheduled(fixedDelay = 30 * 60 * 1000 ,  initialDelay = 500)
    public void evictCache() {
    }

}
