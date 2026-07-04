package org.example.f1database.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "drivers")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Driver {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String nationality;
    private int number;

    @ManyToOne
    private Team team;

    @OneToMany(mappedBy = "driver")
    @JsonIgnore
    private List<Result> results;

    @ManyToMany
    @JoinTable(
            name = "driver_races",
            joinColumns = @JoinColumn(name = "driver_id"),
            inverseJoinColumns = @JoinColumn(name = "race_id")
    )
    @JsonIgnore
    private List<Race> races;
}