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
    search: '',
    amenities: [],
    minSize: 4,
    maxSize: 20,
    rating: 0,
    sortBy: 'rating',
    pageSize: 6,
    page: 1
  });

  const updateFilters = useCallback((newFilters) => {
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
    updateFilters,
    updatePage,
    fetchData
  };

  return (
    <HallContext.Provider value={value}>
      {children}
    </HallContext.Provider>
  );
};