// UserLocation.java
package com.jsf.Roadies.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "user_location")
@EntityListeners(AuditingEntityListener.class)
public class UserLocation {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    private String userId;
    private double latitude;
    private double longitude;
    @CreationTimestamp
    private String timestamp;
}