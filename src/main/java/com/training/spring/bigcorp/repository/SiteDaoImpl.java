package com.training.spring.bigcorp.repository;

import com.training.spring.bigcorp.model.Site;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.stream.Collectors;

@Repository
@Transactional
public class SiteDaoImpl implements SiteDao {

    @PersistenceContext
    private EntityManager entityManager;

    private final static String SELECT_WITH_JOIN = "select s from Site s";

    /**
     * Recherche tous les sites
     * @return tous les sites
     */
    @Override
    public List<Site> findAll() {
        return entityManager.createQuery(SELECT_WITH_JOIN, Site.class).getResultList().stream()
                .distinct()
                .collect(Collectors.toList());
    }

    /**
     * Ajoute ou modifie un site dans la bdd
     * @param site le site à ajouter
     */
    @Override
    public void persist(Site site) {
        entityManager.persist(site);
    }

    /**
     * Cherche un site avec un id
     * @param id l'id recherché
     * @return Le site recherché. null si le site n'est pas trouvé
     */
    @Override
    public Site findById(String id) {
        return entityManager.find(Site.class, id);
    }

    /**
     * Supprime un site
     * @param site L'id du site à supprimer
     */
    @Override
    public void delete(Site site) {
        entityManager.remove(site);
    }

}