package com.cagongu.tourbe.dto.request;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class EvaluateRequest {
    Integer numberStar;

    String title;
    String content;

    Long accountId;

    Long bookingId;
}
