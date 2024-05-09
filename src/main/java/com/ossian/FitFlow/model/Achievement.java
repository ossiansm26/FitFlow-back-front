package com.ossian.FitFlow.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name="achievements")
public class Achievement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "achievementName", nullable = false)
    private String achievementName;
    @Column(name = "achievementDescription")
    private String achievementDescription;
    @Column(name = "achievementURL")
    private String achievementURL;

    @JsonIgnore
    @ManyToMany(mappedBy = "achievement", cascade = CascadeType.ALL)
    private List<User> user = new ArrayList<>();


}
