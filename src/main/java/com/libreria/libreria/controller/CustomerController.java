package com.libreria.libreria.controller;

import com.libreria.libreria.entity.BookEntity;
import com.libreria.libreria.entity.CustomerEntity;
import com.libreria.libreria.exception.ExceptionService;
import com.libreria.libreria.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.support.RequestContextUtils;

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

    @GetMapping("/create")
    public ModelAndView create() throws ExceptionService {
        ModelAndView mav = new ModelAndView("customer-form");
        mav.addObject("customer", new CustomerEntity());
        mav.addObject("title", "create-customer");
        mav.addObject("action", "save");

        return mav;
    }
}
