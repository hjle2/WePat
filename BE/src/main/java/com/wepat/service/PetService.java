package com.wepat.service;

import com.wepat.dto.PetDto;

import java.util.List;
import java.util.concurrent.ExecutionException;

public interface PetService {
    // 전체 펫 정보 가져오기
    List<PetDto> getAllPets(String calendarId) throws ExecutionException, InterruptedException ;
    // 반려동물 추가하기
    PetDto addPet(String calendarId, PetDto pet) throws ExecutionException, InterruptedException ;
    // 반려동물 정보 변경하기
    PetDto modifyPet(String petId, PetDto pet) throws ExecutionException, InterruptedException ;
}
