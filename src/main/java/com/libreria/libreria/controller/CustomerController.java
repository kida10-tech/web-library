package com.libreria.libreria.controller;

import com.libreria.libreria.entity.AuthorEntity;
import com.libreria.libreria.entity.BookEntity;
import com.libreria.libreria.entity.CustomerEntity;
import com.libreria.libreria.exception.ExceptionService;
import com.libreria.libreria.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.support.RequestContextUtils;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@RequestMapping("customers")
@Controller
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @GetMapping("")
    public ModelAndView list(HttpServletRequest request) throws ExceptionService {
        ModelAndView mav = new ModelAndView("customer");
        List<CustomerEntity> customers = customerService.show();
        Map<String, ?> flashMap = RequestContextUtils.getInputFlashMap(request);
        if(flashMap != null) {
            mav.addObject("success",flashMap.get("Success"));
            mav.addObject("error",flashMap.get("Error"));
        }
        mav.addObject("customers", customers);
        return mav;
    }

    @PostMapping("/save")
    public RedirectView save(@RequestParam Long dni, @RequestParam String name, @RequestParam String lastName, @RequestParam String phoneNumber, RedirectAttributes attr) throws ExceptionService {
        try {
            customerService.signUp(dni, name, lastName, phoneNumber);
            attr.addFlashAttribute("Success", "Customer was added successfully.");
        } catch (Exception e) {
            attr.addFlashAttribute("Error", e.getMessage());
        }
        return new RedirectView("/customers");
    }

    @GetMapping("/create")
    public ModelAndView create() throws ExceptionService {
        ModelAndView mav = new ModelAndView("customer-form");
        mav.addObject("customer", new CustomerEntity());
        mav.addObject("title", "create-customer");
        mav.addObject("action", "save");

        return mav;
    }

    @GetMapping("/modify/{id}")
    public ModelAndView modify(@PathVariable Long id) throws ExceptionService {
        ModelAndView mav = new ModelAndView("customer-form");
        mav.addObject("customer", customerService.findById(id));
        mav.addObject("title", "update-customer");
        mav.addObject("action", "update");

        return mav;
    }

    @GetMapping("/subscribe/{id}")
    public RedirectView subscribe(@PathVariable Long id, RedirectAttributes attr) {
        try {
            this.customerService.enableCustomer(id);
            attr.addFlashAttribute("Success", "Customer was subscribed successfully.");
        } catch (Exception e) {
            attr.addFlashAttribute("Error", e.getMessage());
        }
        return new RedirectView("/customers");
    }

    @GetMapping("/unsubscribe/{id}")
    public RedirectView unsubscribe(@PathVariable Long id, RedirectAttributes attr) {
        try {
            this.customerService.disableCustomer(id);
            attr.addFlashAttribute("Success", "Customer was unsubscribed successfully.");
        } catch (Exception e) {
            attr.addFlashAttribute("Error", e.getMessage());
        }
        return new RedirectView("/customers");
    }

    @GetMapping("/delete/{id}")
    public RedirectView delete(@PathVariable Long id, RedirectAttributes attr) throws ExceptionService {
        try {
            this.customerService.delete(id);
            attr.addFlashAttribute("Success", "Customer was deleted successfully.");
        } catch (Exception e) {
            attr.addFlashAttribute("Error", e.getMessage());
        }
        return new RedirectView("/customers");
    }

    @PostMapping("/update")
    public RedirectView update(@RequestParam Long id, @RequestParam String name, @RequestParam String lastName, @RequestParam String phoneNumber, RedirectAttributes attr) throws ExceptionService {
        try {
            customerService.modify(id, name, lastName, phoneNumber);
            attr.addFlashAttribute("Success", "Book was updated successfully.");
        } catch (Exception e) {
            attr.addFlashAttribute("Error", e.getMessage());
        }
        return new RedirectView("/customers");
    }
}
