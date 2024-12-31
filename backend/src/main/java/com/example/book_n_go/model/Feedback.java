package com.example.book_n_go.model;
import com.example.book_n_go.events.FeedbackListener;
import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;


@Entity
@Table(uniqueConstraints = {@UniqueConstraint(columnNames = {"user_id", "hall_id"})})
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@EntityListeners(FeedbackListener.class)
public class Feedback {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    // @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    // @JoinColumn(name = "hall_id", nullable = false)
    @JsonBackReference
    private Hall hall;

    @NotNull
    @Min(0)
    @Max(5)
    private Double rating;

    @NotBlank
    private String content;
   
}