package com.training.spring.bigcorp.service;

import com.training.spring.bigcorp.model.Captor;
import com.training.spring.bigcorp.model.PowerSource;
import com.training.spring.bigcorp.model.Site;
import com.training.spring.bigcorp.repository.CaptorDao;
import com.training.spring.bigcorp.service.measure.FixedMeasureService;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@SpringBootTest
public class CaptorServiceImplTest {

    @Mock
    private CaptorDao captorDao = Mockito.mock(CaptorDao.class);

    @InjectMocks
    private CaptorServiceImpl captorService = new CaptorServiceImpl(new FixedMeasureService());

    @Before
    public void init(){
        MockitoAnnotations.initMocks(this);
    }

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
        List<Captor> mockAnswer = new ArrayList<Captor>();
        Captor captor = new Captor("Capteur A", new Site("SiteTest"));
        captor.setPowerSource(PowerSource.FIXED);
        mockAnswer.add(captor);
        Mockito.when(captorDao.findBySiteId(siteId)).thenReturn(mockAnswer);

        // Appel du SUT
        Set<Captor> captors = captorService.findBySite(siteId);

        // Vérification
        Assertions.assertThat(captors).hasSize(1);
        Assertions.assertThat(captors).extracting(Captor::getName).contains("Capteur A");
    }
}