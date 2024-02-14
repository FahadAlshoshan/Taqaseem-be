package com.app.taqaseem.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@Entity
@Table(name = "GroupTable")
public class Group {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "GroupName", nullable = false)
    private String groupName;

    @Column(name = "CreatedAt", nullable = false)
    private LocalDateTime createdAt;
}

