package com.libreria.libreria.controller;

import com.libreria.libreria.entity.AuthorEntity;
import com.libreria.libreria.exception.ExceptionService;
import com.libreria.libreria.service.AuthorService;
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

@RequestMapping("authors")
@Controller
public class AuthorController {

    @Autowired
    private AuthorService authorService;

    @GetMapping("")
    public ModelAndView list(HttpServletRequest request) throws ExceptionService {
        ModelAndView mav = new ModelAndView("author");
        List<AuthorEntity> authors = authorService.show();
        Map<String, ?> flashMap = RequestContextUtils.getInputFlashMap(request);
        if(flashMap != null) {
            mav.addObject("success",flashMap.get("Success"));
            mav.addObject("error",flashMap.get("Error"));
        }
        mav.addObject("authors", authors);
        return mav;
    }

    @GetMapping("/create")
    public ModelAndView create() throws ExceptionService {
        ModelAndView mav = new ModelAndView("author-form");
        mav.addObject("author", new AuthorEntity());
        mav.addObject("title", "create-author");
        mav.addObject("action", "save");

        return mav;
    }

    @GetMapping("/modify/{id}")
    public ModelAndView modify(@PathVariable Long id) throws ExceptionService {
        ModelAndView mav = new ModelAndView("author-form");
        mav.addObject("author", authorService.findById(id));
        mav.addObject("title", "update-author");
        mav.addObject("action", "update");

        return mav;
    }

    @GetMapping("/delete/{id}")
    public RedirectView delete(@PathVariable Long id, RedirectAttributes attr) throws ExceptionService {
        try {
            this.authorService.delete(id);
            attr.addFlashAttribute("Success", "Author was deleted successfully.");
        } catch (Exception e) {
            attr.addFlashAttribute("Error", e.getMessage());
        }
        return new RedirectView("/authors");
    }

    @PostMapping("/save")
    public RedirectView save(@RequestParam String name, RedirectAttributes attr) throws ExceptionService {
        try {
            AuthorEntity author = new AuthorEntity();
            author.setName(name);
            authorService.add(author);
            attr.addFlashAttribute("Success", "Author was added successfully.");
        } catch (Exception e) {
            attr.addFlashAttribute("Error", e.getMessage());
        }
        return new RedirectView("/authors");
    }

    @PostMapping("/update")
    public RedirectView update(@RequestParam Long id, @RequestParam String name, RedirectAttributes attr) throws ExceptionService {
        try {
            authorService.modify(id, name);
            attr.addFlashAttribute("Success", "Author was updated successfully.");
        } catch (Exception e) {
            attr.addFlashAttribute("Error", e.getMessage());
        }
        return new RedirectView("/authors");
    }

}
