package com.example.book_n_go.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.book_n_go.model.Booking;



@Repository
public interface BookingRepo extends JpaRepository<Booking, Long> {
    @Query("SELECT b FROM Booking b WHERE b.hall.id = :hallId" +
            " AND ((b.startTime BETWEEN :start AND :end) OR (b.endTime BETWEEN :start AND :end))")
    List<Booking> findConflictingBookings(  @Param("hallId") Long hallId, 
                                            @Param("start") LocalDateTime start, 
                                            @Param("end") LocalDateTime end);

    List<Booking> findByUserId(Long userId);

    List<Booking> findByHallId(Long hallId);

    List<Booking> findByWorkspaceId(Long workspaceId);

    //find bookings until a certain date
    List<Booking> findByDateBefore(LocalDateTime date);
}
