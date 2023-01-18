package com.wepat.repository;

import com.wepat.dto.PetDto;

import java.util.List;

public interface PetRepository {
    // 전체 펫 정보 가져오기
    List<PetDto> getAllPets(String calendarId);
    // 반려동물 추가하기
    PetDto addPet(String calendarId, PetDto pet);
    // 반려동물 정보 변경하기
    PetDto modifyPet(String petId, PetDto pet);
}
