package scc.vigilancia.comunitaria.controllers;



import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import scc.vigilancia.comunitaria.dto.LoginRequest;
import scc.vigilancia.comunitaria.dto.NewUserMembroRequest;
import scc.vigilancia.comunitaria.dto.NewUserRequest;
import scc.vigilancia.comunitaria.dto.TokenResponse;
import scc.vigilancia.comunitaria.services.AuthenticationService;
import scc.vigilancia.comunitaria.services.UserService;

@RestController
@RequestMapping("/auth")
@Slf4j
@Api(tags = "Endpoints para autenticação")
@CrossOrigin(origins = {"http://localhost:4200"})
public class AuthenticationController {

    private final AuthenticationService authenticationService;
    private final UserService userService;

    public AuthenticationController(AuthenticationService authenticationService, UserService userService) {
        this.authenticationService = authenticationService;
        this.userService = userService;
    }

    @ApiOperation(nickname = "Autenticação", response = TokenResponse.class, value = "Login de usuário da tabela Users")
    @PostMapping
    public ResponseEntity<TokenResponse> authenticate(@RequestBody LoginRequest loginRequest) {
        return ResponseEntity.ok(authenticationService.authenticate(loginRequest));
    }

    @PostMapping("/signup")
    @ApiOperation(nickname = "Cadastro usuário membro", value = "Cadastrar usuário membro", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> createUser(@RequestBody NewUserMembroRequest newUserMembroRequest) {
        return userService.createNewUserMembro(newUserMembroRequest);
    }
}