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

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
@Transactional
public class MeasureDaoImpl implements MeasureDao {

    @PersistenceContext
    private EntityManager entityManager;

    private final static String SELECT_WITH_JOIN = "select m from Measure m inner join m.captor c";

    /**
     * Ajoute ou modifie une mesure dans la bdd
     * @param measure La mesure à ajouter
     */
    @Override
    public void persist(Measure measure) {
        entityManager.persist(measure);
    }

    /**
     * Cherche une mesure par son id
     *
     * @param id L'id recherché
     * @return La mesure ou null si elle n'est pas trouvée
     */
    @Override
    public Measure findById(Long id) {
        return entityManager.find(Measure.class, id);
    }

    /**
     * Cherche toutes les mesures
     *
     * @return toutes les mesures
     */
    @Override
    public List<Measure> findAll() {
        return entityManager.createQuery(SELECT_WITH_JOIN, Measure.class).getResultList();
    }

    /**
     * Supprime une mesure
     *
     * @param measure L'id de la mesure à supprimer
     */
    @Override
    public void delete(Measure measure) {
        entityManager.remove(measure);
    }
}
