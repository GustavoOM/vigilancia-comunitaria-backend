package scc.vigilancia.comunitaria.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import scc.vigilancia.comunitaria.models.Community;
import scc.vigilancia.comunitaria.models.User;

import java.util.List;
import java.util.Map;

@Repository
public interface CommunityRepository extends JpaRepository<Community, Integer> {

    @Query(value = "SELECT\n" +
            "   c.*,\n" +
            "   CASE\n" +
            "       WHEN cu.email_usuario IS NOT NULL THEN 'mine'\n" +
            "       WHEN ci.email_usuario IS NOT NULL THEN 'pending'\n" +
            "       ELSE 'others'\n" +
            "   END AS status\n" +
            "FROM comunidade c\n" +
            "LEFT JOIN comunidade_usuario cu ON c.id = cu.id_comunidade AND cu.email_usuario = :userEmail\n" +
            "LEFT JOIN convite ci ON c.id = ci.id_comunidade AND ci.email_usuario = :userEmail", nativeQuery = true)
    List<Map<String, Object>> findCommunitiesByParticipation(@Param("userEmail") String userEmail);
}
