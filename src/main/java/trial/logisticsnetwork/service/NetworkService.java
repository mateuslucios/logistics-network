package trial.logisticsnetwork.service;

import org.apache.commons.io.IOUtils;
import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import trial.logisticsnetwork.entity.Edge;
import trial.logisticsnetwork.entity.Network;
import trial.logisticsnetwork.response.NetworkResponse;
import trial.logisticsnetwork.service.exception.InvalidDataException;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Endpoints regarding networks
 */
@Component
@Path("logistics-network")
public class NetworkService {

    private final static Logger logger = LoggerFactory.getLogger(NetworkService.class);

    @PersistenceContext
    private EntityManager em;

    @POST
    @Path("{name}")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @Transactional
    public NetworkResponse createNetwork(@PathParam("name") String name,
                                         @FormDataParam("file") InputStream stream,
                                         @FormDataParam("file") FormDataContentDisposition fileDetail) {
        int lineCounter = 0;

        try {
            em.createQuery("select n from Network n where n.name = :name", Network.class).
                    setParameter("name", name).
                    getSingleResult();

            return new NetworkResponse("Duplicated network: " + name);
        } catch (NoResultException nre) {
            try {
                logger.debug("Network name: " + name);

                List<String> lines = IOUtils.readLines(stream);

                List<Edge> edges = new ArrayList<>();

                for (String line : lines) {
                    lineCounter++;
                    Edge edge = parseLine(line);
                    logger.debug(line + " -> " + edge.toString());
                    edges.add(edge);
                }

                if (edges.isEmpty())
                    throw new InvalidDataException("Invalid data");

                Network network = new Network(name, edges);
                em.persist(network);
                logger.debug("network created " + network);
            } catch (IOException e) {
                logger.error(e.getMessage(), e);
                return new NetworkResponse("Unable to process request");
            } catch (InvalidDataException e) {
                logger.error(e.getMessage(), e);
                return new NetworkResponse("Invalid data on row " + lineCounter);
            }
        } finally {
            IOUtils.closeQuietly(stream);
        }

        return new NetworkResponse("Network successfully created");
    }

    private Edge parseLine(String line) {
        try {
            String[] parsed = line.split(" ");
            return new Edge(parsed[0].trim(), parsed[1].trim(), new Double(parsed[2].trim()));
        } catch (RuntimeException e) {
            throw new InvalidDataException(e);
        }
    }
}
