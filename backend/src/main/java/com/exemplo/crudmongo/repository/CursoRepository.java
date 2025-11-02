package com.exemplo.crudmongo.repository;

import com.exemplo.crudmongo.model.Curso;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CursoRepository extends JpaRepository<Curso, Long> {
}
