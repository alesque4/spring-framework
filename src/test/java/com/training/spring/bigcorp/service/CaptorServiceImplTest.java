package com.training.spring.bigcorp.service;

import com.training.spring.bigcorp.model.Captor;
import com.training.spring.bigcorp.service.measure.FixedMeasureService;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Set;

@SpringBootTest
public class CaptorServiceImplTest {
    private CaptorServiceImpl captorService = new CaptorServiceImpl(new FixedMeasureService());

    @Test
    public void findBySiteShouldReturnNullWhenIdIsNull() {
        // Initialisation
        String siteId = null;

        // Appel du SUT
        Set<Captor> captors = captorService.findBySite(siteId);

        // Vérification
        Assertions.assertThat(captors).isEmpty();
    }

    @Test
    public void findBySite() {
        // Initialisation
        String siteId = "siteId";

        // Appel du SUT
        Set<Captor> captors = captorService.findBySite(siteId);

        // Vérification
        Assertions.assertThat(captors).hasSize(1);
        Assertions.assertThat(captors).extracting(Captor::getName).contains("Capteur A");
    }
}