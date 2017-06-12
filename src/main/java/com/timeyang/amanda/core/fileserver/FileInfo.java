package com.timeyang.amanda.core.fileserver;

import lombok.Data;

import java.time.Instant;

/**
 * 文件元信息
 *
 * @author chaokunyang
 */
@Data
public class FileInfo {
    private String name;
    private String type;
    private long size;
    private Instant dateModified;
    private String url;

    public FileInfo(String name, String type, long size, Instant dateModified, String url) {
        this.name = name;
        this.type = type;
        this.size = size;
        this.dateModified = dateModified;
        this.url = url;
    }
}
