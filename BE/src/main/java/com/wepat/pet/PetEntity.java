package com.wepat.pet;

import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PetEntity {
    public PetEntity(PetDto pet) {
        this.petId = pet.getPetId();
        this.name = pet.getName();
        this.types = pet.getTypes();
        this.age = pet.getAge();
        this.photoUrl = pet.getPhotoUrl();
        this.color = pet.getColor();
        this.birthday = pet.getBirthday();
        this.adaptday = pet.getAdaptday();
        this.schedule = new ArrayList<>();
        this.weightList = new ArrayList<>();
    }
    @ApiParam(value = "반려동물 ID")
    private String petId;
    @ApiParam(value = "calendar ID")
    private String calendarId;
    @ApiParam(value = "반려동물 이름")
    private String name;
    @ApiParam(value = "동물 종류")
    private String types;
    @ApiParam(value = "반려동물 나이")
    private int age;
    @ApiParam(value = "펫 프로필")
    private String photoUrl;
    @ApiParam(value = "프로필 색상")
    private String color;
    @ApiParam(value = "반려동물 생일")
    private String birthday;
    @ApiParam(value = "반려동물 입양일")
    private String adaptday;
    @ApiParam(value = "스캐줄ID 리스트")
    private List<String> schedule;
    @ApiParam(value = "몸무게 정보(Dto) 리스트")
    private List<WeightDto> weightList;
}
