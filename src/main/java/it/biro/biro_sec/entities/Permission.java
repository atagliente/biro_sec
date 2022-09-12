package it.biro.biro_sec.entities;

import javax.persistence.*;

@Entity
public class Permission {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private String name;

//    @ManyToMany(mappedBy = "privileges")
//    private Collection<Role> roles;

}
