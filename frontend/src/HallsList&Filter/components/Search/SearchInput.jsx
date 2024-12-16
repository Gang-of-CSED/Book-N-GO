import React from 'react';
import styles from './Search.module.css';

const SearchInput = ({ value, onChange }) => {
  return (
    <input 
      type="text" 
      className={styles.searchInput}
      placeholder="Search..." 
      value={value}
      onChange={(e) => onChange(e.target.value)}
    />
  );
};

export default SearchInput;