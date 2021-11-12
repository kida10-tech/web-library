package com.libreria.libreria.controller;

import com.libreria.libreria.entity.BookEntity;
import com.libreria.libreria.exception.ExceptionService;
import com.libreria.libreria.repository.AuthorRepository;
import com.libreria.libreria.repository.EditorialRepository;
import com.libreria.libreria.service.AuthorService;
import com.libreria.libreria.service.BookService;
import com.libreria.libreria.service.EditorialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.support.RequestContextUtils;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@RequestMapping("books")
@Controller
public class BookController {

    @Autowired
    private BookService bookService;
    @Autowired
    private AuthorRepository authorRepository;
    @Autowired
    private EditorialRepository editorialRepository;
    @Autowired
    private EditorialService editorialService;
    @Autowired
    private AuthorService authorService;

    @GetMapping("")
    public ModelAndView list(HttpServletRequest request) throws ExceptionService {
        ModelAndView mav = new ModelAndView("book");
        List<BookEntity> books = bookService.show();
        Map<String, ?> flashMap = RequestContextUtils.getInputFlashMap(request);
        if(flashMap != null) {
            mav.addObject("success",flashMap.get("Success"));
            mav.addObject("error",flashMap.get("Error"));
        }
        mav.addObject("books", books);
        return mav;
    }

    @GetMapping("/create")
    public ModelAndView create() throws ExceptionService {
        ModelAndView mav = new ModelAndView("book-form");
        mav.addObject("book", new BookEntity());
        mav.addObject("title", "create-book");
        mav.addObject("action", "save");
        mav.addObject("authors", authorService.show());
        mav.addObject("editorials", editorialService.show());

        return mav;
    }

    @PostMapping("/save")
    public RedirectView save(@RequestParam Long isbn, @RequestParam String title, @RequestParam Integer year, @RequestParam Integer bookCopies, @RequestParam Integer borrowedBooks, @RequestParam Integer booksRemaining, @RequestParam Long author, @RequestParam Long editorial, RedirectAttributes attr) throws ExceptionService {
        try {
            BookEntity book = new BookEntity();
            book.setIsbn(isbn);
            book.setTitle(title);
            book.setYear(year);
            book.setBookCopies(bookCopies);
            book.setBorrowedBooks(borrowedBooks);
            book.setBooksRemaining(booksRemaining);
            book.setAuthor(authorRepository.findById(author).get());
            book.setEditorial(editorialRepository.findById(editorial).get());
            bookService.add(book);

            attr.addFlashAttribute("Success", "Book was added successfully.");
        } catch (Exception e) {
            attr.addFlashAttribute("Error", e.getMessage());
            return new RedirectView("/books");
        }
        return new RedirectView("/books");
    }

    @GetMapping("/subscribe/{isbn}")
    public RedirectView subscribe(@PathVariable Long isbn, RedirectAttributes attr) {
        try {
            this.bookService.subscribe(isbn);
            attr.addFlashAttribute("Success", "Book was subscribed successfully.");
        } catch (Exception e) {
            attr.addFlashAttribute("Error", e.getMessage());
        }
        return new RedirectView("/books");
    }

    @GetMapping("/unsubscribe/{isbn}")
    public RedirectView unsubscribe(@PathVariable Long isbn, RedirectAttributes attr) {
        try {
            this.bookService.unsubscribe(isbn);
            attr.addFlashAttribute("Success", "Book was unsubscribed successfully.");
        } catch (Exception e) {
            attr.addFlashAttribute("Error", e.getMessage());
        }
        return new RedirectView("/books");
    }

    @GetMapping("/modify/{isbn}")
    public ModelAndView modify(@PathVariable Long isbn) throws ExceptionService {
        ModelAndView mav = new ModelAndView("book-form");
        mav.addObject("book", bookService.findById(isbn));
        mav.addObject("title", "update-book");
        mav.addObject("action", "update");
        mav.addObject("authors", authorService.show());
        mav.addObject("editorials", editorialService.show());

        return mav;
    }

    @PostMapping("/update")
    public RedirectView update(@RequestParam Long isbn, @RequestParam String title, @RequestParam Integer year, @RequestParam Integer bookCopies, @RequestParam Integer borrowedBooks, @RequestParam Integer booksRemaining, @RequestParam Long author, @RequestParam Long editorial, RedirectAttributes attr) throws ExceptionService {
        try {
            bookService.modify(isbn, title, year, bookCopies, borrowedBooks, booksRemaining, author, editorial);
            attr.addFlashAttribute("Success", "Book was updated successfully.");
        } catch (Exception e) {
            attr.addFlashAttribute("Error", e.getMessage());
        }
        return new RedirectView("/books");
    }

    @GetMapping("/delete/{isbn}")
    public RedirectView delete(@PathVariable Long isbn, RedirectAttributes attr) throws ExceptionService {
        try {
            this.bookService.delete(isbn);
            attr.addFlashAttribute("Success", "Book was deleted successfully.");
        } catch (Exception e) {
            attr.addFlashAttribute("Error", e.getMessage());
        }
        return new RedirectView("/books");
    }

}
