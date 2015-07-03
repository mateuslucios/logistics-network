package trial.logisticsnetwork.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import trial.logisticsnetwork.entity.Network;
import trial.logisticsnetwork.repository.NetworkRepository;
import trial.logisticsnetwork.response.NetworkResponse;

import java.io.File;
import java.io.FileInputStream;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class NetworkServiceTest {

    @InjectMocks
    private NetworkService networkService;

    @Mock
    private NetworkRepository repository;

    @Test
    public void testDuplicatedNetwork() throws Exception {
        when(repository.findByName(anyString())).thenReturn(new Network());
        NetworkResponse response =
                networkService.createNetwork("test-network", new FileInputStream(new File("src/test/resources/test-network.txt")));
        assertNotNull(response);
        assertEquals("Duplicated network: " + "test-network", response.getMessage());
    }

    @Test
    public void testNewNetwork() throws Exception {
        when(repository.findByName(anyString())).thenReturn(null);
        NetworkResponse response =
                networkService.createNetwork("test-network", new FileInputStream(new File("src/test/resources/test-network.txt")));
        verify(repository).insert(any(Network.class));
        assertNotNull(response);
        assertEquals("Network successfully created", response.getMessage());
    }

    @Test
    public void testInvalidData() throws Exception {
        when(repository.findByName(anyString())).thenReturn(null);
        NetworkResponse response =
                networkService.createNetwork("test-network", new FileInputStream(new File("src/test/resources/test-network-error.txt")));
        verify(repository, never()).insert(any(Network.class));
        assertNotNull(response);
        assertEquals("Invalid data on row 3", response.getMessage());
    }
}