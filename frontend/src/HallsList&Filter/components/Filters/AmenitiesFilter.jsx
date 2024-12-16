import React from 'react';

const amenities = ['Screen', 'Projector', 'AC', 'Ceiling Fans', 'White Board'];

const AmenitiesFilter = () => {
  return (
    <div className="mb-6">
      <h3 className="text-white mb-2">AMENITIES:</h3>
      <div className="space-y-2">
        {amenities.map((amenity) => (
          <label key={amenity} className="flex items-center text-white">
            <input type="checkbox" className="mr-2" />
            {amenity}
          </label>
        ))}
      </div>
    </div>
  );
};

export default AmenitiesFilter;