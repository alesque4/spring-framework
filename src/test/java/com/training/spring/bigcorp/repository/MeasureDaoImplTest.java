package com.training.spring.bigcorp.repository;

import com.training.spring.bigcorp.model.Captor;
import com.training.spring.bigcorp.model.Measure;
import com.training.spring.bigcorp.model.PowerSource;
import com.training.spring.bigcorp.model.Site;
import org.assertj.core.api.Assertions;
import org.assertj.core.groups.Tuple;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@RunWith(SpringRunner.class)
@JdbcTest
@ContextConfiguration(classes = {DaoTestConfig.class})
public class MeasureDaoImplTest {


    @Autowired
    private MeasureDao measureDao;

    private Site site;
    private Captor captor;

    @Before
    public void init() {
        site = new Site("site1");
        captor = new Captor("cTest", PowerSource.FIXED, site);
        captor.setId("c1");
    }

    @Test
    public void findById() {
        Measure measure = measureDao.findById(1L);
        Assertions.assertThat(measure.getValueInWatt()).isEqualTo(1_000_000L);
    }

    @Test
    public void findByIdShouldReturnNullWhenIdUnknown() {
        Measure measure = measureDao.findById(Long.MAX_VALUE);
        Assertions.assertThat(measure).isNull();
    }

    @Test
    public void findAll() {
        List<Measure> measures = measureDao.findAll();
        Assertions.assertThat(measures).hasSize(10).extracting("id", "valueInWatt")
                .contains(Tuple.tuple(1L, 1000000))
                .contains(Tuple.tuple(2L, 1000124))
                .contains(Tuple.tuple(3L, 1001234))
                .contains(Tuple.tuple(6L, -9000000))
                .contains(Tuple.tuple(10L, -909678));
    }

    @Test
    public void create() {
        Assertions.assertThat(measureDao.findAll()).hasSize(10);
        measureDao.create(new Measure(Instant.now(), 1234, captor));
        Assertions.assertThat(measureDao.findAll()).hasSize(11).extracting(Measure::getValueInWatt)
                .contains(1234, 1000000);
    }

    @Test
    public void update() {
        Measure measure = measureDao.findById(1L);
        Assertions.assertThat(measure.getValueInWatt()).isEqualTo(1_000_000);
        measure.setValueInWatt(1234);
        measureDao.update(measure);
        measure = measureDao.findById(1L);
        Assertions.assertThat(measure.getValueInWatt()).isEqualTo(1234);
    }

    @Test
    public void deleteById() {
        Measure measure = new Measure(Instant.now(), 1234, captor);
        measureDao.create(measure);
        List<Measure> measures = measureDao.findAll();
        Measure measureRead = measures.stream()
                .filter(m -> m.getValueInWatt() == 1234)
                .reduce((a,b) -> {throw new IllegalStateException("Should only have 1 element");})
                .get();
        Assertions.assertThat(measureRead.getId()).isNotNull();
        measureDao.deleteById(measureRead.getId());
        Assertions.assertThat(measureDao.findById(measureRead.getId())).isNull();
    }
}
