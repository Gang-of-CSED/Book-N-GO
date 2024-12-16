import React, { useEffect } from 'react';
import HallCard from './HallCard';
import { useHalls } from '../context/HallContext';
import styles from './Hall.module.css';

const HallGrid = () => {
  const { halls, loading, fetchData } = useHalls();

  // useEffect(() => {
  //   fetchData();
  // }, [fetchData]);
  useEffect(() => {
    fetchData();
  }, []); // Empty dependency array ensures this runs only once on mount

  if (loading) {
    return (
      <div className={styles.grid}>
        {[...Array(6)].map((_, index) => (
          <div key={index} className={styles.skeleton}>
            <div className={styles.skeletonImage}></div>
            <div className={styles.skeletonContent}>
              <div className={styles.skeletonTitle}></div>
              <div className={styles.skeletonText}></div>
            </div>
          </div>
        ))}
      </div>
    );
  }

  return (
    <div className={styles.grid}>
      {halls.map((hall) => (
        <HallCard key={hall.id} hall={hall} />
      ))}
    </div>
  );
};

export default HallGrid;