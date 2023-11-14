package scc.vigilancia.comunitaria.models;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Table(name = "convite")
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@IdClass(InviteId.class)
public class Invite {
    @Id
    @ManyToOne
    @JoinColumn(name = "id_comunidade")
    private Community community;

    @Id
    @ManyToOne
    @JoinColumn(name = "email_usuario")
    private User user;
}