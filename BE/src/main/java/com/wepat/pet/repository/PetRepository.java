package com.wepat.pet.repository;

import com.wepat.pet.PetDto;
import com.wepat.pet.PetEntity;
import com.wepat.pet.WeightDto;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.concurrent.ExecutionException;

public interface PetRepository {
    // 반려동물 추가하기
    void addPet(PetDto petDto) throws ExecutionException, InterruptedException;
    // 전체 펫 정보 가져오기
    List<PetDto> getAllPet(String calendarId) throws ExecutionException, InterruptedException;
    // 펫 정보 가져오기
    PetDto getPetById(String petId) throws ExecutionException, InterruptedException;
    // 반려동물 정보 변경하기
    void modifyPetById(String petId, PetDto petDto) throws ExecutionException, InterruptedException;
    // 반려동물 몸무게 추가
    void addPetWeightById(String petId, WeightDto weightDto) throws ExecutionException, InterruptedException;
    // 반려동물 몸무게 정보 변경하기
    void modifyPetWeight(String petId, WeightDto weightDto) throws ExecutionException, InterruptedException;
    // 반려동물 삭제하기
    void deletePetById(String petId) throws ExecutionException, InterruptedException;

}
