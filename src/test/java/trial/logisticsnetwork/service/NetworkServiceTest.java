package trial.logisticsnetwork.service;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import trial.logisticsnetwork.response.NetworkResponse;

import java.io.File;
import java.io.FileInputStream;

import static org.junit.Assert.*;

/**
 * Created by mateus on 7/1/15.
 */
public class NetworkServiceTest {

    private NetworkService networkService;

    @Before
    public void setUp(){
        networkService = new NetworkService();
    }

    @Test
    public void testCreate() throws Exception {
        NetworkResponse response =
                networkService.createNetwork("test-network", new FileInputStream(new File("src/test/resources/test-network.txt")));

        assertNotNull(response);
    }
}