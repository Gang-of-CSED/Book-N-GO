package com.example.book_n_go.repository;

import com.example.book_n_go.enums.Day;
import com.example.book_n_go.model.Workday;

import org.hibernate.boot.registry.classloading.spi.ClassLoaderService.Work;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

// import java.time.DayOfWeek;
import java.util.List;
// import java.util.Set;


@Repository
public interface WorkdayRepo extends JpaRepository<Workday, Long> {
    List<Workday> findByWorkspaceId(Long workspaceId);
    Workday findByWorkspaceIdAndWeekDay(Long workspaceId, Day weekDay);
}
