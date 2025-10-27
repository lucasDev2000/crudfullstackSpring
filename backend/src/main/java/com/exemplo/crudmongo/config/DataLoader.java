package com.exemplo.crudmongo.config;

import com.exemplo.crudmongo.Model.Curso;
import com.exemplo.crudmongo.repository.CursoRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.github.javafaker.Faker;

import java.util.Locale;
import java.util.Random;

@Configuration
public class DataLoader {

    @Bean
    CommandLineRunner loadDatabase(CursoRepository repository) {
        return args -> {
            if (repository.count() == 0) {
                Faker faker = new Faker(new Locale("pt-BR"));
                Random random = new Random();

                for (int i = 0; i < 300; i++) {
                    Curso curso = new Curso();
                    curso.setNome(faker.educator().course());
                    curso.setCargaHoraria(faker.number().numberBetween(90, 500));
                    curso.setAtivo(random.nextBoolean()); // ✅ agora é boolean
                    repository.save(curso);
                }

                System.out.println("✅ Banco populado com 300 registros de cursos!");
            } else {
                System.out.println("ℹ️ Banco já contém dados, não foi necessário repopular.");
            }
        };
    }
}
