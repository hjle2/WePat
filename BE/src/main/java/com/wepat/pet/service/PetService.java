package com.wepat.pet.service;

import com.wepat.pet.PetDto;
import com.wepat.pet.PetEntity;
import com.wepat.pet.WeightDto;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.concurrent.ExecutionException;

public interface PetService {
    // 반려동물 추가하기
    void addPet(PetDto petDto) throws ExecutionException, InterruptedException;
    // 나의 펫 정보 가져오기
    List<PetDto> getAllPet(String calendarId) throws ExecutionException, InterruptedException;
    // 펫 정보 가져오기
    PetDto getPetById(String petId) throws ExecutionException, InterruptedException;
    // 반려동물 정보 변경하기
    void modifyPetById(String petId, PetDto petDto) throws ExecutionException, InterruptedException;
    // 반려동물 몸무게 정보 변경하기
    void addPetWeightById(String petId, WeightDto weightDto) throws ExecutionException, InterruptedException;
    // 반려동물 몸무게 수정
    void modifyPetWeight(String petId, WeightDto weightDto) throws ExecutionException, InterruptedException;
    // 발려동물 삭제하기
    void deletePetById(String petId) throws ExecutionException, InterruptedException;

}
