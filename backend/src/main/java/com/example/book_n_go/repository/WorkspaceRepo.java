package com.example.book_n_go.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.book_n_go.model.Workspace;

@Repository
public interface WorkspaceRepo extends JpaRepository<Workspace, Long> {
//    Workspace findByHallId(Long hallId);
    List<Workspace> findByProviderId(Long providerId);
}
