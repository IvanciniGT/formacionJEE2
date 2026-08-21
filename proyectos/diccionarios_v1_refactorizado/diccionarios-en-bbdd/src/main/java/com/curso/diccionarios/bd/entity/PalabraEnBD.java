package com.curso.diccionarios.bd.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "palabras",
       uniqueConstraints = {@UniqueConstraint(columnNames = {"palabra", "diccionario_id"})})
public class PalabraEnBD {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "palabra", nullable = false,length = 50) // Aqui no es unique.
    // Es la combinación de palabra + diccionario la que tiene que ser única.
    private String palabra;

    // tipo de join: ManyToOne o OneToMany. 
    // En este caso es ManyToOne porque muchas palabras pueden estar en un mismo diccionario.
    @ManyToOne
    @JoinColumn(name = "diccionario_id", nullable = false) // Esta columna se usa como parte de una relación
    private DiccionarioEnBD diccionario;

    @OneToMany(mappedBy = "palabra", cascade = CascadeType.ALL) // La palabra hace referencia a la propiedad palabra de la clase SignificadoEnBD: private PalabraEnBD palabra
    // El cascade ALL significa que:
    //  si borramos una palabra, se borrarán todos sus significados asociados.
    // Pero también.. y más importante.. si añadimos(guardamos) una palabra, se añadirán todos sus significados asociados.
    private List<SignificadoEnBD> significados;

}
