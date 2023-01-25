package com.wepat.gcp;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/v1/files")
@RequiredArgsConstructor
public class FileController {
    private static final Logger LOGGER = LoggerFactory.getLogger(FileController.class);
    private final FileService fileService;

    @PostMapping(consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public List<InputFile> addFile(@RequestParam("files")MultipartFile[] files) {
        LOGGER.info("Call addFile API");
        return fileService.uploadFiles(files);
    }

    @GetMapping
    public void getFile(String fileURL, String fileName) {
        LOGGER.info("Call getFile API");
        fileService.downloadFile(fileURL, fileName);
    }
}
