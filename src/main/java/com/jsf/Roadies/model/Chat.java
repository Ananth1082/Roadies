package com.jsf.Roadies.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Chat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String message;
    @CreationTimestamp
    private LocalDateTime sentAt;

    @ManyToOne
    @JoinColumn(name = "user_squad_id")
    private UserSquad userSquad;

    @ManyToOne
    @JoinColumn(name = "sender_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "group_id")
    private Squad squad;
}
