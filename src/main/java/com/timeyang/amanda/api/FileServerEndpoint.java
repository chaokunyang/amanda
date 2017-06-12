package com.timeyang.amanda.api;

import com.timeyang.amanda.core.fileserver.DirInfo;
import com.timeyang.amanda.core.fileserver.FileInfo;
import com.timeyang.amanda.core.fileserver.StorageFileNotFoundException;
import com.timeyang.amanda.core.fileserver.StorageService;
import com.timeyang.amanda.core.fileserver.util.FileServerUtil;
import com.timeyang.amanda.core.util.HttpRequestUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

/**
 * 文件上传API
 *
 * @author chaokunyang
 */
@RestController
@RequestMapping("api/fs")
public class FileServerEndpoint {

    private final Function<Path, String> getPathUrlFunction =
            FileServerUtil::getPathUrl;

    @Autowired
    private StorageService storageService;

    @GetMapping(value = "/dir/**")
    public DirInfo viewDir(HttpServletRequest request) {
        String dirPath = HttpRequestUtil.extractPathFromPattern(request);
        return storageService.loadDir(dirPath, getPathUrlFunction);
    }

    // @RequestMapping的value的斜线的斜线在被正则匹配之前已经被分割了，因此正则无法匹配斜线，要么手动解析，要么在客户端编码斜线。https://jira.spring.io/browse/SPR-12546。
    // 使用查询参数浏览器无法知道内容类型
    @GetMapping("/file/**")
    public ResponseEntity<Resource> serveFile(HttpServletRequest request) {
        String filepath = HttpRequestUtil.extractPathFromPattern(request);
        Resource file = storageService.loadAsResource(filepath);
        return ResponseEntity
                .ok()
                .body(file);
    }

    @GetMapping("/download/**")
    public ResponseEntity<Resource> download(HttpServletRequest request) {
        String filepath = HttpRequestUtil.extractPathFromPattern(request);;
        Resource file = storageService.loadAsResource(filepath);
        return ResponseEntity
                .ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename()+"\"")
                .body(file);
    }

    @PostMapping
    public List<FileInfo> handleFileUpload(@RequestParam("files") List<MultipartFile> files, @RequestParam(value = "filepath", required = false) String dirPath) {

        List<FileInfo> filesInfo = new ArrayList<>(files.size());
        files.forEach(file -> filesInfo.add(storageService.store(file, dirPath)));

        return filesInfo;
    }

    @DeleteMapping("/**")
    public void handleDelete(HttpServletRequest request) {
        String path = HttpRequestUtil.extractPathFromPattern(request);
        storageService.delete(path);
    }

    @PostMapping("/delete")
    public void handleDelete(@RequestBody String[] paths) {
        storageService.delete(paths);
    }

    @ExceptionHandler(StorageFileNotFoundException.class)
    public ResponseEntity handleStorageFileNotFound(StorageFileNotFoundException ex) {
        return ResponseEntity.notFound().build();
    }

}
