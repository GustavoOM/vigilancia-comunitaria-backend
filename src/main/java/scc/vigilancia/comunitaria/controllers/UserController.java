package scc.vigilancia.comunitaria.controllers;



import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import scc.vigilancia.comunitaria.dto.New.NewCommunityRequest;
import scc.vigilancia.comunitaria.exceptions.EntityNotFoundException;
import scc.vigilancia.comunitaria.models.User;
import scc.vigilancia.comunitaria.services.CommunityService;
import scc.vigilancia.comunitaria.services.UserService;

@RestController
@RequestMapping("/user")
@Slf4j
@Api(tags = "Endpoints users")
@CrossOrigin(origins = {"*"})
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
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

    @GetMapping("/by-community/{idCommunity}")
    @ApiOperation(nickname = "Listar usuarios de uma comunidade", value = "Listar todos os usuarios de uma comunidade", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> findAllUsersByCommunity(@PathVariable(name = "idCommunity") Integer idCommunity) throws EntityNotFoundException{
        return userService.findAllByCommunity(idCommunity);
    }
}