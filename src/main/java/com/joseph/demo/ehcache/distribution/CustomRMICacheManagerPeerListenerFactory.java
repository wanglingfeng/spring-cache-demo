package com.joseph.demo.ehcache.distribution;

import net.sf.ehcache.CacheException;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.distribution.CacheManagerPeerListener;
import net.sf.ehcache.distribution.CacheManagerPeerListenerFactory;
import net.sf.ehcache.distribution.RMICacheManagerPeerListener;
import net.sf.ehcache.util.PropertyUtil;

import java.net.*;
import java.util.Enumeration;
import java.util.Properties;

/**
 * Created by lfwang on 2017/9/15.
 */
public class CustomRMICacheManagerPeerListenerFactory extends CacheManagerPeerListenerFactory {

    /**
     * The default timeout for cache replication for a single replication action.
     * This may need to be increased for large data transfers.
     */
    public static final Integer DEFAULT_SOCKET_TIMEOUT_MILLIS = 120000;

    private static final String PORT = "port";
    private static final String REMOTE_OBJECT_PORT = "remoteObjectPort";
    private static final String SOCKET_TIMEOUT_MILLIS = "socketTimeoutMillis";

    /**
     * @param properties implementation specific properties. These are configured as comma
     *                   separated name value pairs in ehcache.xml
     */
    @Override
    public final CacheManagerPeerListener createCachePeerListener(CacheManager cacheManager, Properties properties)
            throws CacheException {
        String hostName = "localhost";
        try {
            // 使用java代码获取ip
            Enumeration<NetworkInterface> allNetInterfaces = NetworkInterface.getNetworkInterfaces();
            
            while (allNetInterfaces.hasMoreElements()) {
                NetworkInterface netInterface = allNetInterfaces.nextElement();
                Enumeration<InetAddress> addresses = netInterface.getInetAddresses();
                while (addresses.hasMoreElements()) {
                    InetAddress ip = addresses.nextElement();
                    if (ip == null || !(ip instanceof Inet4Address)) continue;
                    if ("127.0.0.1".equals(ip.getHostAddress())) continue;

                    hostName = ip.getHostAddress();
                }
            }
        } catch (SocketException e) {
            e.printStackTrace();
        }
        System.out.println("host ip: " + hostName);

        String portString = PropertyUtil.extractAndLogProperty(PORT, properties);
        Integer port;
        if (portString != null && portString.length() != 0) {
            port = Integer.valueOf(portString);
        } else {
            port = 0;
        }

        //0 means any port in UnicastRemoteObject, so it is ok if not specified to make it 0
        String remoteObjectPortString = PropertyUtil.extractAndLogProperty(REMOTE_OBJECT_PORT, properties);
        Integer remoteObjectPort;
        if (remoteObjectPortString != null && remoteObjectPortString.length() != 0) {
            remoteObjectPort = Integer.valueOf(remoteObjectPortString);
        } else {
            remoteObjectPort = 0;
        }

        String socketTimeoutMillisString = PropertyUtil.extractAndLogProperty(SOCKET_TIMEOUT_MILLIS, properties);
        Integer socketTimeoutMillis;
        if (socketTimeoutMillisString == null || socketTimeoutMillisString.length() == 0) {
            socketTimeoutMillis = DEFAULT_SOCKET_TIMEOUT_MILLIS;
        } else {
            socketTimeoutMillis = Integer.valueOf(socketTimeoutMillisString);
        }
        
        return doCreateCachePeerListener(hostName, port, remoteObjectPort, cacheManager, socketTimeoutMillis);
    }

    /**
     * A template method to actually create the factory
     *
     * @param hostName
     * @param port
     * @param remoteObjectPort
     * @param cacheManager
     * @param socketTimeoutMillis @return a crate CacheManagerPeerListener
     */
    protected CacheManagerPeerListener doCreateCachePeerListener(String hostName,
                                                                 Integer port,
                                                                 Integer remoteObjectPort,
                                                                 CacheManager cacheManager,
                                                                 Integer socketTimeoutMillis) {
        try {
            return new RMICacheManagerPeerListener(hostName, port, remoteObjectPort, cacheManager, socketTimeoutMillis);
        } catch (UnknownHostException e) {
            throw new CacheException("Unable to create CacheManagerPeerListener. Initial cause was " + e.getMessage(), e);
        }
    }
}
