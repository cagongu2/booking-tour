package com.cagongu.tourbe.model;

import com.cagongu.tourbe.enums.StatusAction;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class Tour {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long idTour;

    String titleTour;
    double sale;

    Timestamp departureDate;// ngay khoi hanh
    Timestamp returnDate;

    @Column(unique = false, nullable = true, length = 100000)
    String description;
    String address;
    String duration;// for instance: 1 ngay mot dem

    Double price;
    Double childPrice;
    Double babyPrice;

    String type;

    Long tagId;
    Long serviceId;

    int views;
    int votes;
    int purchaseCount;

    StatusAction status;
    StatusAction statusAction;

    @CreationTimestamp
    @Column(updatable = false)
    Timestamp dateAdded;

    @UpdateTimestamp
    Timestamp dateEdited;

    Timestamp dateDeleted;

    @Builder.Default
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    Set<Booking> bookings = new HashSet<>();

    public void addBooking(Booking booking) {
        bookings.add(booking);
        booking.setTour(this);
    }

    @Column(unique = false, nullable = true, length = 100000)
    String image;

    @Builder.Default
    @OneToMany(cascade = CascadeType.ALL)
    Set<Favorite> favorites = new HashSet<>();
}
