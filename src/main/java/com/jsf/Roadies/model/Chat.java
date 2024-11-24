package com.jsf.Roadies.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
    private LocalDateTime sentAt;

    @ManyToOne
    @JoinColumn(name = "user_squad_id")
    private UserSquad userSquad;

    @Column(name = "sender_id", insertable = false, updatable = false)
    private Long senderId;
    @Column(name = "group_id", insertable = false, updatable = false)
    private Long squadId;
}
