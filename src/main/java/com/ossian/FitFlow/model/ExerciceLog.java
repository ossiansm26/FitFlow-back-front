package com.ossian.FitFlow.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
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

    @ManyToOne
    @JoinColumn(name = "exercice_id", nullable = false)
    private Exercices exercice;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @OneToMany(mappedBy="exerciceLog", cascade = CascadeType.ALL)
    private List<Set> sets = new ArrayList<>();


}
