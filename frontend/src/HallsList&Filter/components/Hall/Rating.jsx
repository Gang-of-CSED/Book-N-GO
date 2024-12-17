import React from 'react';

const Rating = ({ rating }) => {
  return (
    <div className="flex text-orange-500">
      {'★'.repeat(rating)}{'☆'.repeat(5-rating)}
    </div>
  );
};

export default Rating;