package scc.vigilancia.comunitaria.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import scc.vigilancia.comunitaria.configs.MD5PasswordEncoder;
import scc.vigilancia.comunitaria.dto.ApiResponse;
import scc.vigilancia.comunitaria.dto.NewUserMembroRequest;
import scc.vigilancia.comunitaria.dto.NewUserRequest;
import scc.vigilancia.comunitaria.enums.UserType;
import scc.vigilancia.comunitaria.exceptions.EntityNotFoundException;
import scc.vigilancia.comunitaria.models.Community;
import scc.vigilancia.comunitaria.models.User;
import scc.vigilancia.comunitaria.repositories.CommunityRepository;
import scc.vigilancia.comunitaria.repositories.UserRepository;

import java.util.Optional;

@Service
@Slf4j
public class CommunityService{

    private final CommunityRepository communityRepository;

    public CommunityService(CommunityRepository communityRepository) {
        this.communityRepository = communityRepository;
    }

    public Community findCommunityById(Integer id) {
        Optional<Community> community = communityRepository
                .findById(id);

        if(community.isEmpty()) {
            throw new EntityNotFoundException("Comunidade não encontrada");
        }
        Community found = community.get();
        return found;
    }

}
