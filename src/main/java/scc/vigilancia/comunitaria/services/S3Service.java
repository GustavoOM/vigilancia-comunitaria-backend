package scc.vigilancia.comunitaria.services;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.io.IOException;
import java.util.Objects;
import java.util.UUID;

@Service
@Slf4j
public class S3Service {

    private final S3Client amazonS3Client;

    @Value("${s3.bucket}")
    private String bucket;

    public S3Service(S3Client amazonS3Client) {
        this.amazonS3Client = amazonS3Client;
    }

    public String createS3Request(MultipartFile fileName) {
        String imageUUID = UUID.randomUUID().toString();
        String imageKey = imageUUID.concat(".").concat(extractFileExtension(fileName));

        PutObjectRequest objectRequest = PutObjectRequest.builder()
                .bucket(bucket)
                .key(imageKey)
                .build();
        try {
            amazonS3Client.putObject(objectRequest, RequestBody.fromBytes(fileName.getBytes()));
            log.info("[S3Service - OK]: Foto salva no S3 ");
        } catch (IOException e) {
            throw new IllegalArgumentException("Image corrupted");
        }
        return "https://".concat(bucket).concat(".s3.sa-east-1.amazonaws.com/").concat(imageKey);
    }

    private String extractFileExtension(MultipartFile file) {
        return Objects.requireNonNull(file.getOriginalFilename()).split("\\.")[1];
    }

}
