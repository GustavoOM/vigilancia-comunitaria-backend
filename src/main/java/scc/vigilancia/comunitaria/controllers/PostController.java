package scc.vigilancia.comunitaria.controllers;



import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import scc.vigilancia.comunitaria.dto.NewPostRequest;
import scc.vigilancia.comunitaria.dto.NewUserRequest;
import scc.vigilancia.comunitaria.services.AuthenticationService;
import scc.vigilancia.comunitaria.services.PostService;
import scc.vigilancia.comunitaria.services.UserService;

@RestController
@RequestMapping("/post")
@Slf4j
@Api(tags = "Endpoints para criar postagem")
@CrossOrigin(origins = {"*"})
public class PostController {

    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @PostMapping("/create-post")
    @ApiOperation(nickname = "Criar postagem", value = "Criar postagem", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> createPost(@RequestBody NewPostRequest newPostRequest) {
        return postService.createNewPost(newPostRequest);
    }

    @GetMapping
    @ApiOperation(nickname = "Listar postagem", value = "Listar todas as postagens", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> findAllPosts() {
        return postService.findAll();
    }
}