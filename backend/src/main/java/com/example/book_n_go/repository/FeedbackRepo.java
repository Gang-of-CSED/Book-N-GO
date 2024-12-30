package com.example.book_n_go.repository;

import com.example.book_n_go.model.Feedback;
import com.example.book_n_go.model.User;
import com.example.book_n_go.model.Hall;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FeedbackRepo extends JpaRepository<Feedback, Long> {
    Optional<Feedback> findByUserAndHall(User user, Hall hall);
    List<Feedback> findAll();
    List<Feedback> findByHallId(Long hallId);
}