package scc.vigilancia.comunitaria.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import scc.vigilancia.comunitaria.dto.ApiResponse;
import scc.vigilancia.comunitaria.dto.CommunityDTO;
import scc.vigilancia.comunitaria.dto.New.NewCommunityRequest;
import scc.vigilancia.comunitaria.dto.PostDTO;
import scc.vigilancia.comunitaria.enums.PostType;
import scc.vigilancia.comunitaria.enums.StatusType;
import scc.vigilancia.comunitaria.exceptions.EntityNotFoundException;
import scc.vigilancia.comunitaria.models.Community;
import scc.vigilancia.comunitaria.models.Post;
import scc.vigilancia.comunitaria.repositories.CommunityRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class CommunityService{

    private final CommunityRepository communityRepository;

    public CommunityService(CommunityRepository communityRepository) {
        this.communityRepository = communityRepository;
    }

    public Community findCommunityById(Integer id) throws EntityNotFoundException{
        Optional<Community> community = communityRepository
                .findById(id);

        if(community.isEmpty()) {
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
                .message("Comunidade com id "+ communityCreated.getId()  +" criada!")
                .build();
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    public ResponseEntity<Object> findAll() {
        List<Community> communities = communityRepository.findAll();
        List<CommunityDTO> communityDTOS = new ArrayList<>();
        for (Community community : communities) {
            CommunityDTO communityDTO = new CommunityDTO(community);
            communityDTOS.add(communityDTO);
        }
        return ResponseEntity.status(HttpStatus.OK).body(communityDTOS);
    }
}
