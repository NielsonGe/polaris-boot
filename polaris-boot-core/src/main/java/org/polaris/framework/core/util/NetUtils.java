package org.polaris.framework.core.util;

import org.apache.commons.lang3.StringUtils;
import org.polaris.framework.core.type.InetAddressType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.Inet4Address;
import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Enumeration;

/**
 * Net Utility
 *
 * @author Nielson Ge
 * @since 0.0.1.0
 */
public abstract class NetUtils {
    private static final Logger logger = LoggerFactory.getLogger(NetUtils.class);

    /**
     * Get network address
     * @param addressType Network address type
     * @param interfaceName Network interface name
     * @return Network address
     */
    public static InetAddress getLocalInetAddress(InetAddressType addressType, String interfaceName) {
        try {
            Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();

            while (interfaces.hasMoreElements()) {
                NetworkInterface networkInterface = interfaces.nextElement();

                if (StringUtils.isNotBlank(interfaceName)) {
                    if (!interfaceName.equals(networkInterface.getName())) {
                        continue;
                    }
                }

                Enumeration<InetAddress> addresses = networkInterface.getInetAddresses();

                while (addresses.hasMoreElements()) {
                    InetAddress address = addresses.nextElement();

                    if ((addressType == InetAddressType.IPv4 && address instanceof Inet4Address)
                            || (addressType == InetAddressType.IPv6 && address instanceof Inet6Address)) {
                        if (address.isLinkLocalAddress() || address.isLoopbackAddress() || address.isAnyLocalAddress()) {
                            continue;
                        }
                        return address;
                    }
                }
            }

            return null;
        } catch (Exception ex) {
            logger.warn(ex.getLocalizedMessage());
            return null;
        }
    }

    /**
     * Get local network address
     * @param addressType Network address type
     * @return Local network address
     */
    public static InetAddress getLocalInetAddress(InetAddressType addressType) {
        return getLocalInetAddress(addressType, null);
    }
}
