import React, { useState, useEffect } from 'react';
import SearchInput from './SearchInput';
import SortSelect from './SortSelect';
import { useHalls } from '../context/HallContext';
import styles from './Search.module.css';

const SearchBar = () => {
  const { updateFilters, fetchData } = useHalls();
  const [searchTerm, setSearchTerm] = useState('');
  const [sortBy, setSortBy] = useState('rating');

  const handleSearch = () => {
    updateFilters({ search: searchTerm, sortBy });
    fetchData();
  };

  // useEffect(() => {
  //   updateFilters({ sortBy });
  //   fetchData();
  // }, [sortBy, updateFilters, fetchData]);
  useEffect(() => {
    fetchData();
  }, []); // Empty dependency array ensures this runs only once on mount

  return (
    <div className={styles.container}>
      <SortSelect value={sortBy} onChange={setSortBy} />
      <div className={styles.searchWrapper}>
        <SearchInput value={searchTerm} onChange={setSearchTerm} />
      </div>
      <button onClick={handleSearch} className={styles.searchButton}>
        Search
      </button>
    </div>
  );
};

export default SearchBar;