package com.jsf.Roadies.model;

import com.jsf.Roadies.enums.GroupRoleType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class UserSquad {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "squad_id")
    private Squad squad;

    @Enumerated(EnumType.STRING)
    private GroupRoleType role;
}
