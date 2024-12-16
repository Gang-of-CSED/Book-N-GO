import React from 'react'; 
import styles from './Search.module.css';

const sortOptions = [
  { value: 'none', label: 'None' },
  { value: 'rating', label: 'Rating' },
  { value: 'capacity', label: 'Capacity' },
  { value: 'title', label: 'Title' }
];

const SortSelect = ({ value, onChange }) => {
  return (
    <div className={styles.selectContainer}>
      <select 
        className={styles.select}
        value={value}
        onChange={onChange} // Pass the event directly
      >
        {sortOptions.map(option => (
          <option key={option.value} value={option.value}>
            Sort by: {option.label}
          </option>
        ))}
      </select>
      <div className={styles.selectIcon}>
        <svg fill="none" stroke="currentColor" viewBox="0 0 24 24">
          <path strokeLinecap="round" strokeLinejoin="round" strokeWidth="2" d="M19 9l-7 7-7-7" />
        </svg>
      </div>
    </div>
  );
};

export default SortSelect;
