package com.timeyang.amanda.core.fileserver;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author chaokunyang
 */
@ConfigurationProperties("fileserver")
public class StorageProperties {

    /**
     * Folder location for storing files
     */
    private String location = "upload-dir";

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
