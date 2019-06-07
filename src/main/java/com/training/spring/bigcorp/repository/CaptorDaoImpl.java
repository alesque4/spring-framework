package com.training.spring.bigcorp.repository;

import com.training.spring.bigcorp.model.Captor;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CaptorDaoImpl implements CaptorDao {

    private NamedParameterJdbcTemplate jdbcTemplate;

    public CaptorDaoImpl(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Captor> findBySiteId(String siteId) {
        return null;
    }

    @Override
    public void create(Captor element) {

    }

    @Override
    public Captor findById(String s) {
        return null;
    }

    @Override
    public List<Captor> findAll() {
        return null;
    }

    @Override
    public void update(Captor element) {

    }

    @Override
    public void deleteById(String s) {

    }

    // TODO reste du code
}