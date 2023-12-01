package scc.vigilancia.comunitaria.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import scc.vigilancia.comunitaria.dto.ApiResponse;
import scc.vigilancia.comunitaria.dto.CommunityDTO;
import scc.vigilancia.comunitaria.dto.New.NewCommunityRequest;
import scc.vigilancia.comunitaria.dto.UserCommunitiesByStatus;
import scc.vigilancia.comunitaria.exceptions.EntityNotFoundException;
import scc.vigilancia.comunitaria.models.Community;
import scc.vigilancia.comunitaria.repositories.CommunityRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@Slf4j
public class CommunityService {

    private final CommunityRepository communityRepository;

    public CommunityService(CommunityRepository communityRepository) {
        this.communityRepository = communityRepository;
    }

    public Community findCommunityById(Integer id) throws EntityNotFoundException {
        Optional<Community> community = communityRepository
                .findById(id);

        if (community.isEmpty()) {
            throw new EntityNotFoundException("Comunidade não encontrada");
        }
        Community found = community.get();
        return found;
    }

    public ResponseEntity<Object> create(NewCommunityRequest NewCommunityRequest) {
        Community community = new Community();
        community.setName(NewCommunityRequest.getName());

        Community communityCreated = communityRepository.save(community);
        ApiResponse response = ApiResponse
                .builder()
                .code("SUCESSO")
                .message("Comunidade com id " + communityCreated.getId() + " criada!")
                .build();
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    public List<CommunityDTO> findAll() {
        List<Community> communities = communityRepository.findAll();
        List<CommunityDTO> communityDTOS = new ArrayList<>();
        for (Community community : communities) {
            CommunityDTO communityDTO = new CommunityDTO(community);
            communityDTOS.add(communityDTO);
        }
        return communityDTOS;
    }

    public UserCommunitiesByStatus findCommunitiesByParticipation(String email) {
        List<CommunityDTO> myCommunities = new ArrayList<>();
        List<CommunityDTO> pendingInviteCommunities = new ArrayList<>();
        List<CommunityDTO> others = new ArrayList<>();

        List<Map<String, Object>> communitiesData = communityRepository.findCommunitiesByParticipation(email);

        for (Map<String, Object> communityData : communitiesData) {
            CommunityDTO community = new CommunityDTO(
                    (Integer) communityData.get("id"),
                    (String) communityData.get("nome")
            );

            String status = (String) communityData.get("status");

            switch (status) {
                case "mine":
                    myCommunities.add(community);
                    break;
                case "pending":
                    pendingInviteCommunities.add(community);
                    break;
                default:
                    others.add(community);
            }
        }

        return new UserCommunitiesByStatus(myCommunities, pendingInviteCommunities, others);
    }
}
