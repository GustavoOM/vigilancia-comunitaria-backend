package scc.vigilancia.comunitaria.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import scc.vigilancia.comunitaria.dto.ApiResponse;
import scc.vigilancia.comunitaria.dto.NewPostRequest;
import scc.vigilancia.comunitaria.dto.PostDTO;
import scc.vigilancia.comunitaria.enums.PostType;
import scc.vigilancia.comunitaria.enums.StatusType;
import scc.vigilancia.comunitaria.exceptions.EntityNotFoundException;
import scc.vigilancia.comunitaria.models.Community;
import scc.vigilancia.comunitaria.models.Post;
import scc.vigilancia.comunitaria.models.User;
import scc.vigilancia.comunitaria.repositories.PostRepository;

import java.util.ArrayList;
import java.util.List;


@Service
@Slf4j
public class PostService {

    private final UserService userService;

    private final CommunityService communityService;
    private final PostRepository postRepository;

    public PostService(UserService userService, PostRepository postRepository, CommunityService communityService) {
        this.userService = userService;
        this.postRepository = postRepository;
        this.communityService = communityService;
    }

    public ResponseEntity<Object> createNewPost(NewPostRequest newPostRequest) throws EntityNotFoundException {
        User user = userService.findUserByEmail(userService.getIdLoggedUser());
        Post post = new Post();
        post.setAuthor(user);
        //Comunidade criada apenas para testes
        Community community = communityService.findCommunityById(newPostRequest.getIdCommunity());
        post.setCommunity(community);

        post.setTitle(newPostRequest.getTitle());
        post.setContent(newPostRequest.getContent());
        post.setType(PostType.valueOf(newPostRequest.getType()));
        post.setStatus(StatusType.valueOf(newPostRequest.getStatus()));

        Post postCriado = postRepository.save(post);
        ApiResponse response = ApiResponse
                .builder()
                .code("SUCESSO")
                .message(postCriado.getId() + " criado!")
                .build();
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    public ResponseEntity<Object> findAll() {
        List<Post> posts = postRepository.findAll();
        List<PostDTO> postsDTOs = new ArrayList<>();
        for(Post post : posts) {
            PostDTO postDTO = new PostDTO(post);
            postsDTOs.add(postDTO);
        }
        return ResponseEntity.status(HttpStatus.OK).body(postsDTOs);
    }
}





