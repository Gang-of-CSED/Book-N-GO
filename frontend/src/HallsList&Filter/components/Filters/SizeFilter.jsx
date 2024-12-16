import React from 'react';

const SizeFilter = () => {
  return (
    <div className="mb-6">
      <h3 className="text-white mb-2">SIZE:</h3>
      <p className="text-white text-sm mb-2">Range: 4 - 20</p>
      <input type="range" className="w-full" min="4" max="20" />
    </div>
  );
};

export default SizeFilter;