package scc.vigilancia.comunitaria.models;


import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "COMUNIDADE")
@Data
public class Community {

    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nome")
    private String name;

//    @ManyToMany
//    @JoinTable(name = "COMUNIDADE_USUARIO",
//            joinColumns = @JoinColumn(name = "id_comunidade"),
//            inverseJoinColumns = @JoinColumn(name = "email_usuario"))
//    private List<User> members;
}