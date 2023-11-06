package scc.vigilancia.comunitaria.models;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import scc.vigilancia.comunitaria.enums.UserType;

import javax.persistence.*;
import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "USUARIO")
@Data
public class User implements UserDetails {
    @Id
    private String email;

    @Column(name = "nome")
    private String name;

    @Column(name = "senha")
    private String password;

    @Column(name = "permissao")
    @Enumerated(EnumType.STRING)
    private UserType permission;


    @ManyToMany
    @JoinTable(name = "COMUNIDADE_USUARIO",
            joinColumns = @JoinColumn(name = "email_usuario"),
            inverseJoinColumns = @JoinColumn(name = "id_comunidade"))
    @ToString.Exclude
    private List<Community> communities;

    @JsonIgnore
    @OneToMany(mappedBy = "author", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Post> posts;

    public void addCommunity(Community community){
        communities.add(community);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(permission);
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

}
