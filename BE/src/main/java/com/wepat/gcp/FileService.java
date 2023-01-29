package com.wepat.gcp;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


@Service
@RequiredArgsConstructor
public class FileService {
    private static final Logger LOGGER = LoggerFactory.getLogger(FileService.class);
    private final FileRepository fileRepository;
    private final DataBucketUtil dataBucketUtil;
    public List<InputFile> uploadFiles(MultipartFile[] files) {
        List<InputFile> inputFiles = new ArrayList<>();

        Arrays.asList(files).forEach(file -> {
            String originalFileName = file.getOriginalFilename();
            if(originalFileName == null) {
                throw new BadRequestException("Original file name is null");
            }
            Path path = new File(originalFileName).toPath();

            try {
                String contentType = Files.probeContentType(path);
                FileDto fileDto = dataBucketUtil.uploadFile(file, originalFileName, contentType);

                if (fileDto != null) {
                    inputFiles.add(new InputFile(fileDto.getFileName(), fileDto.getFileUrl()));
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        fileRepository.saveAll(inputFiles);
        return inputFiles;
    }

    public void downloadFile(String fileName) {
        dataBucketUtil.downloadFile(fileName);
    }

    public void deleteFile(String fileName) throws IOException {
        dataBucketUtil.deleteFile(fileName);
    }
}
