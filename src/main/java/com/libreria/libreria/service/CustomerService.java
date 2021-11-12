package com.libreria.libreria.service;

import com.libreria.libreria.entity.CustomerEntity;
import com.libreria.libreria.exception.ExceptionService;
import com.libreria.libreria.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.w3c.dom.stylesheets.LinkStyle;

import java.util.List;
import java.util.Optional;

@Service
public class CustomerService {

    @Autowired //La var la inicializa el servidor de aplicaciones
    private CustomerRepository customerRepository;

    @Transactional
    public void signUp(Long dni, String name, String lastName, String phoneNumber, Boolean isActive) throws ExceptionService {

        validate(dni, name, lastName, phoneNumber);

        CustomerEntity customer = new CustomerEntity();
        customer.setDni(dni);
        customer.setName(name);
        customer.setLastName(lastName);
        customer.setPhoneNumber(phoneNumber);

        customerRepository.save(customer);
    }

    @Transactional
    public void modify(Long id, Long dni, String name, String lastName, String phoneNumber) throws ExceptionService {

        validate(dni, name, lastName, phoneNumber);

        //Optional: puedo ver si como respuesta al ID me devuelve un usuario, entonces lo busco y modifico, sino me devuelve una excepcion
        Optional<CustomerEntity> response = customerRepository.findById(id);

        if(response.isPresent()) {
            CustomerEntity customer = response.get();
            customer.setName(name);
            customer.setLastName(lastName);
            customer.setPhoneNumber(phoneNumber);

            customerRepository.save(customer);
        } else throw new ExceptionService("Customer does not exist.");
    }

    @Transactional
    public void disableCustomer(Long id) throws ExceptionService {
        Optional<CustomerEntity> response = customerRepository.findById(id);

        if(response.isPresent()) {
            CustomerEntity customer = response.get();
            customer.setIsActive(false);

            customerRepository.save(customer);
        } else throw new ExceptionService("User does not exist.");
    }

    @Transactional
    public void enableCustomer(Long id) throws ExceptionService {
        Optional<CustomerEntity> response = customerRepository.findById(id);

        if(response.isPresent()) {
            CustomerEntity customer = response.get();
            customer.setIsActive(true);

            customerRepository.save(customer);
        } else throw new ExceptionService("User does not exist.");
    }

    public List<CustomerEntity> show() {
         return customerRepository.findAll();
    }

    public void validate(Long dni, String name, String lastName, String phoneNumber) throws ExceptionService {
        if(name == null || name.isEmpty()) {
            throw new ExceptionService("Customer name can not be null.");
        }
        if(lastName == null || lastName.isEmpty()) {
            throw new ExceptionService("Lastname can not be null.");
        }
        if(dni == null || dni == 0) {
            throw new ExceptionService("DNI can not be null.");
        }
        if(phoneNumber == null || phoneNumber.length() < 7) {
            throw new ExceptionService("Phone number can not be null and must contain at least 7 digits.");
        }
    }

//    @Override
//    public UserDetails loadUserByUsername(String mail) throws UsernameNotFoundException {
//        UserEntity user = userRepository.findByEmail(mail);
//        if(user != null) {
//
//            List<GrantedAuthority> authorities = new ArrayList<>();
//
//            GrantedAuthority p1 = new SimpleGrantedAuthority("PHOTO-MODULE");
//            authorities.add(p1);
//            GrantedAuthority p2 = new SimpleGrantedAuthority("PET-MODULE");
//            authorities.add(p2);
//            GrantedAuthority p3 = new SimpleGrantedAuthority("VOTE-MODULE");
//            authorities.add(p3);
//
//            User u = new User(user.getMail(), user.getPass(), authorities);
//            return u;
//        }
//        return null;
//    }
}
