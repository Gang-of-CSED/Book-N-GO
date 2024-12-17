import React, { createContext, useContext, useRef, useState } from 'react';
import { fetchHalls } from '../services/api';

const HallContext = createContext();

export const useHalls = () => {
  const context = useContext(HallContext);
  if (!context) {
    throw new Error('useHalls must be used within a HallProvider');
  }
  return context;
};

export const HallProvider = ({ children }) => {
  const hallsRef = useRef([]);
  const loadingRef = useRef(false);
  const totalPagesRef = useRef(1);
  const currentPageRef = useRef(1);
  const filtersRef = useRef({
    amenities: [],
    rating: 0,
    pageSize: 6,
    page: 1,
  });
  const sortByRef = useRef('none');
  const searchWordRef = useRef('');

  const triggerUpdate = useState()[1]; // Dummy state to force re-renders when necessary

  const updateSortBy = (sortBy) => {
    console.log('updateSortBy', sortBy);
    sortByRef.current = sortBy;
    triggerUpdate({});
  };
  const resetFilter = () => {
    filtersRef.current = {
      amenities: [],
      rating: 0,
      pageSize: 6,
      page: 1,
    };
    triggerUpdate({});
  }





  const updateSearchWord = (searchWord) => {
    console.log('updateSearchWord', searchWord);
    searchWordRef.current = searchWord;
    triggerUpdate({});
  };

  const updateFilters = (newFilters) => {
    console.log('updateFilters', newFilters);
    filtersRef.current = { ...filtersRef.current, ...newFilters, page: 1 };
    triggerUpdate({});
  };

  const updatePage = (page) => {
    filtersRef.current.page = page;
    triggerUpdate({});
  };

  const fetchData = async () => {
    const body = {
      ...filtersRef.current,
      sortBy: sortByRef.current,
      searchWord: searchWordRef.current,
    };
    console.log('fetchData', JSON.stringify(body, null, 2));
    loadingRef.current = true;
    triggerUpdate({});
    
    try {
      const response = await fetchHalls(body);
      hallsRef.current = response;
      totalPagesRef.current = 2; // Simulated value
      currentPageRef.current = 1; // Simulated value
    } catch (error) {
      console.error('Error fetching halls:', error);
    } finally {
      loadingRef.current = false;
      triggerUpdate({});
    }
  };

  const value = {
    get halls() {
      return hallsRef.current;
    },
    get loading() {
      return loadingRef.current;
    },
    get totalPages() {
      return totalPagesRef.current;
    },
    get currentPage() {
      return currentPageRef.current;
    },
    get filters() {
      return filtersRef.current;
    },
    get sortBy() {
      return sortByRef.current;
    },
    get searchWord() {
      return searchWordRef.current;
    },
    updateFilters,
    updatePage,
    fetchData,
    updateSortBy,
    updateSearchWord,
    resetFilter
  };

  return <HallContext.Provider value={value}>{children}</HallContext.Provider>;
};
