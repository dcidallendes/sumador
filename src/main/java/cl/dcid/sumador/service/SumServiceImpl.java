package cl.dcid.sumador.service;

import cl.dcid.sumador.interfaces.PercentageGetterService;
import cl.dcid.sumador.interfaces.SumService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Service;

@Service
public class SumServiceImpl implements SumService {

    @Autowired
    PercentageGetterService percentageGetterService;

    @Override
    public double sum(double sumando1, double sumando2) {
        double suma = sumando1 + sumando2;
        double percent = percentageGetterService.getPercent();

        return suma + suma * (percent / 100);
    }
}
