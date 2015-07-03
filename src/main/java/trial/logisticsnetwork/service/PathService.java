package trial.logisticsnetwork.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import trial.logisticsnetwork.entity.Network;
import trial.logisticsnetwork.repository.NetworkRepository;
import trial.logisticsnetwork.repository.PathRepository;
import trial.logisticsnetwork.request.PathRequest;
import trial.logisticsnetwork.response.PathResponse;

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

    private static final Logger logger = LoggerFactory.getLogger(PathService.class);

    @Autowired
    private PathRepository repository;

    @Autowired
    private NetworkRepository networkRepository;

    @GET
    @Path("{network-name}/{source}/{target}")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public PathResponse retrieve(@BeanParam PathRequest pathRequest) {
        try {
            if (pathRequest.getAutonomy() < 0.0)
                return new PathResponse("Autonomy must be positive");

            if (pathRequest.getLiterPrice() < 0.0)
                return new PathResponse("Liter Price must be positive");

            Network network = networkRepository.findByName(pathRequest.getNetworkName());

            if (network == null)
                return new PathResponse("Network " + pathRequest.getNetworkName() + " not found");

            if (!network.isProcessed())
                return new PathResponse("Network " + network.getName() + " has not been processed, please try again later.");

            List<trial.logisticsnetwork.entity.Path> paths =
                    repository.find(network, pathRequest.getSource(), pathRequest.getTarget());

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
        } catch (Exception e) {
            logger.error("Unable to process request", e);
            return new PathResponse("Unable to process request");
        }
    }
}
