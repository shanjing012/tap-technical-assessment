package com.assessment.service;

import com.assessment.entity.HouseholdEntity;
import com.assessment.entity.MemberEntity;
import com.assessment.entity.enums.HouseholdType;
import com.assessment.model.Household;
import com.assessment.model.Member;
import com.assessment.repository.HouseholdRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class HouseholdService {

    @Autowired
    private HouseholdRepository householdRepository;

    //create household, return household id
    public Long createHousehold(HouseholdType householdType) {
        HouseholdEntity householdEntity = new HouseholdEntity(householdType);
        return householdRepository.save(householdEntity).getId();
    }

    public List<Household> findAllHousehold() {
        List<HouseholdEntity> householdEntityList = householdRepository.findAll();
        List<Household> householdList = new ArrayList<>();
        for(HouseholdEntity householdEntity : householdEntityList) {
            Household household = new Household(householdEntity.getId(), householdEntity.getHouseholdType());
            for(MemberEntity memberEntity : householdEntity.getMemberEntityList()) {
                Member member = new Member(
                        memberEntity.getId(),
                        memberEntity.getName(),
                        memberEntity.getGender(),
                        memberEntity.getMaritalStatus(),
                        memberEntity.getSpouse() != null ? memberEntity.getSpouse().getId() : null,
                        memberEntity.getAnnualIncome(),
                        memberEntity.getDateOfBirth()
                );
                household.getMemberList().add(member);
            }
            householdList.add(household);
        }
        return householdList;
    }

    public Household findHousehold(Long householdId) {
        HouseholdEntity householdEntity = findHouseholdEntity(householdId);
        Household household = new Household(householdEntity.getId(), householdEntity.getHouseholdType());
        for(MemberEntity memberEntity : householdEntity.getMemberEntityList()) {
            Member member = new Member(
                    memberEntity.getId(),
                    memberEntity.getName(),
                    memberEntity.getGender(),
                    memberEntity.getMaritalStatus(),
                    memberEntity.getSpouse() != null ? memberEntity.getSpouse().getId() : null,
                    memberEntity.getAnnualIncome(),
                    memberEntity.getDateOfBirth()
            );
            household.getMemberList().add(member);
        }
        return household;
    }

    public HouseholdEntity findHouseholdEntity(Long householdId) {
        Optional<HouseholdEntity> optionalHouseholdEntity = householdRepository.findById(householdId);
        return optionalHouseholdEntity.orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid household ID"));
    }

//    public List<Household> findHouseholdBasedOn(Integer maximumAge, Integer minimumAge, Boolean hasMarriedCouple, Integer householdSize, Integer totalIncome) {
//
//    }
}
