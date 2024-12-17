package com.example.book_n_go.dto;

import com.example.book_n_go.enums.Status;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookingUpdateStatusRequest {
    Long bookingId;
    Status status;
}
