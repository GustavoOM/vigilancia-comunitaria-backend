package scc.vigilancia.comunitaria.controllers;

import io.swagger.annotations.ApiOperation;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import scc.vigilancia.comunitaria.services.S3Service;

import java.io.IOException;

@RestController
@RequestMapping("/com")
public class CommunityController {

    private final S3Service s3Service;

    public CommunityController(S3Service s3Service) {
        this.s3Service = s3Service;
    }

    @PostMapping(consumes = {
            MediaType.MULTIPART_FORM_DATA_VALUE,
            MediaType.APPLICATION_JSON_VALUE
    })
    @ApiOperation(value = "Salvar postagem", nickname = "salvarPostagem", response = Object.class)
    public ResponseEntity<Object> salvarPostagem(@RequestPart(name = "image") MultipartFile file)  {
        s3Service.savePost(file);
        return ResponseEntity.ok("ok");
    }

}
