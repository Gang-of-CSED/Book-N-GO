import React from 'react';
import Rating from './Rating';
import styles from './Hall.module.css';

const HallCard = ({ hall }) => {
  return (
    <div className={styles.card}>
      <img 
        // src={hall.image}
        // alt={hall.title}
       src={`http://localhost:3001${hall.image}`} alt={hall.title} 

        className={styles.image}
      />
      <div className={styles.content}>
        <h3 className={styles.title}>{hall.title}</h3>
        <div className={styles.footer}>
          <Rating rating={hall.rating} />
          <span className={styles.capacity}>
            Capacity: {hall.capacity}
          </span>
        </div>
      </div>
    </div>
  );
};

export default HallCard;