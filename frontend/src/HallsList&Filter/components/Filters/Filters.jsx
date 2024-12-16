import React from 'react';
import AmenitiesFilter from './AmenitiesFilter';
import SizeFilter from './SizeFilter';
import RatingFilter from './RatingFilter';
import Pagination from './Pagination';
import { useHalls } from '../context/HallContext';
import styles from './Filters.module.css';

const Filters = () => {
  const { filters, updateFilters, fetchData } = useHalls();

  const handleApplyFilters = () => {
    fetchData();
  };

  return (
    <div className={styles.container}>
      <AmenitiesFilter 
        selected={filters.amenities}
        onChange={(amenities) => updateFilters({ amenities })}
      />
      <SizeFilter 
        min={filters.minSize}
        max={filters.maxSize}
        onChange={(size) => updateFilters(size)}
      />
      <RatingFilter 
        rating={filters.rating}
        onChange={(rating) => updateFilters({ rating })}
      />
      <button
        onClick={handleApplyFilters}
        className={styles.applyButton}
      >
        Apply Filters
      </button>
      <Pagination />
    </div>
  );
};

export default Filters;