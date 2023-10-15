package scc.vigilancia.comunitaria.models;


import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import scc.vigilancia.comunitaria.enums.PostType;
import scc.vigilancia.comunitaria.enums.StatusType;
import scc.vigilancia.comunitaria.enums.UserType;

import javax.persistence.*;

@Entity
@Table(name = "POSTAGEM")
@Data
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "id_autor", referencedColumnName = "email")
    private User author;

    @ManyToOne
    @JoinColumn(name = "id_comunidade")
    private Community community;

    @Column(name = "titulo")
    private String title;

    @Column(name = "conteudo")
    private String content;

    @Column(name = "tipo")
    @Enumerated(EnumType.ORDINAL)
    private PostType type;

    @Column(name = "status")
    @Enumerated(EnumType.ORDINAL)
    private StatusType status;

}






