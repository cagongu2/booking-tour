package com.cagongu.tourbe.model;


import com.cagongu.tourbe.enums.StatusAction;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.sql.Timestamp;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long idPost;

    String titlePost;
    String description;
    @Column(length = 100000)
    String contentPost;
    String status;
    int vote;
    String type;
    int view;

    StatusAction statusAction;

    @CreationTimestamp
    @Column(updatable = false)
    Timestamp dateAdded;
    @UpdateTimestamp
    Timestamp dateEdited;
    Timestamp dateDeleted;

    @Column(unique = false, nullable = true, length = 100000)
    String image;
}
