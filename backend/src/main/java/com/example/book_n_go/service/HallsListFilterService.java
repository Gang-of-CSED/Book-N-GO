package com.example.book_n_go.service;

import com.example.book_n_go.dto.HallsFilterRequest;
import com.example.book_n_go.model.Hall;
import com.example.book_n_go.repository.HallRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HallsListFilterService {

    @Autowired
    private HallRepo hallRepo;

    public List<Hall> applyCriterias(HallsFilterRequest request) {
        Specification<Hall> spec = buildSpecification(request);

        Sort sort = Sort.by(Sort.Direction.DESC, request.getSortBy());

        return hallRepo.findAll(spec, sort);
    }

    private Specification<Hall> buildSpecification(HallsFilterRequest request) {
        return (root, query, criteriaBuilder) -> {
            Specification<Hall> spec = Specification.where(null);

            // Filter by rating
            if (request.getRating() != null) {
                spec = spec.and((hall, query1, cB) -> 
                    cB.greaterThanOrEqualTo(hall.get("rating"), request.getRating()));
            }

            // Search by keyword in name
            if (request.getSearchWord() != null && !request.getSearchWord().isEmpty()) {
                spec = spec.and((hall, query1, cB) ->
                    cB.like(cB.lower(hall.get("name")), "%" + request.getSearchWord().toLowerCase() + "%"));
            }

            // Filter by amenities (wait for ahmed hassan to add amenities to the Hall model)
            // if (request.getAmenities() != null && !request.getAmenities().isEmpty()) {
            //     for (String amenity : request.getAmenities()) {
            //         spec = spec.and((hall, query1, cB) ->
            //             cB.like(cB.lower(hall.get("amenities")), "%" + amenity.toLowerCase() + "%"));
            //     }
            // }

            return spec.toPredicate(root, query, criteriaBuilder);
        };
    }
}
