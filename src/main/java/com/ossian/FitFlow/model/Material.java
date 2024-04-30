package com.ossian.FitFlow.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@Entity
@Table(name="material")
public class Material {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "materialName", nullable = false)
    private String materialName;
    @Column(name = "avalibilityStatus")
    private boolean avalibilityStatus;
    @Column(name = "lastMaintence")
    private Date lastMaintence;

    @JsonIgnore
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "material_exercices",
            joinColumns = @JoinColumn(name = "exercice_id"),
            inverseJoinColumns = @JoinColumn(name = "material_id")
    )
    private List<Exercices> exercices = new ArrayList<>();
}
