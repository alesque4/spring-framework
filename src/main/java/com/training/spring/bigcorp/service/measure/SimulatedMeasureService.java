package com.training.spring.bigcorp.service.measure;

import com.training.spring.bigcorp.config.properties.BigCorpApplicationProperties;
import com.training.spring.bigcorp.model.Measure;
import com.training.spring.bigcorp.model.MeasureStep;
import com.training.spring.bigcorp.model.SimulatedCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.time.Duration;
import java.time.Instant;
import java.util.Arrays;
import java.util.List;

@Service("SimulatedMeasureService")
public class SimulatedMeasureService implements MeasureService<SimulatedCaptor> {

    private RestTemplate restTemplate;

    @Autowired
    private BigCorpApplicationProperties properties;

    public SimulatedMeasureService(RestTemplateBuilder builder) {
        this.restTemplate = builder.setConnectTimeout(Duration.ofSeconds(1)).build();
    }

    public BigCorpApplicationProperties getProperties() {
        return properties;
    }

    public void setProperties(BigCorpApplicationProperties properties) {
        this.properties = properties;
    }

    @Override
    public List<Measure> readMeasures(SimulatedCaptor captor, Instant start, Instant end, MeasureStep step) {
        checkReadMeasuresAgrs(captor, start, end, step);

        //Construction de la requête pour demander des mesures
        UriComponentsBuilder builder = UriComponentsBuilder
                .fromUriString("http://localhost:8090/measures?" +
                        "start="+ start + "&end=" + end +
                        "&min=" + captor.getMinPowerInWatt() +
                        "&max=" + captor.getMaxPowerInWatt() +
                        "&step=" + step.getDurationInSecondes());

        Measure[] measures = restTemplate.getForObject(builder.toUriString(), Measure[].class);
        return Arrays.asList(measures);
    }
}
