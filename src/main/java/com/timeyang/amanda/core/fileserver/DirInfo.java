package com.timeyang.amanda.core.fileserver;

import lombok.Data;

import java.time.Instant;
import java.util.List;

/**
 * 目录信息
 *
 * @author chaokunyang
 */
@Data
public class DirInfo {
    private String name;
    private String url;
    private Instant dateModified;
    private List<DirInfo> dirs;
    private List<FileInfo> files;

    public DirInfo(String name, String url, Instant dateModified) {
        this.name = name;
        this.url = url;
        this.dateModified = dateModified;
    }

    public DirInfo(String name, String url, Instant dateModified, List<DirInfo> dirs, List<FileInfo> files) {
        this.name = name;
        this.url = url;
        this.dateModified = dateModified;
        this.dirs = dirs;
        this.files = files;
    }
}
