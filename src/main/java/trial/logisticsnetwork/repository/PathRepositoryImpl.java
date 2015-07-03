package trial.logisticsnetwork.repository;

import trial.logisticsnetwork.entity.Network;
import trial.logisticsnetwork.entity.Path;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

public class PathRepositoryImpl implements PathRepository {

    @PersistenceContext
    private EntityManager em;

    @Override
    public List<Path> find(Network network, String source, String target) {
        return em.createQuery("select p from Path p where p.network = :network " +
                "and p.source = :source and p.target = :target order by p.distance", Path.class).
                setParameter("network", network).
                setParameter("source", source).
                setParameter("target", target).
                getResultList();
    }
}
