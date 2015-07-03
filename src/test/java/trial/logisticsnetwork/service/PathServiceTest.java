package trial.logisticsnetwork.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import trial.logisticsnetwork.entity.Edge;
import trial.logisticsnetwork.entity.Network;
import trial.logisticsnetwork.entity.Path;
import trial.logisticsnetwork.repository.NetworkRepository;
import trial.logisticsnetwork.repository.PathRepository;
import trial.logisticsnetwork.request.PathRequest;
import trial.logisticsnetwork.response.PathResponse;

import java.util.Arrays;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Matchers.*;
import static org.mockito.Mockito.when;

/**
 * Created by mateus on 7/2/15.
 */
@RunWith(MockitoJUnitRunner.class)
public class PathServiceTest {

    @InjectMocks
    private PathService pathService;

    @Mock
    private NetworkRepository networkRepository;

    @Mock
    private PathRepository pathRepository;


    /*
    *
    * if (pathRequest.getAutonomy() < 0.0)
                return new PathResponse("Autonomy must be positive");

            if (pathRequest.getLiterPrice() < 0.0)
                return new PathResponse("Liter Price must be positive");

            Network network = networkRepository.findByName(pathRequest.getNetworkName());

            if (network == null)
                return new PathResponse("Network " + pathRequest.getNetworkName() + " not found");

            if (!network.isProcessed())
                return new PathResponse("This network has not been processed, please try again later.");
    *
    */


    @Test
    public void testInvalidAutonomy() throws Exception {
        PathRequest request = new PathRequest();
        request.setAutonomy(-1.0);
        PathResponse response = pathService.retrieve(request);
        assertNotNull(response);
        assertEquals("Autonomy must be positive", response.getMessage());
    }

    @Test
    public void testInvalidLiterPrice() throws Exception {
        PathRequest request = new PathRequest();
        request.setAutonomy(10.0);
        request.setLiterPrice(-1.0);
        PathResponse response = pathService.retrieve(request);
        assertNotNull(response);
        assertEquals("Liter Price must be positive", response.getMessage());
    }

    @Test
    public void testNetworkNotFound() throws Exception {
        PathRequest request = new PathRequest();
        request.setLiterPrice(2.5);
        request.setAutonomy(10.0);
        request.setNetworkName("test");
        PathResponse response = pathService.retrieve(request);
        assertNotNull(response);
        assertEquals("Network test not found", response.getMessage());
    }

    @Test
    public void testNetworkNotProcessed() throws Exception {
        PathRequest request = new PathRequest();
        request.setLiterPrice(2.5);
        request.setAutonomy(10.0);
        request.setNetworkName("empty network");
        when(networkRepository.findByName(anyString())).thenReturn(new Network());
        PathResponse response = pathService.retrieve(request);
        assertNotNull(response);
        assertEquals("Network empty network has not been processed, please try again later.", response.getMessage());
    }

    @Test
    public void testRetrievingData() throws Exception {
        PathRequest request = new PathRequest();
        request.setLiterPrice(2.5);
        request.setAutonomy(10.0);
        request.setNetworkName("empty network");
        request.setSource("A");
        request.setTarget("D");

        Edge edge = new Edge("A", "D", 25.0);
        Network network = new Network("network", Arrays.asList(edge));
        network.setProcessed(true);
        when(networkRepository.findByName(anyString())).thenReturn(network);
        Path path = new Path("A", "D", 25.0, "A-D", "", network);
        when(pathRepository.find(any(Network.class), eq("A"), eq("D"))).thenReturn(Arrays.asList(path));
        PathResponse response = pathService.retrieve(request);
        assertNotNull(response);
        assertEquals("Path found", response.getMessage());
        assertEquals(25.0, response.getDistance(), 0.1);
        assertEquals("A", response.getSource());
        assertEquals("D", response.getTarget());
        assertEquals(6.25, response.getCost(), 0.1);
        assertEquals(2.5, response.getRequiredLiters(), 0.1);
        assertEquals("A-D", response.getRoute());

    }


}