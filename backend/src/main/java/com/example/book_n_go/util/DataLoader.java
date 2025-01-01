// package com.example.book_n_go.util;
// import java.time.LocalDateTime;
// import java.util.Arrays;
// import java.util.HashSet;

// import org.springframework.boot.CommandLineRunner;
// import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
// import org.springframework.security.crypto.password.PasswordEncoder;
// import org.springframework.stereotype.Component;

// import com.example.book_n_go.enums.Day;
// import com.example.book_n_go.enums.Role;
// import com.example.book_n_go.enums.Status;
// import com.example.book_n_go.model.Aminity;
// import com.example.book_n_go.model.Booking;
// import com.example.book_n_go.model.Hall;
// import com.example.book_n_go.model.Location;
// import com.example.book_n_go.model.User;
// import com.example.book_n_go.model.Workday;
// import com.example.book_n_go.model.Workspace;
// import com.example.book_n_go.repository.AminityRepo;
// import com.example.book_n_go.repository.BookingRepo;
// import com.example.book_n_go.repository.HallRepo;
// import com.example.book_n_go.repository.LocationRepo;
// import com.example.book_n_go.repository.UserRepo;
// import com.example.book_n_go.repository.WorkdayRepo;
// import com.example.book_n_go.repository.WorkspaceRepo;
// @Component
// public class DataLoader implements CommandLineRunner {
//         private final UserRepo userRepo;
//         private final LocationRepo locationRepo;
//         private final WorkspaceRepo workspaceRepo;
//         private final HallRepo hallRepo;
//         private final WorkdayRepo workdayRepo;
//         private final BookingRepo bookingRepo;
//         private final AminityRepo aminityRepo;
//         public DataLoader(UserRepo userRepo,
//                           LocationRepo locationRepo,
//                           WorkspaceRepo workspaceRepo,
//                           HallRepo hallRepo,
//                           WorkdayRepo workdayRepo,
//                           BookingRepo bookingRepo,
//                           AminityRepo aminityRepo) {
//                 this.userRepo = userRepo;
//                 this.locationRepo = locationRepo;
//                 this.workspaceRepo = workspaceRepo;
//                 this.hallRepo = hallRepo;
//                 this.workdayRepo = workdayRepo;
//                 this.bookingRepo = bookingRepo;
//                 this.aminityRepo = aminityRepo;
//         }
//         @Override
//         public void run(String... args) throws Exception {
//                 // Add sample users
//                 PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
//                 User user1 = new User(null, "provider1@example.com", passwordEncoder.encode("Provider One"), "1234567890", "password1", Role.PROVIDER);
//                 User user2 = new User(null, "provider2@example.com", passwordEncoder.encode("Provider Two"), "0987654321", "password2", Role.PROVIDER);
//                 User user3 = new User(null, "client1@example.com", passwordEncoder.encode("Client One"), "1234567891", "password3", Role.CLIENT);
//                 userRepo.saveAll(Arrays.asList(user1, user2, user3));
//                 System.out.println(userRepo.findAll());
//                 // Add sample locations
//                 Location location1 = new Location(0, 123, "Main St", "New York");
//                 Location location2 = new Location(0, 456, "Broadway", "Los Angeles");
//                 Location location3 = new Location(0, 789, "Market St", "San Francisco");
//                 locationRepo.saveAll(Arrays.asList(location1, location2, location3));
//                 // Add sample workspaces
//                 Workspace workspace1 = new Workspace(0, location1, user1, "Hamada Space", 3.0,
//                         "A cozy workspace in NY, with a great view of the city. This workspace offers a comfortable and productive environment with modern amenities, high-speed internet, and a friendly community. Ideal for freelancers, remote workers");
//                 Workspace workspace2 = new Workspace(0, location2, user2, "Hamo Space", 3.0,
//                         "A modern workspace in LA, with a stunning view of the city. This workspace offers a comfortable and productive environment with modern amenities, high-speed internet, and a friendly community. Ideal for freelancers, remote workers");
//                 workspaceRepo.saveAll(Arrays.asList(workspace1, workspace2));
//                 // Add sample aminities
//                 Aminity projector = new Aminity(0, "Projector", new HashSet<>());
//                 Aminity screen = new Aminity(0, "Screen", new HashSet<>());
//                 Aminity ac = new Aminity(0, "AC", new HashSet<>());
//                 Aminity ceilingFans = new Aminity(0, "Ceiling Fans", new HashSet<>());
//                 Aminity whiteBoard = new Aminity(0, "White Board", new HashSet<>());
//                 aminityRepo.saveAll(Arrays.asList(projector, screen, ac, ceilingFans, whiteBoard));
//                 // Add sample halls
//                 Hall hall1 = new Hall(0, "Main Hall", 100, "Large hall with a beautiful view", 200.0, 4.0, workspace1, new HashSet<>(Arrays.asList(projector, screen)), new HashSet<>());
//                 Hall hall2 = new Hall(0, "Central Hall", 150, "Spacious hall with modern amenities", 250.0, 4.5, workspace1, new HashSet<>(Arrays.asList(ac, ceilingFans)), new HashSet<>());
//                 Hall hall3 = new Hall(0, "West Hall", 80, "Cozy hall with a great view", 150.0, 4.0, workspace1, new HashSet<>(Arrays.asList(whiteBoard)), new HashSet<>());
//                 Hall hall4 = new Hall(0, "East Hall", 120, "Elegant hall with state-of-the-art facilities", 300.0, 4.8, workspace2, new HashSet<>(Arrays.asList(screen, ac)), new HashSet<>());
//                 Hall hall5 = new Hall(0, "Grand Hall", 180, "Grand hall with a luxurious design", 400.0, 5.0, workspace2, new HashSet<>(Arrays.asList(projector, ceilingFans)), new HashSet<>());
//                 Hall hall6 = new Hall(0, "North Hall", 200, "Large hall with a panoramic view", 350.0, 4.9, workspace2, new HashSet<>(Arrays.asList(screen, whiteBoard)), new HashSet<>());
//                 Hall hall7 = new Hall(0, "South Hall", 90, "Compact hall with modern design", 180.0, 4.2, workspace2, new HashSet<>(Arrays.asList(ac, projector)), new HashSet<>());
//                 Hall hall8 = new Hall(0, "Garden Hall", 110, "Hall with a beautiful garden view", 220.0, 4.6, workspace2, new HashSet<>(Arrays.asList(ceilingFans, whiteBoard)), new HashSet<>());
//                 Hall hall9 = new Hall(0, "Skyline Hall", 130, "Hall with a stunning skyline view", 280.0, 4.7, workspace2, new HashSet<>(Arrays.asList(screen, projector, ac)), new HashSet<>());
//                 hallRepo.saveAll(Arrays.asList(hall1, hall2, hall3, hall4, hall5, hall6, hall7, hall8, hall9));
//                 Workday workday1 = new Workday(0, LocalDateTime.of(2021, 9, 6, 9, 0), LocalDateTime.of(2021, 9, 6, 17, 0), Day.MONDAY, workspace1);
//                 Workday workday2 = new Workday(0, LocalDateTime.of(2021, 9, 7, 10, 0), LocalDateTime.of(2021, 9, 7, 18, 0), Day.TUESDAY, workspace2);
//                 Workday workday3 = new Workday(0, LocalDateTime.of(2021, 9, 8, 9, 0), LocalDateTime.of(2021, 9, 8, 17, 0), Day.WEDNESDAY, workspace1);
//                 Workday workday4 = new Workday(0, LocalDateTime.of(2021, 9, 9, 10, 0), LocalDateTime.of(2021, 9, 9, 18, 0), Day.THURSDAY, workspace2);
//                 Workday workday5 = new Workday(0, LocalDateTime.of(2021, 9, 10, 9, 0), LocalDateTime.of(2021, 9, 10, 17, 0), Day.FRIDAY, workspace1);
//                 Workday workday6 = new Workday(0, LocalDateTime.of(2021, 9, 11, 10, 0), LocalDateTime.of(2021, 9, 11, 18, 0), Day.SATURDAY, workspace2);
//                 Workday workday7 = new Workday(0, LocalDateTime.of(2021, 9, 12, 9, 0), LocalDateTime.of(2021, 9, 12, 17, 0), Day.SUNDAY, workspace1);
//                 workdayRepo.saveAll(Arrays.asList(workday1, workday2, workday3, workday4, workday5, workday6, workday7));
       
       
//                 // Add sample bookings
//                 Booking booking1 = new Booking(null, hall1, user3, LocalDateTime.now().plusDays(3), LocalDateTime.now().plusDays(3).plusHours(2), Status.CONFIRMED, 200.0);
//                 Booking booking2 = new Booking(null, hall2, user3, LocalDateTime.now().plusDays(5), LocalDateTime.now().plusDays(5).plusHours(3), Status.PENDING, 250.0);
//                 Booking booking3 = new Booking(null, hall3, user3, LocalDateTime.now().plusDays(7), LocalDateTime.now().plusDays(7).plusHours(4), Status.REJECTED, 150.0);
//                 bookingRepo.saveAll(Arrays.asList(booking1, booking2, booking3));
//                 System.out.println("Sample data loaded successfully.");
//         }
// }