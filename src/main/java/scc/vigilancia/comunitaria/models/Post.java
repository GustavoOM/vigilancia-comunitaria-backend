package scc.vigilancia.comunitaria.models;


import lombok.Data;
import scc.vigilancia.comunitaria.enums.PostType;
import scc.vigilancia.comunitaria.enums.StatusType;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;

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

    @Column(name = "conteudo")
    private String content;

    @Column(name = "tipo")
    @Enumerated(EnumType.ORDINAL)
    private PostType type;

    @Column(name = "status")
    @Enumerated(EnumType.ORDINAL)
    private StatusType status;

    @Column(name = "createdat")
    @CreationTimestamp
    private Timestamp createdAt;

    @ManyToMany(cascade = CascadeType.PERSIST)
    @JoinTable(
            name = "POSTAGEM_IMAGEM",
            joinColumns = @JoinColumn(name = "id_postagem"),
            inverseJoinColumns = @JoinColumn(name = "id_imagem")
    )
    private List<Image> images;
}






