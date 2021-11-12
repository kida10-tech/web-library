package com.libreria.libreria.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Date;

@Entity
@Getter
@Setter
public class LoanEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Temporal(TemporalType.TIMESTAMP)
    private Date loanDate;
    @Temporal(TemporalType.TIMESTAMP)
    private Date returnDate;

    @OneToOne
    private BookEntity book;
    @OneToOne
    private CustomerEntity customer;
}
