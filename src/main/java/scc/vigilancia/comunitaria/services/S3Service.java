package scc.vigilancia.comunitaria.services;


import org.springframework.stereotype.Service;
import software.amazon.awssdk.services.s3.S3Client;

@Service
public class S3Service {

    private final S3Client amazonS3Client;

    public S3Service(S3Client amazonS3Client) {
        this.amazonS3Client = amazonS3Client;
    }

}
