package scc.vigilancia.comunitaria.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import scc.vigilancia.comunitaria.models.Community;
import scc.vigilancia.comunitaria.models.User;

@Repository
public interface CommunityRepository extends JpaRepository<Community, Integer> {
}
