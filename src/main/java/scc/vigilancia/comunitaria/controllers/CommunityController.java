package scc.vigilancia.comunitaria.controllers;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import scc.vigilancia.comunitaria.dto.NewPostRequest;
import scc.vigilancia.comunitaria.services.CommunityService;
import scc.vigilancia.comunitaria.services.S3Service;

@RestController
@RequestMapping("/community")
@Api(tags = "Endpoints para salvar foto (temporário)")
@CrossOrigin(origins = {"*"})
public class CommunityController {

    private final S3Service s3Service;
    private final CommunityService communityService;

    public CommunityController(S3Service s3Service, CommunityService communityService) {
        this.s3Service = s3Service;
        this.communityService = communityService;
    }

    @PostMapping(consumes = {
            MediaType.MULTIPART_FORM_DATA_VALUE,
            MediaType.APPLICATION_JSON_VALUE
    })
    @ApiOperation(value = "Salvar foto", nickname = "salvarFoto", response = Object.class)
    public ResponseEntity<Object> salvarFoto(@RequestPart(name = "image") MultipartFile file)  {
        s3Service.savePost(file);
        return ResponseEntity.ok("ok");
    }
}
