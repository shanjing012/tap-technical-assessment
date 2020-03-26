package com.assessment.service;

import com.assessment.entity.HouseholdEntity;
import com.assessment.entity.MemberEntity;
import com.assessment.entity.enums.Gender;
import com.assessment.entity.enums.HouseholdType;
import com.assessment.entity.enums.MaritalStatus;
import com.assessment.model.Member;
import com.assessment.repository.HouseholdRepository;
import com.assessment.repository.MemberRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
class MemberServiceTest {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private HouseholdRepository householdRepository;

    @Autowired
    private MemberService memberService;

    @Autowired
    private HouseholdService householdService;

    private HouseholdEntity householdEntity;
    private MemberEntity member1;
    private MemberEntity member2;
    private MemberEntity member3;
    private MemberEntity member4;

    @BeforeEach
    public void generateTestHousehold() {
        householdEntity = new HouseholdEntity(HouseholdType.HDB);
        member1 = new MemberEntity("Father A", Gender.MALE, MaritalStatus.MARRIED, 30000, LocalDate.now().minusYears(30));
        member2 = new MemberEntity("Mother A", Gender.FEMALE, MaritalStatus.MARRIED, 30000, LocalDate.now().minusYears(28));
        member3 = new MemberEntity("Son A", Gender.MALE, MaritalStatus.SINGLE, 0, LocalDate.now().minusYears(16).minusDays(1));
        member4 = new MemberEntity("Daughter A", Gender.FEMALE, MaritalStatus.SINGLE, 0, LocalDate.now().minusYears(16).plusDays(1));
        householdEntity.getMemberEntityList().add(member1);
        householdEntity.getMemberEntityList().add(member2);
        householdEntity.getMemberEntityList().add(member3);
        householdEntity.getMemberEntityList().add(member4);
        member1.setHouseholdEntity(householdEntity);
        member2.setHouseholdEntity(householdEntity);
        member3.setHouseholdEntity(householdEntity);
        member4.setHouseholdEntity(householdEntity);
        householdEntity = householdRepository.save(householdEntity);
        member1 = memberRepository.save(member1);
        member2 = memberRepository.save(member2);
        member3 = memberRepository.save(member3);
        member4 = memberRepository.save(member4);
        member1.setSpouse(member2);
        member2.setSpouse(member1);
        member1 = memberRepository.save(member1);
        member2 = memberRepository.save(member2);
    }

    @Test
    void createHouseholdMember() {
        int householdMemberCount = householdService.findHouseholdEntity(householdEntity.getId()).getMemberEntityList().size();
        Long newMemberId = memberService.createHouseholdMember(householdEntity.getId(), new Member(
                0L,
                "Name",
                Gender.MALE,
                MaritalStatus.SINGLE,
                0L,
                100000,
                LocalDate.now().minusYears(25)
        ));
        int newHouseholdMemberCount = householdService.findHouseholdEntity(householdEntity.getId()).getMemberEntityList().size();
        assertNotEquals(householdMemberCount, newHouseholdMemberCount);
    }

    @Test
    void findMemberEntity() {
        MemberEntity tempMember = memberService.findMemberEntity(member2.getId());
        assertEquals(member2.getName(), tempMember.getName());
    }

    @Test
    void deleteMemberFather() {
        memberService.deleteMember(member1.getId(), MaritalStatus.WIDOWED);
        member2 = memberService.findMemberEntity(member2.getId());
        assertNull(member2.getSpouse());
        assertEquals(MaritalStatus.WIDOWED, member2.getMaritalStatus());
    }

    @Test
    void deleteMemberSon() {
        int householdMemberCount = householdEntity.getMemberEntityList().size();
        memberService.deleteMember(member3.getId(), null);
        assertEquals(householdMemberCount - 1, householdRepository.findById(householdEntity.getId()).get().getMemberEntityList().size());
    }
}