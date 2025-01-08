package com.busanit501.bootproject.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PetDTO {
    private Long petId;
    private Long userId;
    private String name;
    private String type;
    private LocalDate birth;
    private String gender;
    private String personality;
    private float weight;
    private String profilePicture;
    private boolean isVerified;

}
