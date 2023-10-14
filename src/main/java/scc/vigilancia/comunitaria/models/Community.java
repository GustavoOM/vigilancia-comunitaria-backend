package scc.vigilancia.comunitaria.models;


import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "COMUNIDADE")
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class Community {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "nome")
    private String name;

    @ManyToMany
    @JoinTable(name = "COMUNIDADE_USUARIO",
            joinColumns = @JoinColumn(name = "id_comunidade"),
            inverseJoinColumns = @JoinColumn(name = "email_usuario"))
    @ToString.Exclude
    private List<User> members;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Community community = (Community) o;
        return id != null && Objects.equals(id, community.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}