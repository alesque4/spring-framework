package com.training.spring.bigcorp.repository;

import com.training.spring.bigcorp.model.*;
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
import org.springframework.orm.ObjectOptimisticLockingFailureException;
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
        Assertions.assertThat(captor.get().getName()).isEqualTo("Eolienne");
        Assertions.assertThat(captor.get().getVersion()).isEqualTo(0);
    }

    @Test
    public void findByIdShouldReturnNullWhenIdUnknown() {
        Optional<Captor> captor = captorDao.findById("unknown");
        Assertions.assertThat(captor).isEmpty();
    }

    @Test
    public void findAll() {
        List<Captor> captors = captorDao.findAll();
        Assertions.assertThat(captors).hasSize(2).extracting("id", "name", "version")
                .contains(Tuple.tuple("c1", "Eolienne", 0))
                .contains(Tuple.tuple("c2", "Laminoire à chaud", 0));
    }

    @Test
    public void findBySiteId() {
        List<Captor> captors = captorDao.findBySiteId("site1");
        Assertions.assertThat(captors).hasSize(2).extracting("id", "name", "version")
                .contains(Tuple.tuple("c1", "Eolienne", 0))
                .contains(Tuple.tuple("c2", "Laminoire à chaud", 0));
    }

    @Test
    public void create() {
        Site site = new Site("BigFactory");
        site.setId("site1");
        site.setVersion(1);
        Captor captor = new RealCaptor("New captor", site);
        captor.setVersion(1);
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
        Captor newcaptor = new RealCaptor("New captor", site);
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
        Captor exampleProbe = new FixedCaptor("lienn", site);
        List<Captor> captors = captorDao.findAll(Example.of(exampleProbe, matcher));
        Assertions.assertThat(captors)
                .hasSize(1)
                .extracting("id", "name")
                .containsExactly(Tuple.tuple("c1", "Eolienne"));
    }

    @Test
    public void preventConcurrentWrite() {
        Captor captor = captorDao.getOne("c1");

        // A la base le numéro de version est à sa valeur initiale
        Assertions.assertThat(captor.getVersion()).isEqualTo(0);

        // On detache cet objet du contexte de persistence
        entityManager.detach(captor);
        captor.setName("Captor updated");

        // On force la mise à jour en base (via le flush) et on vérifie que l'objet retourné
        // est attaché à la session a été mis à jour
        Captor attachedCaptor = captorDao.save(captor);
        entityManager.flush();
        Assertions.assertThat(attachedCaptor.getName()).isEqualTo("Captor updated");
        Assertions.assertThat(attachedCaptor.getVersion()).isEqualTo(1);

        // Si maintenant je réessaie d'enregistrer captor, comme le numéro de version est
        // à 0 je dois avoir une exception
        Assertions.assertThatThrownBy(() -> captorDao.save(captor))
                .isExactlyInstanceOf(ObjectOptimisticLockingFailureException.class);
    }
}
