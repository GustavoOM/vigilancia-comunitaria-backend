package scc.vigilancia.comunitaria.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import scc.vigilancia.comunitaria.configs.MD5PasswordEncoder;
import scc.vigilancia.comunitaria.dto.ApiResponse;
import scc.vigilancia.comunitaria.dto.NewUserMembroRequest;
import scc.vigilancia.comunitaria.dto.NewUserRequest;
import scc.vigilancia.comunitaria.enums.UserType;
import scc.vigilancia.comunitaria.exceptions.EntityNotFoundException;
import scc.vigilancia.comunitaria.models.User;
import scc.vigilancia.comunitaria.repositories.UserRepository;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final MD5PasswordEncoder md5PasswordEncoder;


    public UserService(UserRepository userRepository, MD5PasswordEncoder md5PasswordEncoder) {
        this.userRepository = userRepository;
        this.md5PasswordEncoder = md5PasswordEncoder;
    }

    public ResponseEntity<Object> createNewUser(NewUserRequest newUserRequest) {
        User user = new User();
        user.setEmail(newUserRequest.getEmail());
        user.setName(newUserRequest.getName());
        user.setPermission(UserType.valueOf(newUserRequest.getPermission()));
        user.setPassword(md5PasswordEncoder.encode(newUserRequest.getPassword()));
        userRepository.save(user);
        ApiResponse response = ApiResponse
                .builder()
                .code("SUCESSO")
                .message(newUserRequest.getName() + " criado!")
                .build();
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
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

        if(user.isEmpty()) {
            throw new EntityNotFoundException("Usuário não encontrado");
        }
        User found = user.get();
        found.setEmail(found.getEmail().trim());
        return found;
    }

    @Override
    public UserDetails loadUserByUsername(String username) {
        log.info("[ UserService ] - Finding user by login.");
        return findUserByEmail(username);
    }
}
