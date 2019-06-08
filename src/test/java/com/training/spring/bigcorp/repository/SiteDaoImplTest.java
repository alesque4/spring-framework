package com.training.spring.bigcorp.repository;

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

import java.util.List;

@RunWith(SpringRunner.class)
@JdbcTest
@ContextConfiguration(classes = {DaoTestConfig.class})
public class SiteDaoImplTest {

    @Autowired
    private SiteDao siteDao;



    @Before
    public void init() {

    }

    @Test
    public void findById() {
        Site site = siteDao.findById("site1");
        Assertions.assertThat(site.getName()).isEqualTo("Bigcorp Lyon");
    }

    @Test
    public void findByIdShouldReturnNullWhenIdUnknown() {
        Site site = siteDao.findById("unknown");
        Assertions.assertThat(site).isNull();
    }

    @Test
    public void findAll() {
        List<Site> sites = siteDao.findAll();
        Assertions.assertThat(sites).hasSize(1).extracting("id", "name").contains(Tuple.tuple("site1", "Bigcorp Lyon"));
    }

    @Test
    public void create() {
        Assertions.assertThat(siteDao.findAll()).hasSize(1);
        siteDao.create(new Site("Bigcorp Nantes"));
        Assertions.assertThat(siteDao.findAll()).hasSize(2).extracting(Site::getName).contains("Bigcorp Lyon", "Bigcorp Nantes");
    }

    @Test
    public void update() {
        Site site = siteDao.findById("site1");
        Assertions.assertThat(site.getName()).isEqualTo("Bigcorp Lyon");
        site.setName("Site updated");
        siteDao.update(site);
        site = siteDao.findById("site1");
        Assertions.assertThat(site.getName()).isEqualTo("Site updated");
    }

    @Test
    public void deleteById() {
        Site site = new Site("Bigcorp delete");
        siteDao.create(site);
        Assertions.assertThat(siteDao.findById(site.getId())).isNotNull();
        siteDao.deleteById(site.getId());
        Assertions.assertThat(siteDao.findById(site.getId())).isNull();
    }

    @Test
    public void deleteByIdShouldThrowExceptionWhenIdIsUsedAsForeignKey() {
        Assertions.assertThatThrownBy(
                () -> siteDao.deleteById("site1")).isExactlyInstanceOf(DataIntegrityViolationException.class);
    }
}

