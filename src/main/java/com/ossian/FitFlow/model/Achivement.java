package com.ossian.FitFlow.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name="achivements")
public class Achivement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "achivementName", nullable = false)
    private String achivementName;
    @Column(name = "achivementDescription")
    private String achivementDescription;
    @Column(name = "achivementURL")
    private String achivementURL;

    @JsonIgnore
    @ManyToMany(mappedBy = "achivement")
    private List<User> user = new ArrayList<>();


}
