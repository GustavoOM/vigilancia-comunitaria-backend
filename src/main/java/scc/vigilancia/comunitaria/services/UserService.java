package scc.vigilancia.comunitaria.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import scc.vigilancia.comunitaria.configs.MD5PasswordEncoder;
import scc.vigilancia.comunitaria.dto.ApiResponse;
import scc.vigilancia.comunitaria.dto.CommunityDTO;
import scc.vigilancia.comunitaria.dto.New.NewUserMembroRequest;
import scc.vigilancia.comunitaria.dto.New.NewUserRequest;
import scc.vigilancia.comunitaria.dto.PostDTO;
import scc.vigilancia.comunitaria.dto.UserDTO;
import scc.vigilancia.comunitaria.enums.UserType;
import scc.vigilancia.comunitaria.exceptions.EntityNotFoundException;
import scc.vigilancia.comunitaria.models.Community;
import scc.vigilancia.comunitaria.models.Post;
import scc.vigilancia.comunitaria.models.User;
import scc.vigilancia.comunitaria.repositories.UserRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@Slf4j
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final MD5PasswordEncoder md5PasswordEncoder;
    private final CommunityService communityService;


    public UserService(UserRepository userRepository, MD5PasswordEncoder md5PasswordEncoder, CommunityService communityService) {
        this.userRepository = userRepository;
        this.md5PasswordEncoder = md5PasswordEncoder;
        this.communityService = communityService;
    }

    public ResponseEntity<Object> createNewUser(NewUserRequest newUserRequest) {
        User user = new User();
        user.setEmail(newUserRequest.getEmail());
        user.setName(newUserRequest.getName());
        user.setPermission(UserType.valueOf(newUserRequest.getPermission()));
        user.setPassword(md5PasswordEncoder.encode(newUserRequest.getPassword()));

        //Coloca o usuário na comunidade 1
        Community community = communityService.findCommunityById(1);
        user.setCommunities(List.of(community));

        userRepository.save(user);
        ApiResponse response = ApiResponse
                .builder()
                .code("SUCESSO")
                .message(newUserRequest.getName() + " criado!")
                .build();
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    public ResponseEntity<Object> enterCommunity(Integer idCommunity) {
        User user = findUserByEmail(getIdLoggedUser());
        Community community = communityService.findCommunityById(idCommunity);

        user.addCommunity(community);
        userRepository.save(user);

        ApiResponse response = ApiResponse
                .builder()
                .code("SUCESSO")
                .message("Membro adicionado a comunidade!")
                .build();
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    public ResponseEntity<Object> getCommunities() {
        User user = findUserByEmail(getIdLoggedUser());
        List<CommunityDTO> communityDTOS = new ArrayList<>();
        for (Community community : user.getCommunities()) {
            CommunityDTO communityDTO = new CommunityDTO(community);
            communityDTOS.add(communityDTO);
        }
        return ResponseEntity.status(HttpStatus.OK).body(communityDTOS);
    }

    public ResponseEntity<Object> createNewUserMembro(NewUserMembroRequest newUserMembroRequest) {
        User user = new User();
        user.setEmail(newUserMembroRequest.getEmail());
        user.setName(newUserMembroRequest.getName());
        user.setPermission(UserType.MEMBRO);
        user.setPassword(md5PasswordEncoder.encode(newUserMembroRequest.getPassword()));

        userRepository.save(user);

        ApiResponse response = ApiResponse
                .builder()
                .code("SUCESSO")
                .message(newUserMembroRequest.getName() + " criado!")
                .build();
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    public User findUserByEmail(String email) {
        Optional<User> user = userRepository
                .findByEmail(email);

        if (user.isEmpty()) {
            throw new EntityNotFoundException("Usuário não encontrado");
        }
        User found = user.get();
        found.setEmail(found.getEmail().trim());
        return found;
    }

    public ResponseEntity<Object> findAllByCommunity(Integer communityId) throws EntityNotFoundException {
        communityService.findCommunityById(communityId); //Verifica se a comunidade existe

        List<UserDTO> userDTOS = new ArrayList<>();
        List<User> users = userRepository.findAllByCommunities_IdOrderByNameAsc(communityId);
        for (User user : users) {
            UserDTO userDTO = new UserDTO(user);
            userDTOS.add(userDTO);
        }

        return ResponseEntity.status(HttpStatus.OK).body(userDTOS);
    }

    public ResponseEntity<Object> findAll() {
        List<User> users = userRepository.findAllByOrderByNameAsc();

        List<UserDTO> userDTOS = new ArrayList<>();
        for (User user : users) {
            UserDTO userDTO = new UserDTO(user);
            userDTOS.add(userDTO);
        }

        return ResponseEntity.status(HttpStatus.OK).body(userDTOS);
    }

    @Override
    public UserDetails loadUserByUsername(String username) {
        log.info("[ UserService ] - Finding user by login.");
        return findUserByEmail(username);
    }

    public String getIdLoggedUser() throws EntityNotFoundException {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String emailUsuario = user.getEmail();
        if (emailUsuario == null) {
            throw new EntityNotFoundException("Não existe nenhum usuário logado");
        }
        return emailUsuario;
    }


}
