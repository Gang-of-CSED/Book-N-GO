package com.example.book_n_go.model;

import java.sql.Time;

import com.example.book_n_go.enums.Day;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "Workdays")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class Workday {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @Column(nullable = false)
    private Time startTime;
    @Column(nullable = false)
    private Time endTime;
    @Column(nullable = false)
    private Day weekDay;
    
    @ManyToOne
    @JoinColumn(nullable = false)
    private Workspace workspace;
}
