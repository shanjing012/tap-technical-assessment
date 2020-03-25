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

import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class HouseholdService {

    @Autowired
    EntityManager entityManager;

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

    public List<Household> showStudentEncouragementBonusRecipients(Long ageLessThan, Long annualIncomeLessThan) {

        List<HouseholdEntity> householdEntityList = householdRepository.selectStudentEncouragementBonusHousehold(ageLessThan, annualIncomeLessThan);
        List<Household> householdList = new ArrayList<>();
        for(HouseholdEntity householdEntity : householdEntityList) {
            //Convert householdentity to household and populate members who are less than 16 years old
            Household household = new Household(householdEntity.getId(), householdEntity.getHouseholdType());
            //Filter members who are less than 16 years old
            household.setMemberList(householdEntity.getMemberEntityList()
                    .stream()
                    .filter(memberEntity -> memberEntity.getDateOfBirth().until(LocalDate.now()).get(ChronoUnit.YEARS) < ageLessThan)
                    .map(memberEntity -> new Member(
                        memberEntity.getId(),
                        memberEntity.getName(),
                        memberEntity.getGender(),
                        memberEntity.getMaritalStatus(),
                        memberEntity.getSpouse() != null ? memberEntity.getSpouse().getId() : null,
                        memberEntity.getAnnualIncome(),
                        memberEntity.getDateOfBirth()
                    )).collect(Collectors.toList()));
            householdList.add(household);
        }
        return householdList;
    }
}
