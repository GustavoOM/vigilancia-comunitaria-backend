package scc.vigilancia.comunitaria.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import scc.vigilancia.comunitaria.models.Post;

@Repository
public interface PostRepository extends JpaRepository<Post, Integer> {
}
