package com.whitehawk.elastictransportclient;

import org.apache.log4j.Logger;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.xpack.client.PreBuiltXPackTransportClient;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.Inet4Address;
import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Properties;

/**
 * This is an abstract class which creates an instance of transport client
 * from elastic-search.properties file during its instantiation.
 */
public abstract class TransportClient {

    private static Logger logger = Logger.getLogger(TransportClientImpl.class);
    protected org.elasticsearch.client.transport.TransportClient client;
    protected Properties props;
    private Settings settings;
    private boolean ip4Enabled;
    private boolean ip6Enabled;


    public TransportClient() throws IOException {

        init();

    }

    private void  init() throws IOException {

        ip4Enabled = true;
        ip6Enabled = false;
        getSettingsObj();
        getTransportClient();

    }

    private Settings getSettingsObj() throws IOException{

        InputStream inputStream = new FileInputStream("elastic-search.properties");
        props = new Properties();
        props.load(inputStream);
        String clusterName = props.getProperty("clusterName");
        String user = props.getProperty("user");
        String password = props.getProperty("password");

        settings = Settings.builder()
                .put("client.transport.nodes_sampler_interval", "30s")
                .put("client.transport.sniff", false)
                .put("transport.tcp.compress", true)
                .put("cluster.name", clusterName)
                .put("xpack.security.transport.ssl.enabled", true)
                .put("request.headers.X-Found-Cluster", clusterName)
                .put("xpack.security.user", user+":"+password)
                .build();

        return settings;
    }

    private org.elasticsearch.client.transport.TransportClient getTransportClient(){

        client = new PreBuiltXPackTransportClient(settings);
        try {
            for (InetAddress address : InetAddress.getAllByName(props.getProperty("hostName"))) {
                if ((ip6Enabled && address instanceof Inet6Address)
                        || (ip4Enabled && address instanceof Inet4Address)) {
                    client.addTransportAddress(new InetSocketTransportAddress(address, Integer.parseInt(props.getProperty("port"))));
                }
            }
        } catch (UnknownHostException e) {
            logger.info(e.getMessage());
        }

        return client;
    }

    public org.elasticsearch.client.transport.TransportClient getClient(){

        return client;
    }

    public Settings getSettings(){
        return settings;
    }

    public void closeTransportClient(){

        client.close();

    }

}
