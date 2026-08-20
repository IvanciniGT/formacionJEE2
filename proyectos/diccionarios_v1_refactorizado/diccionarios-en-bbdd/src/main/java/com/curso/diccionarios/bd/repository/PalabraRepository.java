package com.curso.diccionarios.bd.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

import com.curso.diccionarios.bd.entity.PalabraEnBD;

public interface PalabraRepository extends JpaRepository<PalabraEnBD, Integer> {

    // Necesito recuperar una palabra por su texto y nombre del idioma (ignorando mayúsculas y minúsculas)
    public Optional<PalabraEnBD> findByPalabraIgnoringCaseAndDiccionario_IdiomaIgnoringCase(String palabra, String idioma);

}
