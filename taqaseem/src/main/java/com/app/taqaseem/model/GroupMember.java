package com.app.taqaseem.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.persistence.*;

@Data
@NoArgsConstructor
@Entity
@Table(name = "GroupMember")
public class GroupMember {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "GroupID", nullable = false)
    private Group group;

    @ManyToOne
    @JoinColumn(name = "UserID", nullable = false)
    private User user;
}

