package com.training.spring.bigcorp.repository;

import com.training.spring.bigcorp.model.Captor;
import com.training.spring.bigcorp.model.PowerSource;
import com.training.spring.bigcorp.model.Site;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class CaptorDaoImpl implements CaptorDao {

    private NamedParameterJdbcTemplate jdbcTemplate;

    private static String SELECT_WITH_JOIN = "SELECT c.id, c.name, c.site_id, s.name as site_name, s.id as site_id "
            + "FROM Captor c inner join Site s on c.site_id = s.id ";

    public CaptorDaoImpl(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    /**
     * Cherche tous les capteurs d'un site
     * @param siteId L'id du site où on cherche les capteurs
     * @return
     */
    @Override
    public List<Captor> findBySiteId(String siteId) {
        return jdbcTemplate.query(SELECT_WITH_JOIN + " WHERE site_id='" + siteId+"'", this::rowMapper);
    }

    private Captor rowMapper(ResultSet resultSet, int numRow) throws SQLException {
        Captor captor = null;
        Site site = new Site(resultSet.getString("site_name"));
        site.setId(resultSet.getString("site_id"));
        captor = new Captor(resultSet.getString("name"), PowerSource.FIXED, site);
        captor.setId(resultSet.getString("id"));
        site.getCaptors().add(captor);
        return captor;
    }

    /**
     * Ajoute un capteur dans la bdd
     * @param captor Le capteur à ajouter
     */
    @Override
    public void create(Captor captor) {
        jdbcTemplate.update("INSERT INTO CAPTOR (id, name, site_id) VALUES (:id, :name, :site_id)",
                new MapSqlParameterSource()
                        .addValue("id", captor.getId())
                        .addValue("name", captor.getName())
                        .addValue("site_id", captor.getSite().getId()));
    }

    @Override
    public Captor findById(String id) {
        Captor captorLookedFor;
        List<Captor> listCaptors = jdbcTemplate.query(SELECT_WITH_JOIN + " WHERE c.id = '" + id+"'", this::rowMapper);
        if (listCaptors.isEmpty()) {
            captorLookedFor = null;
        } else {
            captorLookedFor = listCaptors.get(0);
        }
        return captorLookedFor;
    }

    /**
     * Cherche tous les capteurs
     * @return tous les capteurs
     */
    @Override
    public List<Captor> findAll() {
        return jdbcTemplate.query(SELECT_WITH_JOIN, this::rowMapper);
    }

    /**
     * Mets à jour un capteur
     * @param captor Le capteur à mettre à jour
     */
    @Override
    public void update(Captor captor) {
        jdbcTemplate.update("update CAPTOR set name = :name where id =:id",
                new MapSqlParameterSource()
                        .addValue("id", captor.getId())
                        .addValue("name", captor.getName()));
    }

    /**
     * Supprime un capteur dans la bdd
     * @param id L'id du capteur à supprimer
     */
    @Override
    public void deleteById(String id) {
        jdbcTemplate.update("delete from CAPTOR where id =:id",
                new MapSqlParameterSource()
                        .addValue("id", id));
    }
}