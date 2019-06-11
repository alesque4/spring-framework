package com.training.spring.bigcorp.repository;

import com.training.spring.bigcorp.model.Captor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
@Transactional
public class CaptorDaoImpl implements CaptorDao {

    @PersistenceContext
    private EntityManager entityManager;

    private final static String SELECT_WITH_JOIN = "select c from Captor c inner join c.site s";

    /**
     * Ajoute ou modifie un capteur dans la bdd
     * @param captor Le capteur à ajouter
     */
    @Override
    public void persist(Captor captor) {
        entityManager.persist(captor);
    }

    /**
     * Cherche tous les capteurs d'un site
     * @param siteId L'id du site où on cherche les capteurs
     * @return
     */
    @Override
    public List<Captor> findBySiteId(String siteId) {
        return entityManager
                .createQuery(SELECT_WITH_JOIN+" where s.id =:siteId", Captor.class)
                .setParameter("siteId", siteId)
                .getResultList();

    }

    /**
     * Cherche un capteur avec son id
     * @param id l'id du capteur recherché
     * @return
     */
    @Override
    public Captor findById(String id) {
        return entityManager.find(Captor.class, id);
    }

    /**
     * Cherche tous les capteurs
     * @return tous les capteurs
     */
    @Override
    public List<Captor> findAll() {
        return entityManager.createQuery(SELECT_WITH_JOIN, Captor.class).getResultList();
    }


    /**
     * Supprime un capteur dans la bdd
     * @param captor L'id du capteur à supprimer
     */
    @Override
    public void delete(Captor captor) {
        entityManager.remove(captor);
    }
}