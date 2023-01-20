package com.wepat.dto;

import io.swagger.annotations.ApiParam;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Data
@NoArgsConstructor
public class PetDto {
    @ApiParam(value = "반려동물 데이터가 표시되는 달력")
    private String calendarId;
    @ApiParam(value = "반려동물 이름")
    private String name;
    @ApiParam(value = "반려동물 나이")
    private int age;
    @ApiParam(value = "반려동물 생일")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate birthday;
    @ApiParam(value = "반려동물 입양일")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate adaptday;
}