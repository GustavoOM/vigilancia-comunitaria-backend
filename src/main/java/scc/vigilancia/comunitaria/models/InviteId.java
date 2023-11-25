package scc.vigilancia.comunitaria.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.bind.annotation.GetMapping;

import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.io.Serializable;


@Embeddable
@Setter
@Getter
@NoArgsConstructor
public class InviteId implements Serializable {

    @ManyToOne
    @JoinColumn(name = "id_comunidade")
    private Community community;

    @ManyToOne
    @JoinColumn(name = "email_usuario")
    private User user;
}
