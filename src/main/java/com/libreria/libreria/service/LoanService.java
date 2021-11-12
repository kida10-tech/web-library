package com.libreria.libreria.service;

import com.libreria.libreria.entity.LoanEntity;
import com.libreria.libreria.exception.ExceptionService;
import com.libreria.libreria.repository.BookRepository;
import com.libreria.libreria.repository.CustomerRepository;
import com.libreria.libreria.repository.LoanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class LoanService {

    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private LoanRepository loanRepository;

    public void add(Date loanDate, Date returnDate, Long idBook, Long idCustomer) throws ExceptionService {
        validate(loanDate, returnDate);
        LoanEntity loanEntity = new LoanEntity();

        loanEntity.setLoanDate(loanDate);
        loanEntity.setReturnDate(returnDate);
        loanEntity.setBook(bookRepository.findById(idBook).get());
        loanEntity.setCustomer(customerRepository.findById(idCustomer).get());

        loanRepository.save(loanEntity);
    }

    public void modify(Long id, Date loanDate, Date returnDate, Long idBook, Long idCustomer) throws ExceptionService {

        validate(loanDate, returnDate);

        //Optional: puedo ver si como respuesta al ID me devuelve un usuario, entonces lo busco y modifico, sino me devuelve una excepcion
        Optional<LoanEntity> response = loanRepository.findById(id);

        if(response.isPresent()) {
            LoanEntity loanEntity = response.get();
            loanEntity.setLoanDate(loanDate);
            loanEntity.setReturnDate(returnDate);
            loanEntity.setBook(bookRepository.findById(idBook).get());
            loanEntity.setCustomer(customerRepository.findById(idBook).get());

            loanRepository.save(loanEntity);
        } else {
            throw new ExceptionService("Loan does not exist.");
        }
    }

    public void delete(Long id) {
        this.loanRepository.deleteById(id);
    }

    public List<LoanEntity> findAll() {
        return loanRepository.findAll();
    }

    public Optional<LoanEntity> findById(Long id) {
        return loanRepository.findById(id);
    }

    public void deleteLoan(Long id) throws ExceptionService{
        Optional<LoanEntity> response = loanRepository.findById(id);
        if(response.isPresent()){
            LoanEntity loanEntity = response.get();
            if(loanEntity.getReturnDate() == new Date()){
                delete(id);
            }
        }else{
            throw new ExceptionService("Loan does not exist.");
        }
    }

    public void validate(Date loanDate, Date returnDate) throws ExceptionService {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
        String dateLoan = sdf.format(loanDate);
        if(loanDate.before(new Date()) || dateLoan.length() < 10){
            throw new ExceptionService("Wrong date");
        }
        String dateReturn = sdf.format(loanDate);
        if(returnDate.before(loanDate) || dateReturn.length() < 10){
            throw new ExceptionService("Wrong date");
        }
    }
}
