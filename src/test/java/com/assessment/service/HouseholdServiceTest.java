package com.assessment.service;

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

import static org.junit.jupiter.api.Assertions.assertEquals;


@SpringBootTest
@ActiveProfiles("test")
@Transactional
class HouseholdServiceTest {

    @Autowired
    private HouseholdService householdService;

    @Autowired
    private MemberService memberService;

    @Autowired
    private HouseholdRepository householdRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Test
    void createHousehold() {
        assertEquals(0, householdRepository.findAll().size());
        householdService.createHousehold(HouseholdType.CONDOMINIUM);
        assertEquals(1, householdRepository.findAll().size());
    }

    @Test
    void deleteHousehold() {
        assertEquals(0, householdRepository.findAll().size());
        Long householdId = householdService.createHousehold(HouseholdType.CONDOMINIUM);
        memberService.createHouseholdMember(householdId, new Member(
                0L,
                "Name",
                Gender.MALE,
                MaritalStatus.SINGLE,
                0L,
                100000,
                LocalDate.now().minusYears(25)
        ));
        assertEquals(1, householdRepository.findAll().size());
        assertEquals(1, memberRepository.findAll().size());
        householdService.deleteHousehold(householdId);
        assertEquals(0, householdRepository.findAll().size());
        assertEquals(0, memberRepository.findAll().size());
    }

    @Test
    void findAllHousehold() {
    }

    @Test
    void findHousehold() {
    }

    @Test
    void findHouseholdEntity() {
    }

    @Test
    void getStudentEncouragementBonusRecipients() {
    }

    @Test
    void getFamilyTogethernessSchemeRecipients() {
    }

    @Test
    void getElderBonusRecipients() {
    }

    @Test
    void getBabySunshineGrantRecipients() {
    }

    @Test
    void getYoloGSTGrantRecipients() {
    }
}