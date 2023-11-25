package scc.vigilancia.comunitaria.models;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "COMUNIDADE")
@Getter
@Setter
@ToString
public class Community {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "nome")
    private String name;

    @JsonIgnore
    @ManyToMany(mappedBy = "communities",fetch = FetchType.LAZY)
    private List<User> members = new ArrayList<>();

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

    public Community() {
    }

    public Community(Integer id, String name, List<User> members) {
        this.id = id;
        this.name = name;
        this.members = members;
    }
}