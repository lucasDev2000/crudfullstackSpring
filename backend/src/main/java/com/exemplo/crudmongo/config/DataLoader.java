package com.exemplo.crudmongo.config;

import com.exemplo.crudmongo.model.Pessoa; 
import com.exemplo.crudmongo.model.Curso; 
import com.exemplo.crudmongo.repository.PessoaRepository;
import com.exemplo.crudmongo.repository.CursoRepository; 
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.github.javafaker.Faker;

import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.Random;

@Configuration
public class DataLoader {

    @Bean
    CommandLineRunner loadDatabase(PessoaRepository pessoaRepo, CursoRepository cursoRepo) {
        return args -> {
            // Só popula se AMBOS estiverem vazios
            if (pessoaRepo.count() == 0 && cursoRepo.count() == 0) {
                Faker faker = new Faker(new Locale("pt-BR"));
                Random random = new Random();

                // 1. Criar Cursos
                System.out.println("ℹ️ Criando Cursos...");
                Curso cursoJava = cursoRepo.save(new Curso("Java Completo 2025", 80, true));
                Curso cursoSpring = cursoRepo.save(new Curso("Spring Boot e Microsserviços", 60, true));
                Curso cursoReact = cursoRepo.save(new Curso("React com Next.js", 40, true));
                List<Curso> cursos = List.of(cursoJava, cursoSpring, cursoReact);

                // 2. Criar Pessoas e associar Cursos
                System.out.println("ℹ️ Criando Pessoas e associando Cursos...");
                for (int i = 0; i < 200; i++) {
                    Pessoa pessoa = new Pessoa();
                    pessoa.setNome(faker.name().fullName());
                    pessoa.setIdade(faker.number().numberBetween(18, 70));
                    
                    // Lógica para associar 1 ou 2 cursos aleatórios
                    int numCursos = random.nextInt(2) + 1; // 1 ou 2 cursos
                    if (numCursos == 1) {
                        pessoa.getCursos().add(cursos.get(random.nextInt(cursos.size())));
                    } else {
                        pessoa.getCursos().add(cursos.get(0)); // Garante pelo menos 2 cursos diferentes
                        pessoa.getCursos().add(cursos.get(1 + random.nextInt(cursos.size() - 1)));
                    }
                    
                    pessoaRepo.save(pessoa);
                }

                System.out.println("✅ Banco populado com 200 Pessoas e 3 Cursos!");
            } else {
                System.out.println("ℹ️ Banco já contém dados, não foi necessário repopular.");
            }
        };
    }
}

