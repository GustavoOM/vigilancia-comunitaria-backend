package scc.vigilancia.comunitaria.controllers;



import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import scc.vigilancia.comunitaria.dto.New.NewCommunityRequest;
import scc.vigilancia.comunitaria.dto.New.NewPostRequest;
import scc.vigilancia.comunitaria.exceptions.EntityNotFoundException;
import scc.vigilancia.comunitaria.services.CommunityService;
import scc.vigilancia.comunitaria.services.PostService;

@RestController
@RequestMapping("/community")
@Slf4j
@Api(tags = "Endpoints communities")
@CrossOrigin(origins = {"*"})
public class CommunityController {

    private final CommunityService communityService;

    public CommunityController(CommunityService communityService) {
        this.communityService = communityService;
    }

    @PostMapping("/create")
    @ApiOperation(nickname = "Criar comunidade", value = "Criar comunidade", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> create(NewCommunityRequest newCommunityRequest) {
        return communityService.create(newCommunityRequest);
    }

    @GetMapping
    @ApiOperation(nickname = "Listar comunidades", value = "Listar todas as comunidades", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> findAll() {
        return communityService.findAll();
    }

}