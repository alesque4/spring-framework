package com.training.spring.bigcorp.repository;

import com.training.spring.bigcorp.model.Site;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class SiteDaoImpl implements SiteDao {

    private NamedParameterJdbcTemplate jdbcTemplate;

    private static String SELECT = "SELECT s.id, s.name FROM SITE s ";

    public SiteDaoImpl(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    /**
     * Recherche tous les sites
     * @return tous les sites
     */
    @Override
    public List<Site> findAll() {
        return jdbcTemplate.query(SELECT, this::siteMapper);
    }

    private Site siteMapper(ResultSet rs, int rowNum) throws SQLException {
        Site site = new Site(rs.getString("name"));
        site.setId(rs.getString("id"));
        return site;
    }

    /**
     * Insère un site dans la bdd
     * @param element le site à ajouter
     */
    @Override
    public void create(Site element) {
        jdbcTemplate.update("INSERT INTO SITE VALUES (:id, :name)",
                new MapSqlParameterSource()
                        .addValue("id", element.getId())
                        .addValue("name", element.getName()));
    }

    /**
     * Cherche un site avec un id
     * @param id l'id recherché
     * @return Le site recherché. null si le site n'est pas trouvé
     */
    @Override
    public Site findById(String id) {
        Site siteLookedFor;
        List<Site> listSite = jdbcTemplate.query(SELECT + " WHERE id=:id",
                new MapSqlParameterSource().addValue("id", id),
                this::siteMapper);
        if(listSite.isEmpty()){
            siteLookedFor = null;
        }else{
            siteLookedFor = listSite.get(0);
        }
        return siteLookedFor;
    }

    /**
     * Mets à jour un site
     * @param site Le site à mettre à jour
     */
    @Override
    public void update(Site site) {
        jdbcTemplate.update("update SITE set name = :name where id =:id", new MapSqlParameterSource()
                .addValue("id", site.getId())
                .addValue("name", site.getName()));
    }

    /**
     * Supprime un site
     * @param id L'id du site à supprimer
     */
    @Override
    public void deleteById(String id) {
        jdbcTemplate.update("delete from SITE where id =:id",
                new MapSqlParameterSource()
                        .addValue("id", id));
    }

}