package cl.dcid.sumador.service;

import cl.dcid.sumador.interfaces.PercentageGetterService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class PercentageGetterServiceImpl implements PercentageGetterService {

    private static final String RANDOM_NUMBER_URL = "https://www.randomnumberapi.com/api/v1.0/random?min=0&max=100";
    @Override
    public double getPercent() {
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<Double[]> response = restTemplate.getForEntity(RANDOM_NUMBER_URL, Double[].class);

        return response.getBody()[0];
    }
}
