package com.libreria.libreria.service;

import com.libreria.libreria.entity.AuthorEntity;
import com.libreria.libreria.entity.BookEntity;
import com.libreria.libreria.exception.ExceptionService;
import com.libreria.libreria.repository.AuthorRepository;
import com.libreria.libreria.repository.BookRepository;
import com.libreria.libreria.repository.EditorialRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class BookService implements GeneralService<BookEntity> {

    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private AuthorRepository authorRepository;
    @Autowired
    private EditorialRepository editorialRepository;

    @Transactional
    public void add(BookEntity bookEntity) throws ExceptionService {
        validate(bookEntity);
        bookRepository.save(bookEntity);
    }

    @Transactional
    public void modify(Long isbn, String title, Integer year, Integer bookCopies, Integer borrowedBooks, Integer booksRemaining, Long author, Long editorial) throws ExceptionService {

        Optional<BookEntity> response = bookRepository.findById(isbn);
        if (response.isPresent()) {
            BookEntity book = response.get();
            book.setIsbn(isbn);
            book.setTitle(title);
            book.setYear(year);
            book.setBookCopies(bookCopies);
            book.setBorrowedBooks(borrowedBooks);
            book.setBooksRemaining(booksRemaining);
            book.setAuthor(authorRepository.findById(author).get());
            book.setEditorial(editorialRepository.findById(editorial).get());

            bookRepository.save(book);
        }else{
            throw new ExceptionService("Book does not exist.");
        }
    }

    @Transactional
    public void subscribe(Long isbn) throws ExceptionService{
        Optional<BookEntity> response = bookRepository.findById(isbn);
        if (response.isPresent()) {
            BookEntity bookEntity = response.get();

            bookEntity.setEnable(true);
            bookRepository.save(bookEntity);
        }else{
            throw new ExceptionService("Book does not exist.");
        }
    }

    @Transactional
    public void unsubscribe(Long isbn) throws ExceptionService {
        Optional<BookEntity> response = bookRepository.findById(isbn);
        if (response.isPresent()) {
            BookEntity bookEntity = response.get();

            bookEntity.setEnable(false);
            bookRepository.save(bookEntity);
        }else{
            throw new ExceptionService("Book does not exist.");
        }
    }

    @Override
    public void delete(Long id) throws ExceptionService {
        bookRepository.deleteById(id);
    }

    @Override
    public List<BookEntity> show() throws ExceptionService {
        return bookRepository.findAll();
    }

    @Override
    public Optional<BookEntity> findById(Long id) throws ExceptionService {
        return bookRepository.findById(id);
    }

    public void validate(BookEntity bookEntity) throws ExceptionService {
        if (bookEntity.getTitle() == null || bookEntity.getTitle().isEmpty()) {
            throw new ExceptionService("Title can't be null.");
        }
        int currentYear = LocalDateTime.now().getYear();

        if (bookEntity.getYear() > currentYear || bookEntity.getYear() < 1800) {
            throw new ExceptionService("Book year can't be lower than 1800.");
        }

        if (bookEntity.getBookCopies() < 0) {
            throw new ExceptionService("Book copies can't be less than 0.");
        }

        if(bookEntity.getBorrowedBooks() < 0){
            throw new ExceptionService("Book borrowed can't be less than 0.");
        }

        if(bookEntity.getBooksRemaining() < 0){
            throw new ExceptionService("Books remaining can't be less than 0.");
        }
        if(bookEntity.getAuthor() == null){
            throw new ExceptionService("Author can't be null.");
        }
    }

}
