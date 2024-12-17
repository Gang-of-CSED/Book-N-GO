import React from 'react';

const Rating = ({ rating }) => {
  const starStyle = {
    fontSize: '24px', 
  };

  return (
    <div className="flex text-orange-500" style={starStyle}>
      {'★'.repeat(rating)}{'☆'.repeat(5 - rating)}
    </div>
  );
};

export default Rating;