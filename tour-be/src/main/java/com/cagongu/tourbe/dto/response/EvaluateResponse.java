package com.cagongu.tourbe.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.sql.Timestamp;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class EvaluateResponse {
    Long idEvaluate;
    Integer numberStar;

    String title;
    String content;

    Timestamp dateAdded;
    Long accountId;

    Long bookingId;
}
