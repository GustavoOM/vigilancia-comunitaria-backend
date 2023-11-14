package scc.vigilancia.comunitaria.controllers;



import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import scc.vigilancia.comunitaria.dto.InviteRequest;
import scc.vigilancia.comunitaria.dto.New.NewUserRequest;
import scc.vigilancia.comunitaria.services.InviteService;
import scc.vigilancia.comunitaria.services.UserService;

@RestController
@RequestMapping("/admin")
@Slf4j
@Api(tags = "Endpoints do administrador")
@CrossOrigin(origins = {"*"})
public class AdminController {
    private final UserService userService;

    private final InviteService inviteService;

    public AdminController(UserService userService, InviteService inviteService) {
        this.userService = userService;
        this.inviteService = inviteService;
    }

    @PostMapping("/create-users")
    @ApiOperation(nickname = "Cadastrar usuário", value = "Cadastrar usuário", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> createUser(@RequestBody NewUserRequest newUserRequest) {
        return userService.createNewUser(newUserRequest);
    }

    @GetMapping
    @ApiOperation(nickname = "Listar convites pendentes", value = "Listar convites pendentes", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> findAllInvites() {
        return inviteService.findAll();
    }

    @PutMapping
    @ApiOperation(nickname = "Aceitar/Rejeitar convite", value = "Aceitar/Rejeitar convite", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> updateInviteSituation(@RequestBody InviteRequest inviteRequest) {
        return inviteService.updateInvite(inviteRequest);
    }

}