package by.burov.user.repository.entity;

import by.burov.user.core.enums.UserRole;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "authorities", schema = "spring")
public class Authority implements GrantedAuthority {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private UserRole roleName;

    @ManyToMany(mappedBy = "authorities")
    private Set<User> users;
    public Authority() {
    }

    public Authority(Long id) {
        this.id = id;
    }

    public Authority(Long id, UserRole role) {
        this.id = id;
        this.roleName = role;
    }

    public Authority(UserRole roleName) {
        this.roleName = roleName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UserRole getRoleName() {
        return roleName;
    }

    public void setRoleName(UserRole roleName) {
        this.roleName = roleName;
    }

    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }

    @Override
    public String getAuthority() {
        return getRoleName().name();
    }
}