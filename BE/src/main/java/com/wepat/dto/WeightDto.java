package com.wepat.dto;

import io.swagger.annotations.ApiParam;
import lombok.Data;

import java.sql.Timestamp;

@Data
public class WeightDto {
    @ApiParam(value = "반려동물ID")
    private String petId;
    @ApiParam(value = "몸무게")
    private double weight;
    @ApiParam(value = "등록일")
    private Timestamp date;
}
