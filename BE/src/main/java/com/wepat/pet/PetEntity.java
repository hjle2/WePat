package com.wepat.pet;

import io.swagger.annotations.ApiParam;
import lombok.Data;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
public class PetEntity {
    public PetEntity(PetDto pet) {
        name = pet.getName();
        age = pet.getAge();
        birthday = pet.getBirthday();
        adaptday = pet.getAdaptday();
        schedule = new ArrayList<>();
        weightList = new ArrayList<>();
    }
    @ApiParam(value = "반려동물 이름")
    private String name;
    @ApiParam(value = "반려동물 나이")
    private int age;
    @ApiParam(value = "펫 프로필")
    private String photoURL;
    @ApiParam(value = "반려동물 생일")
    private LocalDate birthday;
    @ApiParam(value = "반려동물 입양일")
    private LocalDate adaptday;
    @ApiParam(value = "스캐줄ID 리스트")
    private List<String> schedule;
    @ApiParam(value = "몸무게 정보(Dto) 리스트")
    private List<WeightDto> weightList;
}
