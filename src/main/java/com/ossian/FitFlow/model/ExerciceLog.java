package com.ossian.FitFlow.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
@Entity
@Table(name="exerciceLog")
public class ExerciceLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "date")
    private Date date;
    @Column(name = "reps")
    private Long reps;
    @Column(name = "weight")
    private Long weight;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "exercice_id", nullable = false)
    private Exercices exercices;


}
