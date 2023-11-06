package scc.vigilancia.comunitaria.controllers;



import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import scc.vigilancia.comunitaria.dto.New.NewUserRequest;
import scc.vigilancia.comunitaria.services.UserService;

@RestController
@RequestMapping("/admin")
@Slf4j
@Api(tags = "Endpoints do administrador")
@CrossOrigin(origins = {"*"})
public class AdminController {
    private final UserService userService;

    public AdminController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/create-users")
    @ApiOperation(nickname = "Cadastrar usuário", value = "Cadastrar usuário", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> createUser(@RequestBody NewUserRequest newUserRequest) {
        return userService.createNewUser(newUserRequest);
    }
}