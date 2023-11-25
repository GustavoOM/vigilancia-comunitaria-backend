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
public class Invite {

    @EmbeddedId
    private InviteId id;
}