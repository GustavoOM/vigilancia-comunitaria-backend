package scc.vigilancia.comunitaria.controllers;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
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

    @PostMapping("/enter-community")
    @ApiOperation(nickname = "Adicionar membro a comunidade.", value = "Adiciona membro logado a comunidade.", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> enterCommunity(@RequestParam Integer idCommunity) {
        return userService.enterCommunity(idCommunity);
    }

    @GetMapping("/communities")
    @ApiOperation(nickname = "Listar comunidades do usuário logado", value = "Listar todas as comunidades do usuário logado", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> findAllPostsByCommunity() {
        return userService.getCommunities();
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
}