package trial.logisticsnetwork.repository;

import org.springframework.stereotype.Repository;
import trial.logisticsnetwork.entity.Network;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

@Repository
public class NetworkRepositoryImpl implements NetworkRepository {

    @PersistenceContext
    private EntityManager em;

    @Override
    public Network findByName(String name) {
        try {
            return em.createQuery("select n from Network n where n.name = :name", Network.class).
                    setParameter("name", name).
                    getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    @Override
    public void insert(Network network) {
        em.persist(network);
    }
}
