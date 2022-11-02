package cl.dcid.sumador.service;

import cl.dcid.sumador.SumadorApplication;
import cl.dcid.sumador.interfaces.PercentageGetterService;
import cl.dcid.sumador.interfaces.SumService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = SumadorApplication.class)
public class SumServiceImplTests {

    @Autowired
    SumService sumService;

    @MockBean
    PercentageGetterService percentageGetterServiceMock;

    @Test()
    void sumCorrectly() {
        when(percentageGetterServiceMock.getPercent()).thenReturn(10.0);
        assertThat(sumService.sum(10, 20)).isEqualTo(33);
    }
}
