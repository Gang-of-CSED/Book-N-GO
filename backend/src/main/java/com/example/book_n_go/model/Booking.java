package com.example.book_n_go.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.example.book_n_go.enums.Status;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "bookings")
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    // @JoinColumn(name = "hallId", referencedColumnName = "id", insertable = false, updatable = false)
    private Hall hall;

    @ManyToOne
    // @JoinColumn(name = "userId", referencedColumnName = "id", insertable = false, updatable = false)
    private User user;

    @ManyToOne
    // @JoinColumn(name = "workspaceId", referencedColumnName = "id", insertable = false, updatable = false)
    private Workspace workspace;

    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private LocalDate date;

    @Enumerated(EnumType.STRING)
    private Status status;
    
    private double totalCost;

}
