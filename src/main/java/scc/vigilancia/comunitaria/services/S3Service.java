package scc.vigilancia.comunitaria.services;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.io.IOException;
import java.util.UUID;

@Service
@Slf4j
public class S3Service {

    private final S3Client amazonS3Client;

    public S3Service(S3Client amazonS3Client) {
        this.amazonS3Client = amazonS3Client;
    }

    @Value("${s3.bucket}")
    private String bucket;

    public void savePost(MultipartFile file) {
        //TODO: gather owner data
        createS3Request(file);
        //TODO: save full post
    }

    private void createS3Request(MultipartFile file) {
        String imageUUID = UUID.randomUUID().toString();

        PutObjectRequest objectRequest = PutObjectRequest.builder()
                .bucket(bucket)
                .key(file.getOriginalFilename())
                .build();
        try {
            amazonS3Client.putObject(objectRequest, RequestBody.fromBytes(file.getBytes()));
            log.info("[S3Service - OK]: Foto salva no S3 ");
        } catch (IOException e) {
            throw new IllegalArgumentException("Image corrupted");
        }
        //TODO: save uuid to database
    }

    private boolean isImageValid(MultipartFile file) {
        //todo: validate size and format
        return file == null || file.isEmpty();
    }
}
