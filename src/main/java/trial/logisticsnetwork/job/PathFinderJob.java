package trial.logisticsnetwork.job;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import trial.logisticsnetwork.entity.Edge;
import trial.logisticsnetwork.entity.Network;
import trial.logisticsnetwork.entity.Path;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Path Finder Job.
 * <p>
 * Uses a mix of Breadth-First-Search and Dijkstra to find all possible paths.
 * Neighbors are recursively queried from the Edge table as a list while traversing the Graph using a source and a current node.
 * <p>
 * Only source nodes (vertex) are considered during path finding.
 */
@Component
public class PathFinderJob {

    private static final Logger logger = LoggerFactory.getLogger(PathFinderJob.class);
    @PersistenceContext
    private EntityManager em;

    @Transactional
    @Scheduled(fixedDelay = 10000)
    public void findPaths() {

        long now = System.currentTimeMillis();

        List<Network> networks = em.createQuery("select n from Network n where n.processed = false", Network.class).getResultList();

        if (!networks.isEmpty()) {
            Network network = networks.iterator().next();

            List<String> sources = getSources(network.getEdges());

            Map<String, Path> paths = new HashMap<>();

            for (String source : sources) {
                find(new Path(source), paths, network);
            }

            logger.debug(paths.toString());

            int count = em.createQuery("delete from Path p where p.network = :network").setParameter("network", network).executeUpdate();

            logger.info(count + " paths were removed from " + network.toString());

            int i = 0;
            for (Map.Entry<String, Path> entry : paths.entrySet()) {
                i++;
                em.persist(entry.getValue());
                if (i % 100 == 0) // avoid filling up the entity manager
                    em.flush();
            }

            logger.info(paths.size() + " new paths were found for " + network.toString());

            network.setProcessed(true);

            logger.info("Network " + network.getName() + " took " + (System.currentTimeMillis() - now) + "ms to be processed");
        }
    }

    private void find(Path source, Map<String, Path> paths, Network network) {
        List<Edge> edgesFromSource = findTargets(source.getCurrent(), network);

        for (Edge edge : edgesFromSource) {
            Path path = new Path(
                    source.getSource(),
                    edge.getTarget(),
                    source.getDistance() + edge.getDistance(),
                    source.getDescription() + "-" + edge.getTarget(),
                    edge.getTarget(),
                    network);

            String key = path.getSource() + path.getTarget();

            if (!paths.containsKey(key)) {
                paths.put(key, path);
            } else {
                // check and keep only the shortest path.
                Path current = paths.get(key);
                if (current.getDistance() > path.getDistance()){
                    Path previous = paths.remove(key);
                    paths.put(key, path);
                    logger.debug(String.format("Exchanging paths: previous=%s, new=%s", previous, path));
                }
            }

            find(path, paths, network);
        }
    }

    private List<Edge> findTargets(String source, Network network) {
        return em.createQuery("select e from Edge e where e.network = :network and e.source = :source", Edge.class).
                setParameter("network", network).
                setParameter("source", source).
                getResultList();
    }

    private List<String> getSources(List<Edge> edges) {
        List<String> sources = new ArrayList<>();
        for (Edge edge : edges) {
            if (!sources.contains(edge.getSource()))
                sources.add(edge.getSource());
        }
        return sources;
    }
}
