package scc.vigilancia.comunitaria.controllers;



import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import scc.vigilancia.comunitaria.dto.LoginRequest;
import scc.vigilancia.comunitaria.dto.NewUserRequest;
import scc.vigilancia.comunitaria.dto.TokenResponse;
import scc.vigilancia.comunitaria.services.AuthenticationService;
import scc.vigilancia.comunitaria.services.UserService;

@RestController
@RequestMapping("/admin")
@Slf4j
@Api(tags = "Área do administrador")
@CrossOrigin(origins = {"http://localhost:4200", "http://ec2-15-228-86-83.sa-east-1.compute.amazonaws.com"})
public class AdminController {

    private final AuthenticationService authenticationService;
    private final UserService userService;

    public AdminController(AuthenticationService authenticationService, UserService userService) {
        this.authenticationService = authenticationService;
        this.userService = userService;
    }

    @PostMapping("/create-users")
    @ApiOperation(nickname = "Cadastrar usuário", value = "Cadastrar usuário", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> createUser(@RequestBody NewUserRequest newUserRequest) {
        return userService.createNewUser(newUserRequest);
    }
}