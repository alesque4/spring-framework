package com.training.spring.bigcorp.repository;

import com.training.spring.bigcorp.model.Captor;
import com.training.spring.bigcorp.model.PowerSource;
import com.training.spring.bigcorp.model.Site;
import org.assertj.core.api.Assertions;
import org.assertj.core.groups.Tuple;
import org.hibernate.exception.ConstraintViolationException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;
import java.util.List;
import java.util.Optional;

@RunWith(SpringRunner.class)
@DataJpaTest
@ComponentScan
public class CaptorDaoImplTest {

    @Autowired
    private CaptorDao captorDao;

    @Autowired
    private EntityManager entityManager;

    @Test
    public void findById() {
        Optional<Captor> captor = captorDao.findById("c1");
        Assertions.assertThat(captor)
                .get()
                .extracting("name")
                .containsExactly("Eolienne");
    }

    @Test
    public void findByIdShouldReturnNullWhenIdUnknown() {
        Optional<Captor> captor = captorDao.findById("unknown");
        Assertions.assertThat(captor).isEmpty();
    }

    @Test
    public void findAll() {
        List<Captor> captors = captorDao.findAll();
        Assertions.assertThat(captors).hasSize(2).extracting("id", "name")
                .contains(Tuple.tuple("c1", "Eolienne"))
                .contains(Tuple.tuple("c2", "Laminoire à chaud"));
    }

    @Test
    public void findBySiteId() {
        List<Captor> captors = captorDao.findBySiteId("site1");
        Assertions.assertThat(captors).hasSize(2).extracting("id", "name")
                .contains(Tuple.tuple("c1", "Eolienne"))
                .contains(Tuple.tuple("c2", "Laminoire à chaud"));
    }

    @Test
    public void create() {
        Site site = new Site("BigFactory");
        site.setId("site1");
        Captor captor = new Captor("New captor", site);
        captor.setPowerSource(PowerSource.FIXED);
        Assertions.assertThat(captorDao.findAll()).hasSize(2);
        captorDao.save(captor);
        Assertions.assertThat(captorDao.findAll()).hasSize(3).extracting(Captor::getName)
                .contains("Eolienne", "Laminoire à chaud", "New captor");
    }

    @Test
    public void update() {
        Captor captor = captorDao.findById("c1").orElseThrow(() -> new RuntimeException("Optional is empty"));
        Assertions.assertThat(captor.getName()).isEqualTo("Eolienne");
        captor.setName("Captor updated");
        captorDao.save(captor);
        captor = captorDao.findById("c1").orElseThrow(() -> new RuntimeException("Optional is empty"));
        Assertions.assertThat(captor.getName()).isEqualTo("Captor updated");
    }

    @Test
    public void delete() {
        Site site = new Site("Bigcorp Lyon");
        site.setId("site1");
        Captor newcaptor = new Captor("New captor", site);
        newcaptor.setPowerSource(PowerSource.FIXED);
        captorDao.save(newcaptor);
        Assertions.assertThat(captorDao.findById(newcaptor.getId())).isNotEmpty();
        captorDao.delete(newcaptor);
        Assertions.assertThat(captorDao.findById(newcaptor.getId())).isEmpty();
    }

    @Test
    public void deleteByIdShouldThrowExceptionWhenIdIsUsedAsForeignKey() {
        Captor captor = captorDao.findById("c1").orElseThrow(() -> new RuntimeException("Optional is empty"));
        Assertions
                .assertThatThrownBy(() -> {
                    captorDao.delete(captor);
                    entityManager.flush();
                })
                .isExactlyInstanceOf(PersistenceException.class)
                .hasCauseExactlyInstanceOf(ConstraintViolationException.class);
    }

    @Test
    public void findByExample() {
        ExampleMatcher matcher = ExampleMatcher.matching()
                .withIgnorePaths("id", "powerSource", "defaultValueInWatt", "site.name", "captors")
                .withMatcher("name", match -> match.ignoreCase().contains())
                .withMatcher("site.name", match -> match.ignoreCase().contains());
        Site site = new Site("Florange");
        site.setId("site1");
        Captor exampleProbe = new Captor("lienn", site);
        List<Captor> captors = captorDao.findAll(Example.of(exampleProbe, matcher));
        Assertions.assertThat(captors)
                .hasSize(1)
                .extracting("id", "name")
                .containsExactly(Tuple.tuple("c1", "Eolienne"));
    }
}
