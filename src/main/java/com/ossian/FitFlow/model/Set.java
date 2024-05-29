package com.ossian.FitFlow.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name="sets")
public class Set {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "weight")
    private Double weight;

    @Column(name = "reps")
    private Integer reps;
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "exercice_log_id")
    private ExerciceLog exerciceLog;
}