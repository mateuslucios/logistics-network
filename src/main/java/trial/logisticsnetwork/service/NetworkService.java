package trial.logisticsnetwork.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import trial.logisticsnetwork.response.NetworkResponse;

import java.io.*;

/**
 * Endpoints regarding networks
 */
public class NetworkService {

    private final static Logger logger = LoggerFactory.getLogger(NetworkService.class);

    public NetworkResponse createNetwork(String name, InputStream stream) {

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(stream))){
            logger.debug("Network name: " + name);

            while (reader.ready())
                logger.debug(reader.readLine());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new NetworkResponse();
    }
}
