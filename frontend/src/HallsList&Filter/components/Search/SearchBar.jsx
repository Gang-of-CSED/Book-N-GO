import React, { useState, useEffect } from 'react';
import SearchInput from './SearchInput';
import SortSelect from './SortSelect';
import { useHalls } from '../context/HallContext';
import styles from './Search.module.css';

const SearchBar = () => {
  const {fetchData, updateSearchWord, updateSortBy } = useHalls();
  const [searchTerm, setSearchTerm] = useState('');
  const [sortBy, setSortBy] = useState('none');

  const handleSearch = () => {
    const trimmedSearchTerm = searchTerm.trim();
    updateSearchWord(trimmedSearchTerm);
    fetchData();
  };

  const handleSortChange = (e) => {
    setSortBy(e.target.value);
    updateSortBy(e.target.value);
  };

  useEffect(() => {
    fetchData();
  }, []); // Empty dependency array ensures this runs only once on mount

  return (
    <div className={styles.container}>
      <SortSelect value={sortBy} onChange={handleSortChange} />
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
