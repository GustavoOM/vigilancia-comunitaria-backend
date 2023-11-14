package scc.vigilancia.comunitaria.services;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import scc.vigilancia.comunitaria.dto.InviteRequest;
import scc.vigilancia.comunitaria.dto.PendingInvite;
import scc.vigilancia.comunitaria.models.Community;
import scc.vigilancia.comunitaria.models.Invite;
import scc.vigilancia.comunitaria.models.User;
import scc.vigilancia.comunitaria.repositories.InviteRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class InviteService {

    private InviteRepository inviteRepository;
    private UserService userService;

    public InviteService(InviteRepository inviteRepository, UserService userService) {
        this.inviteRepository = inviteRepository;
        this.userService = userService;
    }

    public ResponseEntity<Object> findAll() {
        List<Invite> invites = inviteRepository.findAll();
        List<PendingInvite> pendingInvitesResponse = new ArrayList<>();
        for (Invite invite : invites) {
            pendingInvitesResponse.add(
                    PendingInvite.builder()
                            .communityName(invite.getCommunity().getName())
                            .userName(invite.getUser().getName())
                            .status("PENDING").build()
            );
        }
        return ResponseEntity.ok(pendingInvitesResponse);
    }

    public ResponseEntity<Object> requestCommunity(Integer communityId) {
        Community community = new Community();
        community.setId(communityId);

        User user = new User();
        user.setEmail(userService.getIdLoggedUser());

        Invite invite = new Invite();
        invite.setCommunity(community);
        invite.setUser(user);

        inviteRepository.save(invite);
        return ResponseEntity.ok("Convite enviado com sucesso.");
    }

    public ResponseEntity<Object> updateInvite(InviteRequest inviteRequest) {
        User user = new User();
        user.setEmail(inviteRequest.getUserEmail());

        Community community = new Community();
        community.setId(inviteRequest.getCommunityId());

        Invite invite = new Invite();
        invite.setUser(user);
        invite.setCommunity(community);

        inviteRepository.delete(invite);

        if(inviteRequest.isAccept()) {
            userService.addUserToCommunity(inviteRequest.getCommunityId(), user);
            return ResponseEntity.ok("Usuário adicionado à comunidade com sucesso");
        }
        return ResponseEntity.ok("Convite rejeitado com sucesso");
    }
}
