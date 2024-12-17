// import React from 'react';
import React, { useState, useEffect } from 'react';

import AmenitiesFilter from './AmenitiesFilter';
import SizeFilter from './SizeFilter';
import RatingFilter from './RatingFilter';
import Pagination from './Pagination';
import { useHalls } from '../context/HallContext';
import styles from './Filters.module.css';

const Filters = () => {
  const { filters, updateFilters, fetchData,resetFilter } = useHalls();

  const handleApplyFilters = () => {
    fetchData();
  };
  const handleRestFilters = () => {
    resetFilter();
    fetchData();
  };
  return (
    <div className={styles.container}>
      <AmenitiesFilter 
        selected={filters.amenities}
        onChange={(amenities) => updateFilters({ amenities })}
      />
      {/* <SizeFilter 
        min={filters.minSize}
        max={filters.maxSize}
        onChange={(size) => updateFilters(size)}
      /> */}
      <RatingFilter 
        rating={filters.rating}
        onChange={(rating) => updateFilters({ rating })}
      />
      <button
        onClick={handleApplyFilters}
        className={styles.applyButton}
      >
        Apply 
      </button>
      <button
        onClick={handleRestFilters}
        className={styles.applyButton}
      >
        Reset 
      </button>
      {/* <Pagination /> */}
    </div>
  );
};

export default Filters;