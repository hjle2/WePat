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
    private Storage storage = null;
    private Bucket bucket = null;
    private Storage getStorage() throws IOException {
        if (this.storage != null) return storage;
        InputStream inputStream = new ClassPathResource(gcpConfigFile).getInputStream();
        StorageOptions storageOptions = StorageOptions.newBuilder().setProjectId(gcpProjectId)
                .setCredentials(GoogleCredentials.fromStream(inputStream)).build();
        storage = storageOptions.getService();
        return storage;
    }
    private Bucket getBucket() throws IOException {
        if (bucket != null) return bucket;
        bucket = storage.get(gcpBucketId, Storage.BucketGetOption.fields());
        return bucket;
    }
    public FileDto uploadFile(MultipartFile multipartFile, String fileName, String contentType) {

        try{
            LOGGER.info("Start file uploading process on GCS");
            byte[] fileData = FileUtils.readFileToByteArray(convertFile(multipartFile));

            Bucket bucket = getBucket();

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
    public ResponseEntity<?> downloadFile(String fileName) {
        try{
            LOGGER.info("Start file downloading process on GCS");

            Storage storage = getStorage();
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
    public void deleteFile(String fileName) throws IOException {
        // The ID of your GCP project
        // String projectId = "your-project-id";

        // The ID of your GCS bucket
        // String bucketName = "your-unique-bucket-name";

        // The ID of your GCS object
        // String objectName = "your-object-name";

        Storage storage = getStorage();
        Blob blob = storage.get(gcpBucketId, fileName);
        if (blob == null) {
            System.out.println("The file " + fileName + " wasn't found in " + gcpBucketId);
            return;
        }

        // Optional: set a generation-match precondition to avoid potential race
        // conditions and data corruptions. The request to upload returns a 412 error if
        // the object's generation number does not match your precondition.
        Storage.BlobSourceOption precondition =
                Storage.BlobSourceOption.generationMatch(blob.getGeneration());

        storage.delete(gcpBucketId, fileName, precondition);

        System.out.println("Object " + fileName + " was deleted from " + gcpBucketId);
    }
}