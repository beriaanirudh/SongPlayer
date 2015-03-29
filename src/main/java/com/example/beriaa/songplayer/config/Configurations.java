package com.example.beriaa.songplayer.config;

/**
 * Created by beriaa on 3/15/15.
 */
public class Configurations {
    public String getIPAddress() {
        return IPAddress;
    }

    public void setIPAddress(String IPAddress) {
        this.IPAddress = IPAddress;
    }

    private static String IPAddress;

    public static String getFileName() {
        return fileName;
    }

    private final static String fileName = "Config";

    public static String getDirectoryName() {
        return directoryName;
    }

    private final static String directoryName = "/SongPlayer";

    private final static String IPAddressKey = "com.example.beriaa.songplayer.IPAddress";

    public String getIPAddressKey() {
        return IPAddressKey;
    }
    private final static String appKey = "com.example.beriaa.songplayer_preferences";

    public String getAppKey() {
        return appKey;
    }
    private final static String portNo = ":8080";

    public String getPortNo() {
        return portNo;
    }
}
