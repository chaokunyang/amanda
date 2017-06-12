package com.timeyang.amanda.core.fileserver;

import org.apache.commons.io.FileUtils;
import org.junit.Test;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * ${DESCRIPTION}
 *
 * @author chaokunyang
 */
public class FileSystemStorageServiceTest {

    @Test
    public void testInit() {
    }

    @Test
    public void store() throws Exception {
    }

    @Test
    public void testPath() throws Exception {
        Path path = Paths.get("upload-dir/2017-06-10/a.jpg");
        System.out.println(path);
        System.out.println(path.getName(0));
        System.out.println(path.getName(0).relativize(path));

    }

    @Test
    public void testDelete() throws IOException {
        String pathStr = "F:\\Development\\DevProjects\\amanda\\upload-dir\\2016\\06";
        Path path = Paths.get(pathStr);
        FileUtils.deleteDirectory(path.toFile());
    }

}