package com.timeyang.amanda.api;

import com.timeyang.amanda.core.storage.StorageFileNotFoundException;
import com.timeyang.amanda.core.storage.StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 文件上传API
 *
 * @author chaokunyang
 */
@RestController
@RequestMapping("api/files")
public class FileUploadEndpoint {

    @Autowired
    private StorageService storageService;

    @RequestMapping(method = RequestMethod.GET)
    public @ResponseBody List<String> listUploadedFiles() {
        List<String> fileUrls =
                storageService
                .loadAll()
                .map(path -> MvcUriComponentsBuilder
                        .fromMethodName(FileUploadEndpoint.class,
                                "serveFile", path.getFileName().toString())
                        .build().toString()) // 即产生一个到serveFile方法的url，用于获取文件，注意serveFile方法没有加" Content-Disposition"header，因为对于图片，我想点击链接时直接展示而不是下载
                .collect(Collectors.toList());

        return fileUrls;
    }

    @GetMapping("/{filename:.+}")
    public ResponseEntity<Resource> serveFile(@PathVariable String filename) {
        Resource file = storageService.loadAsResource(filename);
        return ResponseEntity
                .ok()
                .body(file);
    }

    @GetMapping("/download/{filename:.+}")
    public ResponseEntity<Resource> download(@PathVariable String filename) {
        Resource file = storageService.loadAsResource(filename);
        return ResponseEntity
                .ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename()+"\"")
                .body(file);
    }

    @PostMapping
    public String handleFileUpload(@RequestParam("file") MultipartFile file) {
        storageService.store(file);

        String fileUrl = MvcUriComponentsBuilder
                .fromMethodName(FileUploadEndpoint.class,
                        "serveFile", file.getOriginalFilename())
                .build().toString();
        return fileUrl;

    }

    @ExceptionHandler(StorageFileNotFoundException.class)
    public ResponseEntity handleStorageFileNotFound(StorageFileNotFoundException exc) {
        return ResponseEntity.notFound().build();
    }

}
