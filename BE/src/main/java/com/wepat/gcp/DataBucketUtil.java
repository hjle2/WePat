package com.wepat.gcp;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.*;
import net.bytebuddy.utility.RandomString;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.activation.FileTypeMap;
import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.ThreadLocalRandom;

@Component
public class DataBucketUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(DataBucketUtil.class);

    @Value("${gcp.config.file}")
    private String gcpConfigFile;

    @Value("${gcp.project.id}")
    private String gcpProjectId;

    @Value("${gcp.bucket.id}")
    private String gcpBucketId;

    @Value("${gcp.dir.name}")
    private String gcpDirectoryName;


    public FileDto uploadFile(MultipartFile multipartFile, String fileName, String contentType) {

        try{
            LOGGER.info("Start file uploading process on GCS");
            byte[] fileData = FileUtils.readFileToByteArray(convertFile(multipartFile));

            InputStream inputStream = new ClassPathResource(gcpConfigFile).getInputStream();
            LOGGER.info("Get InputStream");

            StorageOptions options = StorageOptions.newBuilder().setProjectId(gcpProjectId)
                    .setCredentials(GoogleCredentials.fromStream(inputStream)).build();
            LOGGER.info("Get StorageOptions");
            Storage storage = options.getService();
            LOGGER.info("Get Storage");
            Bucket bucket = storage.get(gcpBucketId, Storage.BucketGetOption.fields());
            LOGGER.info("Get Bucket");

            RandomString id = new RandomString(6, ThreadLocalRandom.current());
            LOGGER.info("Get RandomString");

            LOGGER.info("/" + fileName + "-" + id.nextString());
            Blob blob = bucket.create("/" + fileName + "-" + id.nextString() + checkFileExtension(fileName), fileData, contentType);
            LOGGER.info("Get Blob");

            if(blob != null){
                LOGGER.info("File successfully uploaded to GCS");
                return new FileDto(blob.getName(), blob.getMediaLink());
            }

        }catch (Exception e){
            LOGGER.error("An error occurred while uploading data. Exception: ", e);
            throw new GCPFileUploadException("An error occurred while storing data to GCS");
        }
        throw new GCPFileUploadException("An error occurred while storing data to GCS");
    }

    public Blob downloadFile(Storage storage, String fileName) {
        Blob blob = storage.get(gcpBucketId, fileName);
        return blob;
    }
    public ResponseEntity<?> downloadFile(String fileURL, String fileName) {
        LOGGER.info("{}, {}", fileURL, fileName);
        try{
            LOGGER.info("Start file downloading process on GCS");

            InputStream inputStream = new ClassPathResource(gcpConfigFile).getInputStream();
            LOGGER.info("Get InputStream");

            StorageOptions options = StorageOptions.newBuilder().setProjectId(gcpProjectId)
                    .setCredentials(GoogleCredentials.fromStream(inputStream)).build();
            LOGGER.info("Get StorageOptions");
            Storage storage = options.getService();
            LOGGER.info("Get Storage");

            BlobId blobId = BlobId.of(gcpBucketId, fileName);
            LOGGER.info("Get BlobId {}", blobId.getName());

            Blob blob = storage.get(blobId);
            LOGGER.info("Get Blob");

            if(blob != null){
                LOGGER.info("File successfully downloaded from GCS");
                return new ResponseEntity<FileDto>(new FileDto(blob.getName(), blob.getMediaLink()),HttpStatus.NOT_ACCEPTABLE);
            }

            return (ResponseEntity<?>) ResponseEntity.ok().contentType(MediaType.valueOf(FileTypeMap.getDefaultFileTypeMap().getContentType(fileName)));

        }catch (Exception e){
            LOGGER.error("An error occurred while downloading data. Exception: ", e);
            throw new GCPFileUploadException("An error occurred while downloading data from GCS");
        }
    }

    private File convertFile(MultipartFile file) {

        try{
            if(file.getOriginalFilename() == null){
                throw new BadRequestException("Original file name is null");
            }
            File convertedFile = new File(file.getOriginalFilename());
            FileOutputStream outputStream = new FileOutputStream(convertedFile);
            outputStream.write(file.getBytes());
            outputStream.close();
            LOGGER.debug("Converting multipart file : {}", convertedFile);
            return convertedFile;
        }catch (Exception e){
            throw new FileWriteException("An error has occurred while converting the file");
        }
    }

    private String checkFileExtension(String fileName) {
        if(fileName != null && fileName.contains(".")){
            String[] extensionList = {".png", ".jpg", ".jpeg", ".pdf", ".doc", ".mp3", "mp4"};

            for(String extension: extensionList) {
                if (fileName.endsWith(extension) || fileName.endsWith(extension.toUpperCase())) {
                    LOGGER.info("Accepted file type : {}", extension);
                    return extension;
                }
            }
        }
        LOGGER.error("Not a permitted file type");
        throw new InvalidFileTypeException("Not a permitted file type");
    }
    // fileName : 파일이 다운로드 될 위치 + 파일 이름인 전체 경로
    public void download(String fileURL, String fileName) {
        try (BufferedInputStream in = new BufferedInputStream(new URL(fileURL).openStream());
             FileOutputStream fileOutputStream = new FileOutputStream(fileName)) {

            byte[] dataBuffer = new byte[1024];
            int bytesRead;

            while ((bytesRead = in.read(dataBuffer, 0, 1024)) != -1) {
                fileOutputStream.write(dataBuffer, 0, bytesRead);
            }
        } catch (MalformedURLException e) {
            LOGGER.info("MalformedURLException occured!");
        } catch (IOException e) {
            LOGGER.info("IOException occured!");
        }
    }
}