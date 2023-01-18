package com.wepat.dto;

import io.swagger.annotations.ApiParam;
import lombok.Data;

import java.security.Timestamp;
import java.util.List;

@Data
public class PetDto {
    @ApiParam(value = "스캐줄ID 리스트")
    private List<String> schedule;
    @ApiParam(value = "몸무게 정보(Dto) 리스트")
    private List<WeightDto> weightList;
    @ApiParam(value = "반려동물 이름", required = true)
    private String name;
    @ApiParam(value = "반려동물 나이")
    private int age;
    @ApiParam(value = "반려동물 생일")
    private Timestamp birthday;
    @ApiParam(value = "반려동물 입양일")
    private Timestamp adaptday;
}
