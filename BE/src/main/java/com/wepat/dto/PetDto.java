package com.wepat.dto;

import com.google.cloud.Timestamp;
import com.wepat.entity.PetEntity;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PetDto {
    public PetDto(PetEntity pet) {
        this.name = pet.getName();
        this.animalType = pet.getAnimalType();
        this.age = pet.getAge();
        this.photoURL = pet.getPhotoURL();
        this.color = pet.getColor();
        this.birthday = pet.getBirthday().toLocalDateTime().toLocalDate();
        this.adaptday = pet.getAdaptday().toLocalDateTime().toLocalDate();
    }
//    @ApiParam(value = "반려동물 데이터가 표시되는 달력")
//    private String calendarId;
    @ApiParam(value = "반려동물 이름")
    private String name;
    @ApiParam(value = "동물 종류")
    private String animalType;
    @ApiParam(value = "반려동물 나이")
    private int age;
    @ApiParam(value = "반려동물 프로필")
    private String photoURL;
    @ApiParam(value = "프로필 색상")
    private String color;
    @ApiParam(value = "반려동물 생일")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate birthday;
    @ApiParam(value = "반려동물 입양일")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate adaptday;
}