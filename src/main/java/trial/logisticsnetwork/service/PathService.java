package trial.logisticsnetwork.service;

import org.springframework.stereotype.Component;
import trial.logisticsnetwork.entity.Network;
import trial.logisticsnetwork.request.PathRequest;
import trial.logisticsnetwork.response.PathResponse;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.ws.rs.BeanParam;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;

/**
 * Endpoints regarding paths.
 */
@Component
@Path("path")
public class PathService {

    @PersistenceContext
    private EntityManager em;

    @GET
    @Path("{network-name}/{source}/{target}")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public PathResponse retrieve(@BeanParam PathRequest pathRequest) {

        if (pathRequest.getAutonomy() < 0.0)
            return new PathResponse("Autonomy must be positive");

        if (pathRequest.getLiterPrice() < 0.0)
            return new PathResponse("Autonomy must be positive");

        try {
            Network network = em.createQuery("select n from Network n where n.name = :name", Network.class).
                    setParameter("name", pathRequest.getNetworkName()).
                    getSingleResult();

            if (!network.isProcessed())
                return new PathResponse("This network has not been processed, please try again later.");

            List<trial.logisticsnetwork.entity.Path> paths =
                    em.createQuery("select p from Path p where p.network = :network and p.source = :source and p.target = :target order by p.distance", trial.logisticsnetwork.entity.Path.class).
                            setParameter("network", network).
                            setParameter("source", pathRequest.getSource()).
                            setParameter("target", pathRequest.getTarget()).
                            getResultList();

            trial.logisticsnetwork.entity.Path shortestPath = paths.iterator().next();

            double requiredLiters = pathRequest.getAutonomy() > 0 ? shortestPath.getDistance() / pathRequest.getAutonomy() : 0;

            double cost = requiredLiters * pathRequest.getLiterPrice();

            return new PathResponse(
                    shortestPath.getSource(),
                    shortestPath.getTarget(),
                    shortestPath.getDescription(),
                    shortestPath.getDistance(),
                    requiredLiters,
                    cost,
                    "Path found"
            );
        } catch (NoResultException e) {
            return new PathResponse("Network " + pathRequest.getNetworkName() + " not found");
        }
    }
}
