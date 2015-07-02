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

            List<String> sources = getSources(network.getEdges());

            List<Path> paths = new ArrayList<>();

            for (String source : sources) {
                find(new Path(source), paths);
            }

            logger.debug(paths.toString());
        }
    }

    /*private void f(String source, List<Path> paths){
        List<NetworkEdge> edgesFromSource = findTargets(source);

        for (NetworkEdge edge : edgesFromSource) {
            Path path = new Path(source, edge.getTarget(), edge.getDistance(), source + "-" + edge.getTarget());

            if (!paths.contains(path))
                paths.add(path);

            f(edge.getTarget(), paths);
        }
    }*/

    private void find(Path source, List<Path> paths){
        List<NetworkEdge> edgesFromSource = findTargets(source.getCurrent());

        for (NetworkEdge edge : edgesFromSource) {
            Path path = new Path(
                    source.getSource(),
                    edge.getTarget(),
                    source.getDistance() + edge.getDistance(),
                    source.getPathDescription() + "-" + edge.getTarget(),
                    edge.getTarget());

            if (!paths.contains(path))
                paths.add(path);

            find(path, paths);
        }
    }

    /*private Path find(String source, String target) {
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
    }*/

    /*private Path find(String source, String target, Path path) {
        Path newPath = new Path();
        try {
            NetworkEdge result =
                    em.createQuery("select e from NetworkEdge e where e.source = :source and e.target = :target", NetworkEdge.class).
                            setParameter("source", source).
                            setParameter("target", target).
                            getSingleResult();

            //path.setSource(source);
            newPath.setTarget(target);
            newPath.setDistance(path.getDistance() + result.getDistance());
            newPath.setPathDescription(path.getPathDescription() + "-" + target);
        } catch (NoResultException e) {
            return null;
        }

        return newPath;
    }*/

    private List<NetworkEdge> findTargets(String source) {
                return em.createQuery("select e from NetworkEdge e where e.source = :source", NetworkEdge.class).
                        setParameter("source", source).
                        getResultList();
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
