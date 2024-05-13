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
    @Column(name = "exerciseName", nullable = false)
    private String exerciseName;
    @Column(name = "duration")
    private Long duration;
    @Column(name = "description")
    private String description;
    @Column(name="feelings")
    private String feelings;


    @JsonIgnore
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "exercices_exerciceCollection",
            joinColumns = @JoinColumn(name = "collectionExercice_id"),
            inverseJoinColumns = @JoinColumn(name = "exercices_id")
    )
    private List<CollectionExercices> collectionExercices = new ArrayList<>();

    @ManyToMany(mappedBy = "exercices", cascade = CascadeType.ALL)
    List<Material> material= new ArrayList<>();

    @ManyToMany(mappedBy = "exercices", cascade = CascadeType.ALL)
    List<MuscleGroup> MuscleGroup= new ArrayList<>();

    @OneToMany(mappedBy="exercices",cascade = CascadeType.ALL )
    private List<ExerciceLog> exerciceLog = new ArrayList<>();

}
