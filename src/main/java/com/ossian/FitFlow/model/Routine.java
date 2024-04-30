package com.ossian.FitFlow.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@Entity
@Table(name="routine")
public class Routine {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "description", nullable = false)
    private String description;
    @Column(name = "category")
    private String category;
    @Column(name = "status")
    private boolean status;
    @Column(name = "startDate")
    private Date start;
    @Column(name = "endDate")
    private Date end;
    @Column(name = "coachsComments")
    private String coachsComments;

    @JsonIgnore
    @ManyToMany(mappedBy = "routinesCreated",cascade = CascadeType.ALL)
    private List<User> userCreated = new ArrayList<>();

    @JsonIgnore
    @ManyToMany(mappedBy = "routinesAssociated",cascade = CascadeType.ALL)
    private List<User> userAdded = new ArrayList<>();

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "rutinas_colleciones",
            joinColumns = @JoinColumn(name = "collectionsExercices_id"),
            inverseJoinColumns = @JoinColumn(name = "routine_id")
    )
    private List<CollectionExercices> exercicesCollection = new ArrayList<>();

}
