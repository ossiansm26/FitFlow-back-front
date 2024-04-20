package com.ossian.FitFlow.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name="collectionExercices")
public class CollectionExercices {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "nameCollection", nullable = false)
    private String nameCollection;
    @Column(name = "difficultyLevel", nullable = false)
    private int difficultyLevel;
    @Column(name = "totalExercices", nullable = false)
    private int totalExercices;
    @Column(name = "urlExplanatoryVideo")
    private String urlExplanatoryVideo;

    @JsonIgnore
    @ManyToMany(mappedBy = "exercicesCollection")
    List<Routine> routine= new ArrayList<>();

    @ManyToMany(mappedBy = "collectionExercices")
    List<Exercices> exercices= new ArrayList<>();

}
