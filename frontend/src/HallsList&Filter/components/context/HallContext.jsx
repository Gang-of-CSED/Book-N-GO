import React, { createContext, useContext, useState, useCallback } from 'react';
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
  const [halls, setHalls] = useState([]);
  const [loading, setLoading] = useState(false);
  const [totalPages, setTotalPages] = useState(1);
  const [currentPage, setCurrentPage] = useState(1);
  const [filters, setFilters] = useState({
    amenities: [],
    rating: 0,
    pageSize: 6,
    page: 1
  });
  const [sortBy, setSortBy] = useState('none');
  const [searchWord, setSearchWord] = useState('');

  const updateSortBy = useCallback((sortBy) => {
    console.log("updateSortBy",sortBy);
    setSortBy(sortBy);
  }, []);

  const updateSearchWord = useCallback((searchWord) => {
    console.log("updateSearchWord",searchWord);
    setSearchWord(searchWord);
  }, []);

  const updateFilters = useCallback((newFilters) => {
    console.log("updateFilters",newFilters);
    setFilters(prev => ({ ...prev, ...newFilters, page: 1 }));
  }, []);

  const updatePage = useCallback((page) => {
    setFilters(prev => ({ ...prev, page }));
  }, []);

  const fetchData = useCallback(async () => {
    setLoading(true);
    try {
      const response = await fetchHalls(filters);

      // console.log("halls response",response);
      // setHalls(response.halls);
      setHalls(response);
      // setTotalPages(response.totalPages);
      setTotalPages(2);
      // setCurrentPage(response.currentPage);
      setCurrentPage(1);
    } catch (error) {
      console.error('Error fetching halls:', error);
    } finally {
      setLoading(false);
    }
  }, [filters]);

  const value = {
    halls,
    loading,
    totalPages,
    currentPage,
    filters,
    sortBy,
    searchWord,
    updateFilters,
    updatePage,
    fetchData,
    updateSortBy,
    updateSearchWord,
  };

  return (
    <HallContext.Provider value={value}>
      {children}
    </HallContext.Provider>
  );
};