package com.jsf.Roadies.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(
        indexes = {
                @Index(name = "idx_user_time", columnList = "user_id, time")
        }
)
public class Location {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)

    private User user;

    @CreationTimestamp
    private LocalDateTime time;

    private Double latitude;
    private Double longitude;
}
