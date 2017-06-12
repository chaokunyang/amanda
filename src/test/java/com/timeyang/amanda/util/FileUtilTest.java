package com.timeyang.amanda.util;

import org.junit.Test;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * @author chaokunyang
 */
public class FileUtilTest {
    @Test
    public void testPath() throws IOException {
        Path path1 = Paths.get("a/");
        System.out.println(path1);

        Path path2 = Paths.get("b");
        System.out.println(path1.resolve(path2));
    }
}
