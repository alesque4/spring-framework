package com.training.spring.bigcorp.repository;

import com.training.spring.bigcorp.model.Captor;
import com.training.spring.bigcorp.model.Measure;
import com.training.spring.bigcorp.model.PowerSource;
import com.training.spring.bigcorp.model.Site;
import com.training.spring.bigcorp.utils.H2DateConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
@Transactional
public class MeasureDaoImpl implements MeasureDao {

    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;

    @Autowired
    private H2DateConverter h2DateConverter;

    private static String SELECT_WITH_JOIN = "SELECT m.id, m.instant, m.value_in_watt, m.captor_id,"
            + " c.name as captor_name, c.site_id, s.name as site_name "
            + "FROM Measure m inner join Captor c on m.captor_id=c.id inner join site s on c.site_id = s.id ";


    private Measure measureMapper(ResultSet rs, int rowNum) throws SQLException {
        Site site = new Site(rs.getString("site_name"));
        site.setId(rs.getString("site_id"));

        Captor captor = new Captor(rs.getString("captor_name"), PowerSource.FIXED, site);
        captor.setId(rs.getString("captor_id"));

        Measure measure = new Measure(h2DateConverter.convert(rs.getString("instant")),
                rs.getInt("value_in_watt"),
                captor);
        measure.setId(rs.getLong("id"));
        return measure;
    }

    /**
     * Ajoute une mesure dans la bdd
     * @param element La mesure à ajouter
     */
    @Override
    public void create(Measure element) {
        jdbcTemplate.update("INSERT INTO MEASURE (ID, INSTANT, VALUE_IN_WATT, CAPTOR_ID) "+
                        "VALUES (:id, :instant, :valueInWatt, :captor_id)",
                new MapSqlParameterSource()
                        .addValue("id", element.getId())
                        .addValue("instant", element.getInstant())
                        .addValue("valueInWatt", element.getValueInWatt())
                        .addValue("captor_id", element.getCaptor().getId()));
    }

    /**
     * Cherche une mesure par son id
     *
     * @param aLong L'id recherché
     * @return La mesure ou null si elle n'est pas trouvée
     */
    @Override
    public Measure findById(Long aLong) {
        Measure measureLookedFor;
        List<Measure> listMeasure = jdbcTemplate.query(SELECT_WITH_JOIN + " where m.id=:id",
                new MapSqlParameterSource("id", aLong),
                this::measureMapper);
        if (listMeasure.isEmpty()) {
            measureLookedFor = null;
        } else {
            measureLookedFor = listMeasure.get(0);
        }
        return measureLookedFor;
    }

    /**
     * Cherche toutes les mesures
     *
     * @return toutes les mesures
     */
    @Override
    public List<Measure> findAll() {
        return jdbcTemplate.query(SELECT_WITH_JOIN, this::measureMapper);
    }

    /**
     * Mets à jour une mesure
     *
     * @param element La mesure à mettre à jour
     */
    @Override
    public void update(Measure element) {
        jdbcTemplate.update("update MEASURE "
                        + "set instant=:instant, value_in_watt=:value_in_watt, captor_id=:captor_id",
                new MapSqlParameterSource()
                        .addValue("instant", element.getInstant())
                        .addValue("value_in_watt", element.getValueInWatt())
                        .addValue("captor_id", element.getCaptor().getId()));
    }

    /**
     * Supprime une mesure
     *
     * @param aLong L'id de la mesure à supprimer
     */
    @Override
    public void deleteById(Long aLong) {
        jdbcTemplate.update("delete from MEASURE where id=:id",
                new MapSqlParameterSource().addValue("id", aLong));
    }
}
