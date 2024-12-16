import React from 'react';
import styles from './RatingFilter.module.css';

const RatingFilter = ({ rating, onChange }) => {
  const handleStarClick = (selectedRating) => {
    onChange(selectedRating === rating ? 0 : selectedRating);
  };

  return (
    <div className={styles.ratingContainer}>
      <h3 className={styles.title}>RATING:</h3>
      <div className={styles.starsContainer}>
        {[1, 2, 3, 4, 5].map((star) => (
          <span
            key={star}
            onClick={() => handleStarClick(star)}
            className={star <= rating ? styles.starFilled : styles.starEmpty}
          >
            ★
          </span>
        ))}
      </div>
    </div>
  );
};

export default RatingFilter;