package com.example.book_n_go.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "Workspaces")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class Workspace {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @Column(nullable = false)
    private int providerId;
    @Column(nullable = false)
    private int locationId;
    @ManyToOne
    @JoinColumn(name = "locationId", referencedColumnName = "id", insertable = false, updatable = false)
    private Location location;
}
