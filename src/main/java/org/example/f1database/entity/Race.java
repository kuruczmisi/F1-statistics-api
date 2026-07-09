package org.example.f1database.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "races")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Race {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String location;

    @Column(name = "race_year")
    private int year;

    @OneToMany(mappedBy = "race")
    @JsonIgnore
    private List<Result> results = new ArrayList<>();

    @ManyToMany
    @JoinTable(
            name = "driver_races",
            joinColumns = @JoinColumn(name = "race_id"),
            inverseJoinColumns = @JoinColumn(name = "driver_id")
    )
    @JsonIgnore
    private List<Driver> drivers = new ArrayList<>();
}