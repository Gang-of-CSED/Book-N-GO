import React from 'react';
import { useNavigate } from 'react-router-dom';
import Rating from './Rating';
import styles from './Hall.module.css';
import dummyImg from '../../../assets/1.jpg';

const HallCard = ({ hall }) => {
  const navigate = useNavigate();

  const handleCardClick = () => {
    navigate(`/hall/${hall.id}`);
  };

  return (
    <div className={styles.card} onClick={handleCardClick}>
      <img 
        src={dummyImg} 
        alt={hall.name} 
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