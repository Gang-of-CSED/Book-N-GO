package com.example.book_n_go.service;

import com.example.book_n_go.dto.HallsFilterRequest;
import com.example.book_n_go.model.Hall;
import com.example.book_n_go.repository.HallRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class HallsListFilterService {

    @Autowired
    private HallRepo hallRepo;

    public List<Hall> applyCriterias(HallsFilterRequest request) {
        List<Hall> halls = hallRepo.findAll();

        halls = sortHalls(halls, request.getSortBy());
        // halls = filterByAmenities(halls, request.getAmenities()); // Uncomment if needed
        halls = filterByRating(halls, request.getRating());
        halls = searchHalls(halls, request.getSearchWord());

        return halls;
    }

    public List<Hall> sortHalls(List<Hall> halls, String sortBy) {
        if (sortBy != null) {
            return halls.stream()
                    .sorted((h1, h2) -> {
                        if (sortBy.equalsIgnoreCase("rating")) {
                            return Double.compare(h2.getRating(), h1.getRating());
                        }
                        else if (sortBy.equalsIgnoreCase("capacity")) {
                            return Integer.compare(h2.getCapacity(), h1.getCapacity());
                        }
                        else if(sortBy.equalsIgnoreCase("pricePerHour")) {
                            return Double.compare(h1.getPricePerHour(), h2.getPricePerHour());
                        }
                        return 0;
                    })
                    .collect(Collectors.toList());
        }
        return halls;
    }

    // Uncomment and implement this method if needed
    // public List<Hall> filterByAmenities(List<Hall> halls, List<String> amenities) {
    //     if (amenities != null && !amenities.isEmpty()) {
    //         return halls.stream()
    //                 .filter(hall -> hall.getAmenities().containsAll(amenities))
    //                 .collect(Collectors.toList());
    //     }
    //     return halls;
    // }

    public List<Hall> filterByRating(List<Hall> halls, Integer rating) {
        if (rating != null) {
            return halls.stream().filter(hall -> hall.getRating() >= rating).collect(Collectors.toList());
        }
        return halls;
    }

    public List<Hall> searchHalls(List<Hall> halls, String searchWord) {
        if (searchWord != null && !searchWord.isEmpty()) {
            return halls.stream().filter(hall -> hall.getName().toLowerCase().contains(searchWord.toLowerCase())).collect(Collectors.toList());
        }
        return halls;
    }
}
