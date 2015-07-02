package trial.logisticsnetwork.jobs;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import trial.logisticsnetwork.entity.Network;
import trial.logisticsnetwork.entity.NetworkEdge;
import trial.logisticsnetwork.entity.Path;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by mateus on 7/1/15.
 */
@Component
public class PathFinderJob {

    private static final Logger logger = LoggerFactory.getLogger(PathFinderJob.class);
    @PersistenceContext
    private EntityManager em;

    @Transactional
    @Scheduled(fixedDelay = 10000)
    public void findPaths() {

        List<Network> networks = em.createQuery("select n from Network n where n.processed = false", Network.class).getResultList();

        if (!networks.isEmpty()) {
            Network network = networks.iterator().next();
            List<NetworkEdge> edges = network.getEdges();

            List<String> sources = getSources(edges);
            List<String> targets = getTargets(edges);
            List<Path> paths = new ArrayList<>();

            Path path = new Path();
            for (String source : sources) {
                for (String target : targets) {

                    path = find(source, target);

                    if (path != null) {
                        paths.add(path);
                    } else {
                        List<NetworkEdge> edgesFromSource = findTargets(source);
                        for (NetworkEdge edge : edgesFromSource) {
                            Path path1 = find(edge.getTarget(), target);

                        }
                    }
                }
            }
        }
    }

    private Path find(String source, String target){
        Path path = new Path();
        try {
            NetworkEdge result =
                    em.createQuery("select e from NetworkEdge e where e.source = :source and e.target = :target", NetworkEdge.class).
                            setParameter("source", source).
                            setParameter("target", target).
                            getSingleResult();

            path.setSource(source);
            path.setTarget(target);
            path.setDistance(path.getDistance() + result.getDistance());
            path.setPathDescription(source + "-" + target);
        } catch (NoResultException e) {
            return null;
        }

        return path;
    }

    private List<NetworkEdge> findTargets(String source) {
        List<NetworkEdge> edgesFromsource =
                    em.createQuery("select e from NetworkEdge e where e.source = :source", NetworkEdge.class).
                    setParameter("source", source).
                    getResultList();

            /*for (NetworkEdge edge : edgesFromsource) {
                //Path tempPath = find(edge.getTarget(), target, path);
            }*/
        return edgesFromsource;
    }

    private List<String> getTargets(List<NetworkEdge> edges) {
        List<String> targets = new ArrayList<>();
        for (NetworkEdge edge : edges) {
            if (!targets.contains(edge.getTarget()))
                targets.add(edge.getTarget());
        }
        return targets;
    }

    private List<String> getSources(List<NetworkEdge> edges) {
        List<String> sources = new ArrayList<>();
        for (NetworkEdge edge : edges) {
            if (!sources.contains(edge.getSource()))
                sources.add(edge.getSource());
        }
        return sources;
    }
}
