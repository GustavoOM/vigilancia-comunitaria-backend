package scc.vigilancia.comunitaria.controllers;



import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import scc.vigilancia.comunitaria.dto.New.NewPostRequest;
import scc.vigilancia.comunitaria.exceptions.EntityNotFoundException;
import scc.vigilancia.comunitaria.services.PostService;

@RestController
@RequestMapping("/post")
@Slf4j
@Api(tags = "Endpoints postagem")
@CrossOrigin(origins = {"*"})
public class PostController {

    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @PostMapping("/create-post")
    @ApiOperation(nickname = "Criar postagem", value = "Criar postagem", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> createPost(NewPostRequest newPostRequest,
                                             @RequestPart(name = "image", required = false) MultipartFile file) throws EntityNotFoundException {
        return postService.createNewPost(newPostRequest, file);
    }

    @GetMapping
    @ApiOperation(nickname = "Listar postagem", value = "Listar todas as postagens das comunidades que o usuário logado faz parte", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> findAllPosts() {
        return postService.findAll();
    }

    @GetMapping("/by-community")
    @ApiOperation(nickname = "Listar postagens de uma comunidade", value = "Listar todas as postagens de uma comunidade", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> findAllPostsByCommunity(@RequestParam(name = "Id da comunidade") Integer idCommunity) throws EntityNotFoundException{
        return postService.findAllByCommunity(idCommunity);
    }
}