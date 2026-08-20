package com.curso.diccionarios.bd.repository;

import com.curso.diccionarios.bd.entity.DiccionarioEnBD;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface DiccionarioRepository extends JpaRepository<DiccionarioEnBD, Integer> {

    // Método para comprobar si existe un diccionario con el mismo idioma, ignorando mayúsculas y minúsculas  
    public boolean existsByIdiomaIgnoringCase(String idioma); 

    public Optional<DiccionarioEnBD> findByIdiomaIgnoringCase(String idioma);

}
