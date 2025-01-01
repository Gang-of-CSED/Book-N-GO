import React from 'react';
import { Header } from '../../components/Header';
import Filters from '../components/Filters/Filters';
import HallGrid from '../components/Hall/HallGrid';
import SearchBar from '../components/Search/SearchBar';
import { HallProvider } from '../components/context/HallContext';
import styles from './HallsList.module.css';
import Pagination from '../components/Filters/Pagination';

const token = new URLSearchParams(window.location.search).get('token');
if (token != null) localStorage.setItem('token', token);

export const HallsList = () => {
  return (
    <>
      <Header />
      <HallProvider>
        <div className={styles.container}>

          <SearchBar />
          <div className={styles.content}>
            <aside className={styles.sidebar}>
              <Filters />
            </aside>
            <main className={styles.main}>
              <HallGrid />
            <div className={styles.paginationContainer} >
              <Pagination />
            </div>
            </main>
          </div>
        </div>
      </HallProvider>
    </>
  );
};

export default HallsList;