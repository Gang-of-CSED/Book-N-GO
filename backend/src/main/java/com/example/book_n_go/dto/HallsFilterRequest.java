package com.example.book_n_go.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class HallsFilterRequest {
    private List<String> amenities;
    private Integer rating;
    private Integer page;
    private String sortBy;
    private String searchWord;
}