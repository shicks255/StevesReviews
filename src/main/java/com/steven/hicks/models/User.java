package com.steven.hicks.models;

import javax.persistence.*;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    private String username;
    private String password;
    @Column(name = "email_list")
    private boolean emailList;
    @Column(name = "email_address")
    private String emailAddress;
    @Column(name = "is_admin")
    private boolean admin;
}
