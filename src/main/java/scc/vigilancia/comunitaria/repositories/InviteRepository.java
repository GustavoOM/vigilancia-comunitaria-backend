package scc.vigilancia.comunitaria.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import scc.vigilancia.comunitaria.models.Invite;
import scc.vigilancia.comunitaria.models.InviteId;

public interface InviteRepository extends JpaRepository<Invite, InviteId> {
}
