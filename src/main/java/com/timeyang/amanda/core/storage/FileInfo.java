package com.timeyang.amanda.core.storage;

import lombok.Data;

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
    private String url;

    public FileInfo(String name, String type, long size, String url) {
        this.name = name;
        this.type = type;
        this.size = size;
        this.url = url;
    }
}
