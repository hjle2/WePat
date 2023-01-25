package com.wepat.gcp;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

@Repository
public class FileRepository {

    private final static Logger LOGGER = LoggerFactory.getLogger(FileRepository.class);
    public void saveAll(List<InputFile> inputFiles) {
        for (InputFile file : inputFiles) {
            LOGGER.info("file name: {}, fileURL: {}, fileId: {}",  file.getFileName(), file.getFileUrl(), file.getId());
        }
    }
}
