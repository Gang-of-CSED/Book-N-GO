// // Mock API service
// const mockHalls = Array(50).fill(null).map((_, index) => ({
//   id: index + 1,
//   title: `Conference Hall ${index + 1}`,
//   rating: Math.floor(Math.random() * 5) + 1,
//   capacity: Math.floor(Math.random() * 16) + 4,
//   image: 'C:\\Users\\LENOVO\\Desktop\\حاجات عشوائية\\1008476_180367415461422_1474228601_o.jpg',
//   amenities: {
//     screen: Math.random() > 0.5,
//     projector: Math.random() > 0.5,
//     ac: Math.random() > 0.5,
//     ceilingFans: Math.random() > 0.5,
//     whiteBoard: Math.random() > 0.5,
//   }
// }));

// export const fetchHalls = async (params) => {
//   // Simulate API delay
//   await new Promise(resolve => setTimeout(resolve, 500));

//   let filteredHalls = [...mockHalls];

//   // Apply search filter
//   if (params.search) {
//     filteredHalls = filteredHalls.filter(hall => 
//       hall.title.toLowerCase().includes(params.search.toLowerCase())
//     );
//   }

//   // Apply amenities filter
//   if (params.amenities?.length > 0) {
//     filteredHalls = filteredHalls.filter(hall => 
//       params.amenities.every(amenity => 
//         hall.amenities[amenity.toLowerCase().replace(' ', '')]
//       )
//     );
//   }

//   // Apply size filter
//   if (params.minSize && params.maxSize) {
//     filteredHalls = filteredHalls.filter(hall => 
//       hall.capacity >= params.minSize && hall.capacity <= params.maxSize
//     );
//   }

//   // Apply rating filter
//   if (params.rating) {
//     filteredHalls = filteredHalls.filter(hall => 
//       hall.rating >= params.rating
//     );
//   }

//   // Apply sorting
//   if (params.sortBy) {
//     switch (params.sortBy) {
//       case 'rating':
//         filteredHalls.sort((a, b) => b.rating - a.rating);
//         break;
//       case 'capacity':
//         filteredHalls.sort((a, b) => b.capacity - a.capacity);
//         break;
//       case 'title':
//         filteredHalls.sort((a, b) => a.title.localeCompare(b.title));
//         break;
//     }
//   }

//   // Calculate pagination
//   const totalItems = filteredHalls.length;
//   const totalPages = Math.ceil(totalItems / params.pageSize);
//   const start = (params.page - 1) * params.pageSize;
//   const end = start + params.pageSize;
  
//   return {
//     halls: filteredHalls.slice(start, end),
//     totalPages,
//     currentPage: params.page
//   };
// };
export const fetchHalls = async () => {
  try {
    const response = await fetch('http://localhost:3001/halls');

    if (!response.ok) {
      throw new Error('Network response was not ok');
    }
    const data = await response.json();
    // console.log(data);
    return data;
  } catch (error) {
    console.error('Failed to fetch halls:', error);
    throw error;
  }
};