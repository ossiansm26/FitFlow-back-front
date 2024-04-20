package com.ossian.FitFlow.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name="exercices")
public class Exercices {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "nameExercise", nullable = false)
    private String nameExercise;
    @Column(name = "duration", nullable = false)
    private int duration;
    @Column(name = "description")
    private String description;


    @JsonIgnore
    @ManyToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    @JoinTable(
            name = "exercices_exerciceCollection",
            joinColumns = @JoinColumn(name = "collectionExercice_id"),
            inverseJoinColumns = @JoinColumn(name = "exercices_id")
    )
    private List<CollectionExercices> collectionExercices = new ArrayList<>();


    @ManyToMany(mappedBy = "exercices")
    List<Material> material= new ArrayList<>();


    @ManyToMany(mappedBy = "exercices")
    List<MuscleGroup> MuscleGroup= new ArrayList<>();

}
