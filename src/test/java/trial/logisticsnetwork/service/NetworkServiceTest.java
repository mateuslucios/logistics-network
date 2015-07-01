//package trial.logisticsnetwork.service;
//
//import org.junit.Assert;
//import org.junit.Before;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.runners.MockitoJUnitRunner;
//import trial.logisticsnetwork.entity.Network;
//import trial.logisticsnetwork.response.NetworkResponse;
//
//import javax.persistence.EntityManager;
//import javax.persistence.NoResultException;
//import javax.persistence.TypedQuery;
//import java.io.File;
//import java.io.FileInputStream;
//
//import static org.junit.Assert.*;
//import static org.mockito.Matchers.anyObject;
//import static org.mockito.Matchers.anyString;
//import static org.mockito.Matchers.eq;
//import static org.mockito.Mockito.*;
//
///**
// * Created by mateus on 7/1/15.
// */
//@RunWith(MockitoJUnitRunner.class)
//public class NetworkServiceTest {
//
//    @InjectMocks
//    private NetworkService networkService;
//
//    @Mock
//    private EntityManager em;
//
//    @Mock
//    private TypedQuery<Network> query;
//
//    @Test
//    public void testDuplicatedNetwork() throws Exception {
//        when(em.createQuery("select n from Network n where n.name = :name", Network.class)).thenReturn(query);
//        when(query.setParameter(eq("name"), anyString())).thenReturn(query);
//        when(query.getSingleResult()).thenReturn(new Network());
//        NetworkResponse response =
//                networkService.createNetwork("test-network", new FileInputStream(new File("src/test/resources/test-network.txt")));
//        assertNotNull(response);
//        assertEquals("Duplicated network: " + "test-network", response.getMessage());
//    }
//
//    @Test
//    public void testNewNetwork() throws Exception {
//        when(em.createQuery("select n from Network n where n.name = :name", Network.class)).thenReturn(query);
//        when(query.setParameter(eq("name"), anyString())).thenReturn(query);
//        when(query.getSingleResult()).thenThrow(new NoResultException());
//        NetworkResponse response =
//                networkService.createNetwork("test-network", new FileInputStream(new File("src/test/resources/test-network.txt")));
//        verify(em, only()).persist(anyObject());
//        assertNotNull(response);
//        assertEquals("Network successfully created", response.getMessage());
//    }
//
//    @Test
//    public void testInvalidData() throws Exception {
//        when(em.createQuery("select n from Network n where n.name = :name", Network.class)).thenReturn(query);
//        when(query.setParameter(eq("name"), anyString())).thenReturn(query);
//        when(query.getSingleResult()).thenThrow(new NoResultException());
//        NetworkResponse response =
//                networkService.createNetwork("test-network", new FileInputStream(new File("src/test/resources/test-network-error.txt")));
//        verify(em, only()).persist(anyObject());
//        assertNotNull(response);
//        assertEquals("Invalid data on row 3", response.getMessage());
//    }
//}