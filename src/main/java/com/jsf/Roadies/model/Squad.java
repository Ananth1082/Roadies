package com.jsf.Roadies.model;

import jakarta.persistence.*;
import lombok.*;


import java.sql.Timestamp;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Squad {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;

    private String squadName;
    private String squadDescription;
    private int squadCapacity;
    private int squadRange;

    private Timestamp createdAt;
    private Timestamp updatedAt;
    private Boolean isExpired;

    @OneToMany(mappedBy = "squad", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Route> routes;

    @OneToMany(mappedBy = "squad", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<UserSquad> userSquads;
}
