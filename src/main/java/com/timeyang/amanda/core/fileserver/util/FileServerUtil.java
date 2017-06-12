package com.timeyang.amanda.core.fileserver.util;

import com.timeyang.amanda.api.FileServerEndpoint;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * 存储工具类
 *
 * @author chaokunyang
 */
public class FileServerUtil {

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");

    public static String getDirPath(String dirPath) {
        if(dirPath == null || !dirPath.startsWith("home")) {
            return "home" + "/" + LocalDateTime.now().format(formatter);
        }
        return dirPath;
    }

    /**
     * Url获取函数，作为函数传入StorageService。
     * @param path
     * @return FileInfo
     */
    public static String getPathUrl(Path path) {
        String resourcePath = path.getName(0).relativize(path).toString().replace("\\", "/");
        String methodName = path.toFile().isDirectory() ? "viewDir" : "serveFile";
        Method method;
        try {
            method = FileServerEndpoint.class.getDeclaredMethod(methodName, HttpServletRequest.class);
        } catch (NoSuchMethodException e) {
            throw new IllegalArgumentException("No " + methodName + " method in " + FileServerEndpoint.class.getSimpleName());
        }

        String methodPath;
        RequestMapping requestMapping = AnnotatedElementUtils.findMergedAnnotation(method, RequestMapping.class);

        if (requestMapping == null) {
            throw new IllegalArgumentException("No @RequestMapping on: " + method.toGenericString());
        }
        String[] paths = requestMapping.path();
        if (ObjectUtils.isEmpty(paths) || StringUtils.isEmpty(paths[0])) {
            methodPath = "/";
        }else {
            methodPath = paths[0].substring(0, paths[0].length() - 2);
        }

        String baseUrl = MvcUriComponentsBuilder
                .fromController(FileServerEndpoint.class)
                .build().toString();

        String pathUrl = baseUrl + methodPath + resourcePath;

        return pathUrl;
    }

}
