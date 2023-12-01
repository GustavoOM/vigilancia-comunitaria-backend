package scc.vigilancia.comunitaria.controllers;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import scc.vigilancia.comunitaria.exceptions.EntityNotFoundException;
import scc.vigilancia.comunitaria.services.InviteService;
import scc.vigilancia.comunitaria.services.UserService;

@RestController
@RequestMapping("/user")
@Slf4j
@Api(tags = "Endpoints users")
@CrossOrigin(origins = {"*"})
public class UserController {

    private final UserService userService;
    private final InviteService inviteService;

    public UserController(UserService userService, InviteService inviteService) {
        this.userService = userService;
        this.inviteService = inviteService;
    }

    @GetMapping("/communities")
    @ApiOperation(nickname = "Listar comunidades do usuário logado", value = "Listar todas as comunidades do usuário logado", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> findAllUsersCommunities() {
        return userService.getCommunities();
    }

    @GetMapping("/communities-to-enter")
    @ApiOperation(nickname = "Listar comunidades que usuário logado ainda não faz parte", value = "Listar comunidades que usuário logado ainda não faz parte", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> findAllUsersCommunitiesToEnter() {
        return userService.getCommunitiesToEnter();
    }

    @GetMapping("/by-community/{idCommunity}")
    @ApiOperation(nickname = "Listar usuarios de uma comunidade", value = "Listar todos os usuarios de uma comunidade", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> findAllUsersByCommunity(@PathVariable(name = "idCommunity") Integer idCommunity) throws EntityNotFoundException {
        return userService.findAllByCommunity(idCommunity);
    }

    @GetMapping("/communities-by-status")
    @ApiOperation(nickname = "Listar comunidades do usuário logado por participação", value = "Listar todas as comunidades do usuário logado por participação", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> findAllCommunitiesByStatus() {
        return userService.getCommunitiesByStatus();
    }

    @PostMapping("/request-community")
    @ApiOperation(nickname = "Pedir pra entrar na comunidade", value = "Pedir pra entrar na comunidade", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> requestCommunity(Integer communityId) {
        return inviteService.requestCommunity(communityId);
    }

    @GetMapping("/invites-logged-user")
    @ApiOperation(nickname = "Listar convites pendentes do usuário logado", value = "Listar convites pendentes do usuário logado", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> findAllInvitesLoggerUser() {
        return inviteService.findAllLoggerUser();
    }
}