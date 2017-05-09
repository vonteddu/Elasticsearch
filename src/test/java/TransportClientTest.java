import com.whitehawk.elastictransportclient.TransportClientImpl;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.*;

/**
 * This test class provides test cases for the methods implemented by TransportClientImpl class
 */
public class TransportClientTest {

    TransportClientImpl transportClient;

    @Before
    public void setUp() throws IOException {

        transportClient = new TransportClientImpl();

    }

    @Test
    public void testElasticSearchSettings(){

        assertNotNull(transportClient.getSettings());

    }

    //test for if the client instance is null

    @Test
    public void testTransportClientInstantiation(){

        assertNotNull(transportClient.getClient());
        transportClient.closeTransportClient();
    }

    @Test
    public void testClusterHealthMethod(){

        assertNotNull(transportClient.getHealth());

    }

    @Test
    public void checkIfExistMethod(){

        assertTrue(transportClient.isFieldValueExists("nlp", "vendor", "name", "CyberOne"));
        assertFalse(transportClient.isFieldValueExists("nlp","vendor", "name", "cybersecurity"));

    }

    @Test
    public void testGetAllDatamethods(){

        assertNotNull(transportClient.getAllData("nlp","vendor","name"));
    }

}
