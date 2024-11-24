package com.jsf.Roadies.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@EntityListeners(AuditingEntityListener.class)
public class Squad {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;

    private String squadName;
    private String squadDescription;
    private int squadCapacity;
    private int squadRange;

    @CreationTimestamp
    private LocalDateTime createdAt;
    @UpdateTimestamp
    private LocalDateTime updatedAt;
    private Boolean isExpired=false;

    @OneToMany(mappedBy = "squad", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Route> routes;

    @OneToMany(mappedBy = "squad", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<UserSquad> userSquads;
}
