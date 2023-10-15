package scc.vigilancia.comunitaria.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import scc.vigilancia.comunitaria.dto.ApiResponse;
import scc.vigilancia.comunitaria.dto.NewPostRequest;
import scc.vigilancia.comunitaria.enums.PostType;
import scc.vigilancia.comunitaria.enums.StatusType;
import scc.vigilancia.comunitaria.models.Community;
import scc.vigilancia.comunitaria.models.Post;
import scc.vigilancia.comunitaria.models.User;
import scc.vigilancia.comunitaria.repositories.PostRepository;


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

    public ResponseEntity<Object> createNewPost(NewPostRequest newPostRequest) {
        User user = userService.findUserByEmail(newPostRequest.getEmailAuthor());
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
}
