package com.timeyang.amanda.core.fileserver;

import com.timeyang.amanda.core.fileserver.util.FileServerUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Instant;
import java.util.ArrayList;
import java.util.function.Function;

/**
 * @author chaokunyang
 */
@Service
public class FileSystemStorageService implements StorageService {

    private static final Logger LOGGER = LogManager.getLogger();

    private final Path rootLocation;

    private Function<Path, String> getPathUrlFunction;

    @Autowired
    public FileSystemStorageService(StorageProperties properties) {
        this.rootLocation = Paths.get(properties.getLocation());
    }

    @Override
    public void init() {
        rootLocation.resolve("home/private").toFile().mkdirs();
    }

    @Override
    public FileInfo store(MultipartFile file, String dirPath) {
        try {
            if (file.isEmpty()) {
                throw new StorageException("Failed to store empty file " + file.getOriginalFilename());
            }

            dirPath = FileServerUtil.getDirPath(dirPath);
            Path targetPath = rootLocation.resolve(dirPath + "/" + file.getOriginalFilename());

            if(!Files.exists(targetPath.getParent()))
                targetPath.getParent().toFile().mkdirs();

            Files.copy(file.getInputStream(), targetPath);

            return getFileInfo(targetPath.toFile());
        } catch (IOException e) {
            throw new StorageException("Failed to store file " + file.getOriginalFilename(), e);
        }
    }

    @Override
    public DirInfo loadDir(String dirPath, Function<Path, String> getPathUrlFunction) {

        this.getPathUrlFunction = getPathUrlFunction;
        Path currentDirPath = rootLocation.resolve(dirPath);
        File currentDir = currentDirPath.toFile();
        String url = this.getPathUrlFunction.apply(currentDirPath);

        DirInfo dirInfo = new DirInfo(currentDir.getName(), url,
                Instant.ofEpochMilli(currentDir.lastModified()),
                new ArrayList<>(), new ArrayList<>());

        try {
            Files.walk(currentDirPath, 1)
                    .filter(path -> !path.equals(currentDirPath))
                    .map(Path::toFile)
                    .forEach(file -> this.populate(dirInfo, file));
        } catch (IOException e) {
            throw new StorageException("Failed to read specified dir: " + dirPath, e);
        }

        return dirInfo;
    }

    @Override
    public Path load(String filepath) {
        return rootLocation.resolve(filepath);
    }

    @Override
    public Resource loadAsResource(String filepath) {
        try {
            Path file = load(filepath);
            Resource resource = new UrlResource(file.toUri());
            if(resource.exists() || resource.isReadable()) {
                return resource;
            }else {
                throw new StorageFileNotFoundException("Couldn't read file: " + filepath);
            }
        } catch (MalformedURLException e) {
            throw new StorageFileNotFoundException("Couldn't read file: " + filepath);
        }
    }

    @Override
    public void deleteAll() {
        FileSystemUtils.deleteRecursively(rootLocation.toFile());
    }

    @Override
    public void delete(String path) {
        FileSystemUtils.deleteRecursively(rootLocation.resolve(path).toFile());
    }

    @Override
    public void delete(String[] paths) {
        Assert.notEmpty(paths, "Paths must not be empty");
        for (String path : paths) {
            LOGGER.info("delete path: " + rootLocation.resolve(path).toAbsolutePath());
            FileSystemUtils.deleteRecursively(rootLocation.resolve(path).toFile());
        }
    }

    private void populate(DirInfo dirInfo, File file) {
        if(file.isDirectory()) {
            dirInfo.getDirs().add(getDirInfo(file));
        }else {
            dirInfo.getFiles().add(getFileInfo(file));
        }
    }

    private DirInfo getDirInfo(File dir) {
        String url = this.getPathUrlFunction.apply(dir.toPath());
        return new DirInfo(dir.getName(), url, Instant.ofEpochMilli(dir.lastModified()));
    }

    private FileInfo getFileInfo(File file) {
        String fileType = "";
        try {
            fileType =  Files.probeContentType(file.toPath());
        } catch (IOException e) {
            LOGGER.info("Can't get file type for file: " + file);
        }

        String url = this.getPathUrlFunction.apply(file.toPath());

        return new FileInfo(file.getName(), fileType, file.length(), Instant.ofEpochMilli(file.lastModified()), url);
    }
}
