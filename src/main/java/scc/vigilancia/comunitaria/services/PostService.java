package scc.vigilancia.comunitaria.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import scc.vigilancia.comunitaria.dto.ApiResponse;
import scc.vigilancia.comunitaria.dto.New.NewPostRequest;
import scc.vigilancia.comunitaria.dto.PostDTO;
import scc.vigilancia.comunitaria.enums.PostType;
import scc.vigilancia.comunitaria.enums.StatusType;
import scc.vigilancia.comunitaria.enums.UserType;
import scc.vigilancia.comunitaria.exceptions.EntityNotFoundException;
import scc.vigilancia.comunitaria.models.Community;
import scc.vigilancia.comunitaria.models.Image;
import scc.vigilancia.comunitaria.models.Post;
import scc.vigilancia.comunitaria.models.User;
import scc.vigilancia.comunitaria.repositories.PostRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


@Service
@Slf4j
public class PostService {

    private final UserService userService;

    private final CommunityService communityService;
    private final PostRepository postRepository;
    private final S3Service s3Service;

    public PostService(UserService userService, CommunityService communityService, PostRepository postRepository, S3Service s3Service) {
        this.userService = userService;
        this.communityService = communityService;
        this.postRepository = postRepository;
        this.s3Service = s3Service;
    }

    public ResponseEntity<Object> createNewPost(NewPostRequest newPostRequest, MultipartFile file) throws EntityNotFoundException {
        User user = userService.findUserByEmail(userService.getIdLoggedUser());
        //Comunidade criada apenas para testes
        Community community = communityService.findCommunityById(newPostRequest.getIdCommunity());

        Post post = new Post();
        post.setAuthor(user);
        post.setCommunity(community);
        post.setContent(newPostRequest.getContent());
        post.setType(PostType.valueOf(newPostRequest.getType()));
        post.setStatus(StatusType.valueOf(newPostRequest.getStatus()));

        saveImage(file, post);

        Post postCriado = postRepository.save(post);
        ApiResponse response = ApiResponse
                .builder()
                .code("SUCESSO")
                .message(postCriado.getId() + " criado!")
                .build();
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    private void saveImage(MultipartFile file, Post post) {
        if (file == null) return;

        String imageName = s3Service.createS3Request(file);
        Image img = new Image();
        img.setCaminhoS3(imageName);
        if(post.getImages() == null){
            post.setImages(List.of(img));
        } else {
            post.getImages().add(img);
        }
    }

    public ResponseEntity<Object> findAll() {
        List<Post> posts = postRepository.findAll();
        List<PostDTO> postsDTOs = new ArrayList<>();
        for (Post post : posts) {
            PostDTO postDTO = new PostDTO(post);
            postsDTOs.add(postDTO);
        }
        return ResponseEntity.status(HttpStatus.OK).body(postsDTOs);
    }

    public ResponseEntity<Object> findAllByCommunity(Integer communityId) throws EntityNotFoundException{
        User user = userService.findUserByEmail(userService.getIdLoggedUser());
        GrantedAuthority grantedAuthority = user.getAuthorities().iterator().next();

        communityService.findCommunityById(communityId);

        List<PostDTO> postsDTOs = new ArrayList<>();

        if (Objects.equals(grantedAuthority.getAuthority(), UserType.ADMINISTRADOR.name()) || user.getCommunities().stream().anyMatch(community -> community.getId().equals(communityId))){
            List<Post> posts = postRepository.findAllByCommunity_Id(communityId);
            for (Post post : posts) {
                PostDTO postDTO = new PostDTO(post);
                postsDTOs.add(postDTO);
            }
        }

        return ResponseEntity.status(HttpStatus.OK).body(postsDTOs);
    }
}





