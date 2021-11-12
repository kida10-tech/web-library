package com.libreria.libreria.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
public class CustomerEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private Long dni;
    private String name;
    private String lastName;
    @Column(unique = true, nullable = false)
    private String phoneNumber;
    private Boolean isActive;
}
