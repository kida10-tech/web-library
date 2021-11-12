
package com.libreria.libreria.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class BookEntity {
    @Id
    private Long isbn;
    @Column(name = "title", unique = true)
    private String title;
    private Integer year;
    private Integer bookCopies;
    private Integer borrowedBooks;
    private Integer booksRemaining;
    private Boolean enable;

    @OneToOne
    private AuthorEntity author;
    @OneToOne
    private EditorialEntity editorial;

}