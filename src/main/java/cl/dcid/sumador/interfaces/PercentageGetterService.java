package cl.dcid.sumador.interfaces;

import cl.dcid.sumador.config.CacheConfiguration;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.retry.annotation.Retryable;

public interface PercentageGetterService {

    @Retryable(maxAttempts = 3)
    @Cacheable(value = CacheConfiguration.PERCENT_CACHE_NAME)
    double getPercent();
}
