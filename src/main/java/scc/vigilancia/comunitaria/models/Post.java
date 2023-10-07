package scc.vigilancia.comunitaria.models;


import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "POSTAGEM")
@Data
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

//    @ManyToOne
//    @JoinColumn(name = "id_autor", referencedColumnName = "email")
//    private User author;
//
//    @ManyToOne
//    @JoinColumn(name = "id_comunidade")
//    private Community community;

    @Column(name = "titulo")
    private String title;

    @Column(name = "conteudo")
    private String content;

}






