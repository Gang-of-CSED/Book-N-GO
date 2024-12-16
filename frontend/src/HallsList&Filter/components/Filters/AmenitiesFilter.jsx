import React from 'react';

const amenities = ['Screen', 'Projector', 'AC', 'Ceiling Fans', 'White Board'];

const AmenitiesFilter = ({ selected, onChange }) => {
  const handleCheckboxChange = (amenity) => {
    // Toggle the selected state for the amenity
    const newSelected = selected.includes(amenity)
      ? selected.filter((item) => item !== amenity) // Remove the amenity
      : [...selected, amenity]; // Add the amenity
    onChange(newSelected);
  };

  return (
    <div className="mb-6">
      <h3 className="text-white mb-2">AMENITIES:</h3>
      <div className="space-y-2">
        {amenities.map((amenity) => (
          <label key={amenity} className="flex items-center text-white">
            <input
              type="checkbox"
              className="mr-2"
              checked={selected.includes(amenity)}
              onChange={() => handleCheckboxChange(amenity)}
            />
            {amenity}
          </label>
        ))}
      </div>
    </div>
  );
};

export default AmenitiesFilter;
