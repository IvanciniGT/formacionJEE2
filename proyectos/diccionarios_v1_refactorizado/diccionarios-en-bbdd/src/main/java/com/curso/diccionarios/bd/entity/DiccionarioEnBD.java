package com.curso.diccionarios.bd.entity;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "diccionarios")
public class DiccionarioEnBD {

    @Id // Clave primaria de la tabla
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Autogenerada
    private Integer id;

    // Formar un tamaño máximo:10 caracteres
    @Column(name = "idioma", nullable = false, unique = true, length = 10) // Constrain de tipo UNIQUE y NOT NULL, tamaño máximo 50 caracteres
    private String idioma;

    @OneToMany(mappedBy = "diccionario") // La palabra diccionario hace referencia a la propiedad diccionario de la clase PalabraEnBD: private DiccionarioEnBD diccionario
    private List<PalabraEnBD> palabras;

}
