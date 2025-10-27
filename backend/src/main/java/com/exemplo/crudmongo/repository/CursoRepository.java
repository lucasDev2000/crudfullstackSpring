package com.exemplo.crudmongo.repository;

import com.exemplo.crudmongo.Model.Curso;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
//import org.springframework.data.mongodb.repository.MongoRepository; remover esse código
import org.springframework.stereotype.Repository;

@Repository
public interface CursoRepository extends JpaRepository<Curso, Long> {

    @Query("SELECT c FROM Curso c WHERE " +
           "(:nome IS NULL OR LOWER(c.nome) LIKE LOWER(CONCAT('%', :nome, '%'))) AND " +
           "(:cargaHoraria = 0 OR c.cargaHoraria = :cargaHoraria) AND " +
           "(:ativo IS NULL OR c.ativo = :ativo)")
    Page<Curso> buscarPorFiltros(@Param("nome") String nome,
                                 @Param("cargaHoraria") int cargaHoraria,
                                 @Param("ativo") boolean ativo,
                                 Pageable pageable);
}


