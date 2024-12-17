package com.example.book_n_go.model;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class HallSchedule {
    private List<Workday> workdays;
    private List<Period> bookingPeriods;
}
