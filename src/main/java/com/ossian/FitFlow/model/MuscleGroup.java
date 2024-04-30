package com.ossian.FitFlow.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name="muscleGroup")
public class MuscleGroup {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "groupName")
    private String groupName;
    @Column(name = "muscleImage")
    private String muscleImage;
    @Column(name = "bodyPart")
    private String bodyPart;

    @JsonIgnore
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "muscleGroup_exercices",
            joinColumns = @JoinColumn(name = "exercice_id"),
            inverseJoinColumns = @JoinColumn(name = "muscleGroup_id")
    )
    private List<Exercices> exercices = new ArrayList<>();

}
