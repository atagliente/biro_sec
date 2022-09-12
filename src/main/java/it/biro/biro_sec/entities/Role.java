package it.biro.biro_sec.entities;

import javax.persistence.*;
import java.util.Collection;

@Entity
@Table
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    @ManyToMany(mappedBy = "roles")
    private Collection<Account> accounts;

    @ManyToMany
    @JoinTable(
            name = "roles_permission",
            joinColumns = @JoinColumn(name = "role_id"),
            inverseJoinColumns = @JoinColumn(name = "permission_id"))
    private Collection<Permission> privileges;

    public Long getId() {
        return this.id;
    }

    public Collection<Account> getUsers() {
        return accounts;
    }

    public void setUsers(Collection<Account> accounts) {
        this.accounts = accounts;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
