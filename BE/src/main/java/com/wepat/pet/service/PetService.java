package com.wepat.pet.service;

import com.wepat.pet.PetDto;

import java.util.List;
import java.util.concurrent.ExecutionException;

public interface PetService {
    PetDto addPet(PetDto pet) throws ExecutionException, InterruptedException;
    // 나의 펫 정보 가져오기
    List<PetDto> getAllPet(String calendarId) throws ExecutionException, InterruptedException;
    // 펫 정보 가져오기
    PetDto getPet(String calendarId) throws ExecutionException, InterruptedException;
    // 반려동물 추가하기
    // 반려동물 정보 변경하기
    PetDto modifyPet(String petId, PetDto pet) throws ExecutionException, InterruptedException;
    // 반려동물 몸무게 정보 변경하기
    PetEntity addPetWeight(String petId, WeightDto weightDto) throws ExecutionException, InterruptedException;
    // 발려동물 삭제하기
    ResponseEntity<?> deletePet(String CalendarId, String petId) throws ExecutionException, InterruptedException;

}
