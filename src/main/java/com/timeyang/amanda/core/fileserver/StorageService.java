package com.timeyang.amanda.core.fileserver;

import org.springframework.core.io.Resource;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;
import java.util.function.Function;

/**
 * 存储服务
 *
 * @author chaokunyang
 */
public interface StorageService {

    void init();

    @PreAuthorize("(#dirPath != null and #dirPath.startsWith('home/private')) ? " +
            "hasAuthority('UPLOAD_PRIVATE_FILE') : hasAuthority('UPLOAD_FILE') ")
    FileInfo store(MultipartFile file, String dirPath);

    DirInfo loadDir(String dirPath, Function<Path, String> getPathUrlFunction);

    Path load(String filepath);

    @PreAuthorize("(#filepath != null and #filepath.startsWith('home/private')) ? " +
            "hasAuthority('VIEW_PRIVATE_FILE') : true ")
    Resource loadAsResource(String filepath);

    @PreAuthorize("hasAuthority('DELETE_ANY_FILE')")
    void deleteAll();

    @PreAuthorize("hasAuthority('DELETE_ANY_FILE')")
    void delete(String path);

    @PreAuthorize("hasAuthority('DELETE_ANY_FILE')")
    void delete(String[] paths);

}