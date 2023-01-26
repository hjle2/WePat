package com.wepat.dto;

import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WeightDto {
//    @ApiParam(value = "반려동물ID")
//    private String petId;
    @ApiParam(value = "몸무게")
    private double weight;
    @ApiParam(value = "등록일")
    private String date;
}
