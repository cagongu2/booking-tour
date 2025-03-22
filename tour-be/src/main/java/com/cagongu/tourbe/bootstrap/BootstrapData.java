package com.cagongu.tourbe.bootstrap;


import com.cagongu.tourbe.repository.AccountRepository;
import com.cagongu.tourbe.repository.BookingRepository;
import com.cagongu.tourbe.repository.TourRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor
public class BootstrapData implements CommandLineRunner {
    private final AccountRepository accountRepository;
    private final BookingRepository bookingRepository;
    private final TourRepository tourRepository;
    private final PasswordEncoder passwordEncoder;


    @Override
    public void run(String... args) throws Exception {
//        loadUser();
//        loadTour();
//        createBooking();
    }

//    private void createBooking() {
//        Account account = accountRepository.findAll().getFirst();
//        Tour tour = tourRepository.findAll().getFirst();
//
//        if(bookingRepository.count() < 1){
//            Booking booking = Booking.builder()
//                    .PIN(UUID.randomUUID().toString())
//                    .status("CONFIRMED")
//                    .totalPrice(10000.0)
//                    .address(tour.getAddress())
//                    .phone(account.getPhone())
//                    .email(account.getEmail())
//                    .notes("Special requests for this booking")
//                    .customerName(account.getName())
//                    .adult(2)
//                    .children(1)
//                    .travelType("Family Trip")
//                    .buyer(account.getName())
//                    .statusAction(StatusAction.ACTIVE.name())
//                    .account(account)
//                    .tour(tour)
//                    .build();
//
//            bookingRepository.save(booking);
//            tour.getBookings().add(booking);
//        }
//
//        tourRepository.save(tour);
//    }

//    private void loadTour() {
//        if(tourRepository.count() <= 1){
//            Tour tour1 = new Tour();
//            tour1.setTitleTour("Tour khám phá thành phố");
//            tour1.setPrice(100.0);
//            tour1.setSale(10.0);
//            tour1.setDepartureDate(Timestamp.valueOf("2024-12-01 08:00:00"));
//            tour1.setDescription("Khám phá các điểm nổi bật trong thành phố");
//            tour1.setAddress("123 Main Street, City Center");
//            tour1.setDuration("1 ngày 1 đêm");
//            tour1.setType(TypeOfTour.TOUR_XE_TU_LAI.name());
//            tour1.setTagId(null);
//            tour1.setServiceId(null);
//            tour1.setViews(100);
//            tour1.setVotes(50);
//
//            tour1.setPurchaseCount(20);
//            tour1.setStatusAction(StatusAction.ACTIVE);
//            tour1.setBookings(null);
//
//            Tour tour2 = new Tour();
//            tour2.setTitleTour("Tour thiên nhiên");
//            tour2.setPrice(200.0);
//            tour2.setSale(15.0);
//            tour2.setDepartureDate(Timestamp.valueOf("2024-12-15 09:00:00"));
//            tour2.setDescription("Khám phá vẻ đẹp thiên nhiên");
//            tour2.setAddress("Mountain Road, Countryside");
//            tour2.setDuration("2 ngày 1 đêm");
//            tour2.setType(TypeOfTour.TOUR_THANH_LOC.name());
//            tour2.setTagId(null);
//            tour2.setServiceId(null);
//            tour2.setViews(150);
//            tour2.setVotes(60);
//            tour2.setPurchaseCount(30);
//            tour2.setStatusAction(StatusAction.ACTIVE);
//            tour2.setBookings(null);
//
//            tourRepository.saveAll(List.of(tour1, tour2));
//        }
//    }

//    private void loadUser() {
//        if(accountRepository.count() == 0){
//            Account account1 = new Account();
//            account1.setName("John Doe");
//            account1.setEmail("johndoe@example.com");
//            account1.setPhone("123456789");
//            account1.setRole("USER");
//            account1.setUsername("johndoe");
//            account1.setPassword(passwordEncoder.encode("12345678"));
//            account1.setAvatar("https://example.com/avatar.jpg");
//            account1.setIdFacebook("facebookId123");
//            account1.setIdGoogle("googleId123");
//            account1.setWebsite("https://example.com");
//            account1.setAddress("123 Main Street, Hometown");
//            account1.setGender("Male");
//            account1.setDob(Timestamp.valueOf("1990-01-01 00:00:00"));
//            account1.setVerify(1);
//            account1.setVerifyToken("token12345");
//            account1.setStatusAction(StatusAction.ACTIVE.name());
//
//            Account account2 = new Account();
//            account2.setName("Jane Smith");
//            account2.setEmail("janesmith@example.com");
//            account2.setPhone("987654321");
//            account2.setRole("USER");
//            account2.setUsername("janesmith");
//            account2.setPassword(passwordEncoder.encode("12345678"));
//            account2.setAvatar("https://example.com/avatar2.jpg");
//            account2.setIdFacebook("facebookId456");
//            account2.setIdGoogle("googleId456");
//            account2.setWebsite("https://example.com");
//            account2.setAddress("456 Elm Street, Cityville");
//            account2.setGender("Female");
//            account2.setDob(Timestamp.valueOf("1992-02-02 00:00:00"));
//            account2.setVerify(1);
//            account2.setVerifyToken("token45678");
//            account2.setStatusAction(StatusAction.ACTIVE.name());
//
//            Account account3 = new Account();
//            account3.setName("Alice Brown");
//            account3.setEmail("alicebrown@example.com");
//            account3.setPhone("555123456");
//            account3.setRole("ADMIN");
//            account3.setUsername("alicebrown");
//            account3.setPassword(passwordEncoder.encode("12345678"));
//            account3.setAvatar("https://example.com/avatar3.jpg");
//            account3.setIdFacebook("facebookId789");
//            account3.setIdGoogle("googleId789");
//            account3.setWebsite("https://example.com");
//            account3.setAddress("789 Maple Street, Townsville");
//            account3.setGender("Female");
//            account3.setDob(Timestamp.valueOf("1995-03-03 00:00:00"));
//            account3.setVerify(1);
//            account3.setVerifyToken("token78910");
//            account3.setStatusAction(StatusAction.ACTIVE.name());
//
//            Account account4 = new Account();
//            account4.setName("Bob Martin");
//            account4.setEmail("bobmartin@example.com");
//            account4.setPhone("444321789");
//            account4.setRole("USER");
//            account4.setUsername("bobmartin");
//            account4.setPassword(passwordEncoder.encode("12345678"));
//            account4.setAvatar("https://example.com/avatar4.jpg");
//            account4.setIdFacebook("facebookId321");
//            account4.setIdGoogle("googleId321");
//            account4.setWebsite("https://example.com");
//            account4.setAddress("321 Pine Street, Suburbia");
//            account4.setGender("Male");
//            account4.setDob(Timestamp.valueOf("1988-04-04 00:00:00"));
//            account4.setVerify(1);
//            account4.setVerifyToken("token321654");
//            account4.setStatusAction(StatusAction.ACTIVE.name());
//
//            accountRepository.saveAll(List.of(account1, account2, account3, account4));
//        }

//    }
}
