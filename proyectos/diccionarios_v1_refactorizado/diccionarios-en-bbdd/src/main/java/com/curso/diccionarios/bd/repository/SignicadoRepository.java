package com.curso.diccionarios.bd.repository;

import com.curso.diccionarios.bd.entity.SignificadoEnBD;

import org.springframework.data.jpa.repository.JpaRepository;


public interface SignicadoRepository extends JpaRepository<SignificadoEnBD, Integer> {
}
