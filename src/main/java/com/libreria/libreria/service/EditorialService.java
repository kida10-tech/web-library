package com.libreria.libreria.service;

import com.libreria.libreria.entity.AuthorEntity;
import com.libreria.libreria.entity.EditorialEntity;
import com.libreria.libreria.exception.ExceptionService;
import com.libreria.libreria.repository.EditorialRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class EditorialService implements GeneralService<EditorialEntity> {

    @Autowired
    private EditorialRepository editorialRepository;

    @Transactional
    public void add(EditorialEntity editorialEntity) throws ExceptionService {
        validate(editorialEntity);
        editorialRepository.save(editorialEntity);
    }

    @Transactional
    public void modify(Long id, String name) throws ExceptionService {
        if (name == null || name.isEmpty()) {
            throw new ExceptionService("Name can not be null.");
        }
        Optional<EditorialEntity> response = editorialRepository.findById(id);
        if (response.isPresent()) {
            EditorialEntity editorial = response.get();

            editorial.setName(name);
            editorialRepository.save(editorial);
        }else{
            throw new ExceptionService("Editorial does not exist.");
        }
    }

    @Override
    public void delete(Long id) throws ExceptionService {
        editorialRepository.deleteById(id);
    }

    @Override
    public List<EditorialEntity> show() throws ExceptionService {
        return editorialRepository.findAll();
    }

    @Override
    public Optional<EditorialEntity> findById(Long id) throws ExceptionService {
        return editorialRepository.findById(id);
    }

    public void validate(EditorialEntity editorialEntity) throws ExceptionService {
        if (editorialEntity.getName() == null || editorialEntity.getName().isEmpty()) {
            throw new ExceptionService("Name can't be null.");
        }
    }
}
