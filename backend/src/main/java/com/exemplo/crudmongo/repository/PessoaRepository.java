package com.exemplo.crudmongo.repository;

import com.exemplo.crudmongo.model.Pessoa; 
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@Repository
public interface PessoaRepository extends JpaRepository<Pessoa, Long> {

    List<Pessoa> findByNomeContainingIgnoreCase(String nome);
    List<Pessoa> findByIdade(Integer idade);

    // --- Critério 1 ---
    @Query("SELECT DISTINCT p FROM Pessoa p JOIN p.cursos c WHERE LOWER(c.nome) LIKE LOWER(CONCAT('%', :curso, '%'))")
    List<Pessoa> buscarPorNomeDeCurso(String curso);
 
    @Query("""
            SELECT DISTINCT p
            FROM Pessoa p
            LEFT JOIN p.cursos c
            WHERE (:nome IS NULL OR LOWER(p.nome) LIKE LOWER(CONCAT('%', :nome, '%')))
              AND (:curso IS NULL OR LOWER(c.nome) LIKE LOWER(CONCAT('%', :curso, '%')))
              AND (:idadeMin IS NULL OR p.idade >= :idadeMin)
              AND (:idadeMax IS NULL OR p.idade <= :idadeMax)
            """)
    Page<Pessoa> pesquisarComFiltros(String nome,
                                     String curso,
                                     Integer idadeMin,
                                     Integer idadeMax,
                                     Pageable pageable);
    
    @Query("SELECT COUNT(p) FROM Pessoa p")
    long contarTotalPessoas();

    @Query("""
            SELECT c.nome, COUNT(p)
            FROM Pessoa p
            JOIN p.cursos c
            GROUP BY c.nome
            """)
    List<Object[]> contarPorCurso();

    @Query("""
            SELECT c.nome, AVG(p.idade)
            FROM Pessoa p
            JOIN p.cursos c
            GROUP BY c.nome
            """)
    List<Object[]> mediaIdadePorCurso();
}