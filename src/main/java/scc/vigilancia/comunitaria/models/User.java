package scc.vigilancia.comunitaria.models;


import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "USUARIO")
@Data
public class User {
    @Id
    private String email;

    @Column(name = "nome")
    private String name;

    @Column(name = "senha")
    private String password;

    @Column(name = "permissao")
    private Integer permission;

//    @ManyToMany(mappedBy = "members",fetch = FetchType.LAZY)
//    private List<Community> communities;
}
