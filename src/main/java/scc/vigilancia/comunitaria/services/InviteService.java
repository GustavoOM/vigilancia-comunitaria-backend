package scc.vigilancia.comunitaria.services;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import scc.vigilancia.comunitaria.dto.ApiResponse;
import scc.vigilancia.comunitaria.dto.InviteRequest;
import scc.vigilancia.comunitaria.dto.PendingInvite;
import scc.vigilancia.comunitaria.models.Community;
import scc.vigilancia.comunitaria.models.Invite;
import scc.vigilancia.comunitaria.models.InviteId;
import scc.vigilancia.comunitaria.models.User;
import scc.vigilancia.comunitaria.repositories.InviteRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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
            InviteId inviteId = invite.getId();
            pendingInvitesResponse.add(
                    PendingInvite.builder()
                            .communityId(inviteId.getCommunity().getId())
                            .communityName(inviteId.getCommunity().getName())
                            .userName(inviteId.getUser().getName())
                            .userEmail(inviteId.getUser().getEmail())
                            .status("PENDING").build()
            );
        }
        return ResponseEntity.ok(pendingInvitesResponse);
    }

    public ResponseEntity<Object> findAllLoggerUser() {
        List<Invite> invites = inviteRepository.findAll();
        List<PendingInvite> pendingInvitesResponse = new ArrayList<>();
        for (Invite invite : invites) {
            InviteId inviteId = invite.getId();
            if (Objects.equals(inviteId.getUser().getEmail(), userService.getIdLoggedUser())){
                pendingInvitesResponse.add(
                        PendingInvite.builder()
                                .communityId(inviteId.getCommunity().getId())
                                .communityName(inviteId.getCommunity().getName())
                                .userName(inviteId.getUser().getName())
                                .userEmail(inviteId.getUser().getEmail())
                                .status("PENDING").build()
                );
            }
        }
        return ResponseEntity.ok(pendingInvitesResponse);
    }

    public ResponseEntity<Object> requestCommunity(Integer communityId) {
        Community community = new Community();
        community.setId(communityId);

        User user = new User();
        user.setEmail(userService.getIdLoggedUser());

        Invite invite = new Invite();
        InviteId inviteId = new InviteId();
        inviteId.setCommunity(community);
        inviteId.setUser(user);
        invite.setId(inviteId);

        inviteRepository.save(invite);
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.builder().message("Convite enviado com sucesso.").build());
    }

    public ResponseEntity<Object> updateInvite(InviteRequest inviteRequest) {
        User user = userService.findUserByEmail(inviteRequest.getUserEmail());

        Community community = new Community();
        community.setId(inviteRequest.getCommunityId());

        Invite invite = new Invite();
        InviteId inviteId = new InviteId();
        inviteId.setCommunity(community);
        inviteId.setUser(user);
        invite.setId(inviteId);

        inviteRepository.delete(invite);

        if(inviteRequest.isAccept()) {
            userService.addUserToCommunity(inviteRequest.getCommunityId(), user);
            return ResponseEntity.ok("Usuário adicionado à comunidade com sucesso");
        }
        return ResponseEntity.ok("Convite rejeitado com sucesso");
    }
}
