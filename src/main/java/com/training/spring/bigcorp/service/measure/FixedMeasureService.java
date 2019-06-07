package com.training.spring.bigcorp.service.measure;

import com.training.spring.bigcorp.model.Captor;
import com.training.spring.bigcorp.model.Measure;
import com.training.spring.bigcorp.model.MeasureStep;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Service("FixedMeasureService")
@Lazy
public class FixedMeasureService implements MeasureService {

    @Value("${bigcorp.measure.default-fixed}")
    private Integer defaultValue;

    @Override
    public List<Measure> readMeasures(Captor captor, Instant start, Instant end, MeasureStep step) {
        System.out.println("Appel de readMeasures : "+this);
        List<Measure> measures = new ArrayList<>();
        Instant current = start;

        //Vérification des paramètres
        checkReadMeasuresAgrs(captor, start, end, step);

        while(current.isBefore(end)){
            measures.add(new Measure(current, defaultValue, captor));
            current = current.plusSeconds(step.getDurationInSecondes());
        }
        return measures;
    }
}