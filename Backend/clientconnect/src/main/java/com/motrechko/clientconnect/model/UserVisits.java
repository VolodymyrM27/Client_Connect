package com.motrechko.clientconnect.model;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)

public class UserVisits {
    private Long userId;
    private Long visits;
    private String firstName;
    private String lastName;
    private LocalDate dateOfBirth;
    private String gender;
}
