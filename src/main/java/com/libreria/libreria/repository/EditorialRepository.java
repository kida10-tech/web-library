package com.libreria.libreria.repository;

import com.libreria.libreria.entity.EditorialEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EditorialRepository extends JpaRepository<EditorialEntity, Long> {
}
