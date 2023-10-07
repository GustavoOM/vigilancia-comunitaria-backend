package scc.vigilancia.comunitaria.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import scc.vigilancia.comunitaria.models.Post;
import scc.vigilancia.comunitaria.models.User;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
}
