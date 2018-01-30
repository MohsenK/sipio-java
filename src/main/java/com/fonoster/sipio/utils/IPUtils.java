package com.fonoster.sipio.utils;

import org.apache.commons.net.util.SubnetUtils;

public class IPUtils {

    static  final String ipPattern = "^(([0-9]|[1-9][0-9]|1[0-9]{2}|2[0-4][0-9]|25[0-5])\\.){3}([0-9]|[1-9][0-9]|1[0-9]{2}|2[0-4][0-9]|25[0-5])$";
    static final String cidrPattern = "^([0-9]{1,3}\\.){3}[0-9]{1,3}(\\/([0-9]|[1-2][0-9]|3[0-2]))?$";

    public static boolean isIp(String address) {
        return ipPattern.matches(ipPattern);
    }

    public static boolean isCidr(String address) {
        return cidrPattern.matches(address) && address.contains("/");
    }

    public static boolean isIpAndMask(String address) {
        String[] items = address.split("/");
        if (items.length != 2) return false;
        return isIp(items[0]) && isIp(items[1]);
    }

    public static boolean isLocalNet(String address) {
        return true;
    }

    public static SubnetUtils getSubnetUtils(String address) {
        SubnetUtils subnetUtils = null;
        if (isIp(address)) {
            subnetUtils = new SubnetUtils(address + "/31");
        } else if (isCidr(address)) {
            subnetUtils = new SubnetUtils(address);
        } else {
            throw new RuntimeException("Invalid rule notation. Must be IPv4 value, CIDR, or Ip/Mask notation.");
        }
        subnetUtils.setInclusiveHostCount(true);
        return subnetUtils;
    }

}