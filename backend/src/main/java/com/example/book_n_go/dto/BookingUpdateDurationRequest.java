package com.example.book_n_go.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookingUpdateDurationRequest {
    Long bookingId;
    LocalDateTime startTime;
    LocalDateTime endTime;
}
