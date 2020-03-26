package com.assessment.service;

import com.assessment.entity.HouseholdEntity;
import com.assessment.entity.MemberEntity;
import com.assessment.entity.enums.HouseholdType;
import com.assessment.entity.enums.MaritalStatus;
import com.assessment.mapper.MemberMapper;
import com.assessment.model.Household;
import com.assessment.model.Member;
import com.assessment.repository.HouseholdRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class HouseholdService {

    @Autowired
    private HouseholdRepository householdRepository;

    @Autowired
    private MemberService memberService;

    //create household, return household id
    public Long createHousehold(HouseholdType householdType) {
        HouseholdEntity householdEntity = new HouseholdEntity(householdType);
        return householdRepository.save(householdEntity).getId();
    }

    //create household, return household id
    public void deleteHousehold(Long householdId) {
        HouseholdEntity householdEntity = findHouseholdEntity(householdId);
        memberService.deleteMemberList(householdEntity.getMemberEntityList());
        householdRepository.delete(householdEntity);
    }

    public List<Household> findAllHousehold() {
        List<HouseholdEntity> householdEntityList = householdRepository.findAll();
        List<Household> householdList = new ArrayList<>();
        // For each household, convert its details + member details
        for (HouseholdEntity householdEntity : householdEntityList) {
            Household household = new Household(householdEntity.getId(), householdEntity.getHouseholdType());
            for (MemberEntity memberEntity : householdEntity.getMemberEntityList()) {
                Member member = MemberMapper.toModel.apply(memberEntity);
                household.getMemberList().add(member);
            }
            householdList.add(household);
        }
        return householdList;
    }

    public Household findHousehold(Long householdId) {
        HouseholdEntity householdEntity = findHouseholdEntity(householdId);
        Household household = new Household(householdEntity.getId(), householdEntity.getHouseholdType());
        // For the household, convert its details and the member details
        for (MemberEntity memberEntity : householdEntity.getMemberEntityList()) {
            Member member = MemberMapper.toModel.apply(memberEntity);
            household.getMemberList().add(member);
        }
        return household;
    }

    public HouseholdEntity findHouseholdEntity(Long householdId) {
        Optional<HouseholdEntity> optionalHouseholdEntity = householdRepository.findById(householdId);
        return optionalHouseholdEntity.orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid household ID"));
    }

    public List<Household> getStudentEncouragementBonusRecipients(Long ageLessThan, Long annualIncomeLessThan) {
        LocalDate today = LocalDate.now();
        List<HouseholdEntity> householdEntityList = householdRepository.selectStudentEncouragementBonusHousehold(today.minusYears(ageLessThan), annualIncomeLessThan);
        List<Household> householdList = new ArrayList<>();
        for (HouseholdEntity householdEntity : householdEntityList) {
            //Convert household entity to household and populate members who are less than {ageLessThan} years old
            Household household = new Household(householdEntity.getId(), householdEntity.getHouseholdType());
            //Filter members who are less than 16 years old
            household.setMemberList(householdEntity.getMemberEntityList()
                    .stream()
                    .filter(memberEntity -> memberEntity.getDateOfBirth().until(LocalDate.now()).minusYears(ageLessThan).isNegative())
                    .map(MemberMapper.toModel)
                    .collect(Collectors.toList()));
            householdList.add(household);
        }
        return householdList;
    }

    public List<Household> getFamilyTogethernessSchemeRecipients(Long ageLessThan) {
        LocalDate today = LocalDate.now();
        List<HouseholdEntity> householdEntityList = householdRepository.selectFamilyTogethernessSchemeHouseholdV2(today.minusYears(ageLessThan));
        List<Household> householdList = new ArrayList<>();
        for (HouseholdEntity householdEntity : householdEntityList) {
            //Create spouse hash map to store members who are married
            HashMap<Long, Member> spouseHashMap = new HashMap<>();
            //Convert household and household members
            Household household = new Household(householdEntity.getId(), householdEntity.getHouseholdType());
            List<Member> memberList = householdEntity.getMemberEntityList()
                    .stream()
                    .map(MemberMapper.toModel)
                    .collect(Collectors.toList());
            //Filter members who are less than {ageLessThan} years old
            //Or are married to another member of the family
            for (Member member : memberList) {
                //if married:
                if(member.getSpouseId() != null && member.getMaritalStatus() == MaritalStatus.MARRIED) {
                    //if spouseHashMap does not contain member's spouse, add him to spouseHashMap
                    //else, take both members and add them to the household list
                    if(!spouseHashMap.containsKey(member.getSpouseId()))
                        spouseHashMap.put(member.getId(), member);
                    else {
                        household.getMemberList().add(spouseHashMap.get(member.getSpouseId()));
                        household.getMemberList().add(member);
                    }
                } else if(member.getDateOfBirth().until(LocalDate.now()).minusYears(ageLessThan).isNegative()) {
                    household.getMemberList().add(member);
                }
            }
            householdList.add(household);
        }
        return householdList;
    }

    public List<Household> getElderBonusRecipients(Long ageMoreThan) {
        LocalDate today = LocalDate.now();
        List<HouseholdEntity> householdEntityList = householdRepository.selectElderBonusHousehold(today.minusYears(ageMoreThan));
        List<Household> householdList = new ArrayList<>();
        for (HouseholdEntity householdEntity : householdEntityList) {
            Household household = new Household(householdEntity.getId(), householdEntity.getHouseholdType());
            household.setMemberList(householdEntity.getMemberEntityList()
                    .stream()
                    .filter(memberEntity -> !memberEntity.getDateOfBirth().until(LocalDate.now()).minusYears(ageMoreThan).isNegative())
                    .map(MemberMapper.toModel)
                    .collect(Collectors.toList()));
            householdList.add(household);
        }
        return householdList;
    }

    public List<Household> getBabySunshineGrantRecipients(Long ageLessThan) {
        LocalDate today = LocalDate.now();
        List<HouseholdEntity> householdEntityList = householdRepository.selectBabySunshineGrantHousehold(today.minusYears(ageLessThan));
        List<Household> householdList = new ArrayList<>();
        for (HouseholdEntity householdEntity : householdEntityList) {
            Household household = new Household(householdEntity.getId(), householdEntity.getHouseholdType());
            household.setMemberList(householdEntity.getMemberEntityList()
                    .stream()
                    .filter(memberEntity -> memberEntity.getDateOfBirth().until(LocalDate.now()).minusYears(ageLessThan).isNegative())
                    .map(MemberMapper.toModel)
                    .collect(Collectors.toList()));
            householdList.add(household);
        }
        return householdList;
    }

    public List<Household> getYoloGSTGrantRecipients(Long annualIncomeLessThan) {
        List<HouseholdEntity> householdEntityList = householdRepository.selectYoloGSTGrantHousehold(annualIncomeLessThan);
        List<Household> householdList = new ArrayList<>();
        for (HouseholdEntity householdEntity : householdEntityList) {
            Household household = new Household(householdEntity.getId(), householdEntity.getHouseholdType());
            household.setMemberList(householdEntity.getMemberEntityList()
                    .stream()
                    .map(MemberMapper.toModel)
                    .collect(Collectors.toList()));
            householdList.add(household);
        }
        return householdList;
    }

}
