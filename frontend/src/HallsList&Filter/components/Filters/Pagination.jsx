import React from 'react';
import { useHalls } from '../context/HallContext';

const Pagination = () => {
  const { totalPages, currentPage, updatePage, fetchData } = useHalls();

  const handlePageChange = (page) => {
    updatePage(page);
    fetchData();
  };

  const pages = Array.from({ length: totalPages }, (_, i) => i + 1);

  return (
    <div className="flex gap-2 text-white mt-8">
      <button 
        className="px-2 py-1 border border-gray-600 rounded hover:bg-gray-700"
        onClick={() => handlePageChange(Math.max(1, currentPage - 1))}
        disabled={currentPage === 1}
      >
        {'<<'}
      </button>
      {pages.map(page => (
        <button
          key={page}
          className={`px-2 py-1 rounded ${
            currentPage === page ? 'bg-orange-500' : 'border border-gray-600 hover:bg-gray-700'
          }`}
          onClick={() => handlePageChange(page)}
        >
          {String(page).padStart(2, '0')}
        </button>
      ))}
      <button 
        className="px-2 py-1 border border-gray-600 rounded hover:bg-gray-700"
        onClick={() => handlePageChange(Math.min(totalPages, currentPage + 1))}
        disabled={currentPage === totalPages}
      >
        {'>>'}
      </button>
    </div>
  );
};

export default Pagination;