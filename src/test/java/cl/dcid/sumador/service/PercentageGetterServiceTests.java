package cl.dcid.sumador.service;

import cl.dcid.sumador.config.CacheConfiguration;
import cl.dcid.sumador.interfaces.PercentageGetterService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.cache.interceptor.SimpleKey;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SpringBootTest
public class PercentageGetterServiceTests {

    @Autowired
    private CacheManager cacheManager;

    @Autowired
    private PercentageGetterService percentageGetterService;

    @EnableCaching
    @Configuration
    static class AppDefCachingTestConfiguration {
        @Bean
        public PercentageGetterService percentageGetterServiceMock() {
            PercentageGetterService mockService = mock(PercentageGetterService.class);
            when(mockService.getPercent()).thenReturn(10.0);
            return mockService;
        }

        @Bean
        public CacheManager cacheManager() {
            return new ConcurrentMapCacheManager(CacheConfiguration.PERCENT_CACHE_NAME);
        }
    }

    @Test
    void cacheCreated() {
        double percentage = percentageGetterService.getPercent();
        Cache cache = cacheManager.getCache(CacheConfiguration.PERCENT_CACHE_NAME);
        SimpleKey key = new SimpleKey();
        Cache.ValueWrapper cached = cache.get(key);

        assertThat(cached).isNotNull();
        assertThat(cached.get()).isEqualTo(percentage);
    }


}
