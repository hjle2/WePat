package com.wepat.gcp;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/v1/files")
@RequiredArgsConstructor
public class FileController {
    private static final Logger LOGGER = LoggerFactory.getLogger(FileController.class);
    private final FileService fileService;

    @PostMapping(consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public List<InputFile> addFile(@RequestParam("files")MultipartFile[] files) {
    public List<InputFile> uploadFile(@RequestParam("files")MultipartFile[] files) {
        return fileService.uploadFiles(files);
    }

    @GetMapping
    public void downloadFile(String fileName) {
        fileService.downloadFile(fileName);
    }

    @DeleteMapping
    public void deleteFile(String fileName) {
        try {
            fileService.deleteFile(fileName);
        } catch (IOException e) {

        }
    }
}
