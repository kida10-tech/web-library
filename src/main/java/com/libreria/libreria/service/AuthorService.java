package com.libreria.libreria.service;

import com.libreria.libreria.entity.AuthorEntity;
import com.libreria.libreria.exception.ExceptionService;
import com.libreria.libreria.repository.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class AuthorService implements GeneralService<AuthorEntity> {
    @Autowired
    private AuthorRepository authorRepository;

    @Transactional
    public void add(AuthorEntity authorEntity) throws ExceptionService {
        validate(authorEntity);
        authorRepository.save(authorEntity);
    }

    @Transactional
    public void modify(Long id, String name) throws ExceptionService {
        if (name == null || name.isEmpty()) {
            throw new ExceptionService("Name can not be null.");
        }
        Optional<AuthorEntity> response = authorRepository.findById(id);
        if (response.isPresent()) {
            AuthorEntity author = response.get();

            author.setName(name);
            authorRepository.save(author);
        }else{
            throw new ExceptionService("Author does not exist.");
        }
    }

    @Override
    @Transactional
    public void delete(Long id) throws ExceptionService {
        authorRepository.deleteById(id);
    }

    @Override
    public List<AuthorEntity> show() throws ExceptionService {
        return authorRepository.findAll();
    }

    @Override
    public Optional<AuthorEntity> findById(Long id) throws ExceptionService {
        return authorRepository.findById(id);
    }

    public void validate(AuthorEntity authorEntity) throws ExceptionService {
        if (authorEntity.getName() == null || authorEntity.getName().isEmpty()) {
            throw new ExceptionService("Name can't be null.");
        }
    }
}
