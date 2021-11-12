package com.libreria.libreria.service;

import com.libreria.libreria.exception.ExceptionService;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface GeneralService <E extends Object>{

    @Transactional
    public void delete(Long id) throws ExceptionService;

    @Transactional(readOnly = true)
    public List<E> show() throws ExceptionService;

    @Transactional
    public Optional<E> findById(Long id) throws ExceptionService;
}
