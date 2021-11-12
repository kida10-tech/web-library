package com.libreria.libreria.controller;

import com.libreria.libreria.entity.AuthorEntity;
import com.libreria.libreria.entity.EditorialEntity;
import com.libreria.libreria.exception.ExceptionService;
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

@Controller
@RequestMapping("editorials")
public class EditorialController {

    @Autowired
    private EditorialService editorialService;

    @GetMapping("")
    public ModelAndView list(HttpServletRequest request) throws ExceptionService {
        ModelAndView mav = new ModelAndView("editorial");
        //List<EditorialEntity> editorials = editorialService.show();
        Map<String, ?> flashMap = RequestContextUtils.getInputFlashMap(request);
        if(flashMap != null) {
            mav.addObject("success",flashMap.get("Success"));
            mav.addObject("error",flashMap.get("Error"));
        }
        mav.addObject("editorials", editorialService.show());
        return mav;
    }

    @GetMapping("/create")
    public ModelAndView create() throws ExceptionService {
        ModelAndView mav = new ModelAndView("editorial-form");
        mav.addObject("editorial", new EditorialEntity());
        mav.addObject("name", "create-editorial");
        mav.addObject("action", "save");

        return mav;
    }

    @GetMapping("/modify/{id}")
    public ModelAndView modify(@PathVariable Long id) throws ExceptionService {
        ModelAndView mav = new ModelAndView("editorial-form");
        mav.addObject("editorial", editorialService.findById(id));
        mav.addObject("name", "update-editorial");
        mav.addObject("action", "update");

        return mav;
    }

    @GetMapping("/delete/{id}")
    public RedirectView delete(@PathVariable Long id, RedirectAttributes attr) throws ExceptionService {
        try {
            this.editorialService.delete(id);
            attr.addFlashAttribute("Success", "Editorial was deleted successfully.");
        } catch (Exception e) {
            attr.addFlashAttribute("Error", e.getMessage());
        }
        return new RedirectView("/editorials");
    }

    @PostMapping("/save")
    public RedirectView save(@RequestParam String name, RedirectAttributes attr) throws ExceptionService {
        try {
            EditorialEntity editorial = new EditorialEntity();
            editorial.setName(name);
            editorialService.add(editorial);
            attr.addFlashAttribute("Success", "Editorial was added successfully.");
        } catch (Exception e) {
            attr.addFlashAttribute("Error", e.getMessage());
        }
        return new RedirectView("/editorials");
    }

    @PostMapping("/update")
    public RedirectView update(@RequestParam Long id, @RequestParam String name, RedirectAttributes attr) throws ExceptionService {
        try {
            editorialService.modify(id, name);
            attr.addFlashAttribute("Success", "Editorial was updated successfully.");
        } catch (Exception e) {
            attr.addFlashAttribute("Error", e.getMessage());
        }
        return new RedirectView("/editorials");
    }
}
