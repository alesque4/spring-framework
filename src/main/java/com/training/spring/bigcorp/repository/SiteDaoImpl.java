package com.training.spring.bigcorp.repository;

import com.training.spring.bigcorp.model.Site;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class SiteDaoImpl implements SiteDao {

    private NamedParameterJdbcTemplate jdbcTemplate;

    public SiteDaoImpl(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void create(Site element) {

    }

    @Override
    public Site findById(String s) {
        return null;
    }

    @Override
    public List<Site> findAll() {
        return null;
    }

    @Override
    public void update(Site element) {

    }

    @Override
    public void deleteById(String s) {

    }

    // TODO reste du code
}