import React from 'react';
import {Header} from '../../components/Header';
import SearchBar from '../components/Search/SearchBar';
import Filters from '../components/Filters/Filters';
import HallGrid from '../components/Hall/HallGrid';
import { HallProvider } from '../components/context/HallContext';
import styles from './HallsList.module.css';

export const HallsList = () => {
  return (
    <HallProvider>
      <div className={styles.container}>
        <Header />
        <SearchBar />
        <div className={styles.content}>
          <aside className={styles.sidebar}>
            <Filters />
          </aside>
          <main className={styles.main}>
            <HallGrid />
          </main>
        </div>
      </div>
    </HallProvider>
  );
};

export default HallsList;