import React from 'react';
import Rating from './Rating';
import styles from './Hall.module.css';

import dummyImg from '../../../assets/1.jpg';

const HallCard = ({ hall }) => {
  return (
    <div className={styles.card}>
      <img 
        // src={hall.image}
        // alt={hall.title}
       src={dummyImg} alt={hall.name} 

        className={styles.image}
      />
      <div className={styles.content}>
        <h3 className={styles.title}>{hall.name}</h3>
        <div className={styles.footer}>
          <Rating rating={hall.rating} />
          <span className={styles.capacity}>
            Capacity: {hall.capacity}
            <p>Price per hour: ${hall.pricePerHour}</p>

          </span>
        </div>
      </div>
    </div>
  );
};

export default HallCard;