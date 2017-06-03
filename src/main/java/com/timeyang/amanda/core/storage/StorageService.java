package com.timeyang.amanda.core.storage;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;
import java.util.stream.Stream;

/**
 * 存储服务
 *
 * @author chaokunyang
 */
public interface StorageService {

    void init();

    // @PreAuthorize("hasAuthority('UPLOAD_FILE')")
    void store(MultipartFile file);

    Stream<Path> loadAll();

    Path load(String filename);

    Resource loadAsResource(String filename);

    // @PreAuthorize("hasAuthority('DELETE_ANY_FILE')")
    void deleteAll();

}