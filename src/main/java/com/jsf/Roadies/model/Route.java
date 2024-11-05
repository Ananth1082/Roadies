package com.jsf.Roadies.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Route {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Changed to IDENTITY for Long type
    private Long routeId;
    private String routeName;
    private String routeDescription;

    @ManyToOne
    @JoinColumn(name = "squad_id")
    private Squad squad;
}
