package com.assessment.service;

import com.assessment.entity.HouseholdEntity;
import com.assessment.entity.MemberEntity;
import com.assessment.entity.enums.Gender;
import com.assessment.entity.enums.HouseholdType;
import com.assessment.entity.enums.MaritalStatus;
import com.assessment.model.Household;
import com.assessment.model.Member;
import com.assessment.repository.HouseholdRepository;
import com.assessment.repository.MemberRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.event.annotation.BeforeTestClass;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;


@SpringBootTest
@ActiveProfiles("test")
@Transactional
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class HouseholdServiceTest {

    @Autowired
    private HouseholdService householdService;

    @Autowired
    private MemberService memberService;

    @Autowired
    private HouseholdRepository householdRepository;

    @Autowired
    private MemberRepository memberRepository;

    @BeforeAll
    public void generateTestHousehold() {
        createHouseholdA();
        createHouseholdB();
        createHouseholdC();
        createHouseholdD();
        createHouseholdE();
    }

    @Test
    public void createHousehold() {
        int originalSize = householdRepository.findAll().size();
        householdService.createHousehold(HouseholdType.CONDOMINIUM);
        assertEquals(originalSize + 1, householdRepository.findAll().size());
    }

    @Test
    public void deleteHousehold() {
        int originalSize = householdRepository.findAll().size();
        int originalMemberSize = memberRepository.findAll().size();
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
        assertEquals(originalSize + 1, householdRepository.findAll().size());
        assertEquals(originalMemberSize + 1, memberRepository.findAll().size());
        householdService.deleteHousehold(householdId);
        assertEquals(originalSize, householdRepository.findAll().size());
        assertEquals(originalMemberSize, memberRepository.findAll().size());
    }

    @Test
    public void findAllHousehold() {
        List<Household> householdList = householdService.findAllHousehold();
        assertEquals(5, householdList.size());
    }

    @Test
    public void findHousehold() {
        Household household = householdService.findHousehold(1L);
        assertEquals(4, household.getMemberList().size());
        assertEquals("Father A", household.getMemberList().get(0).getName());
    }

    @Test
    public void getStudentEncouragementBonusRecipients() {
        List<Household> householdList = householdService.getStudentEncouragementBonusRecipients(16L, 150000L);
        assertEquals(1, householdList.size());
        assertEquals("Daughter A", householdList.get(0).getMemberList().get(0).getName());
    }

    @Test
    public void getFamilyTogethernessSchemeRecipients() {
        List<Household> householdList = householdService.getFamilyTogethernessSchemeRecipients(18L);
        assertEquals(2, householdList.size());
        assertEquals("Son A", householdList.get(0).getMemberList().get(2).getName());
        assertEquals(4, householdList.get(1).getMemberList().size());
    }

    @Test
    public void getElderBonusRecipients() {
        List<Household> householdList = householdService.getElderBonusRecipients(50L);
        assertEquals("Mother D", householdList.get(0).getMemberList().get(0).getName());
    }

    @Test
    public void getBabySunshineGrantRecipients() {
        List<Household> householdList = householdService.getBabySunshineGrantRecipients(5L);
        assertEquals("Daughter E Younger", householdList.get(0).getMemberList().get(0).getName());
    }

    @Test
    public void getYoloGSTGrantRecipients() {
        List<Household> householdList = householdService.getYoloGSTGrantRecipients(100000L);
        assertEquals(3, householdList.size());
    }

    /**
     * Father - Exactly 30
     * Mother - Exactly 28
     * Son - Exactly 16
     * Daughter - 15
     * Annual Income: 80000
     * Have Married Member: Yes
     * Stays In: HDB
     */
    private void createHouseholdA() {
        System.out.println(LocalDate.now());
        HouseholdEntity householdEntity1 = new HouseholdEntity(HouseholdType.HDB);
        MemberEntity member1 = new MemberEntity("Father A", Gender.MALE, MaritalStatus.MARRIED, 30000, LocalDate.now().minusYears(30));
        MemberEntity member2 = new MemberEntity("Mother A", Gender.FEMALE, MaritalStatus.MARRIED, 30000, LocalDate.now().minusYears(28));
        MemberEntity member3 = new MemberEntity("Son A", Gender.MALE, MaritalStatus.SINGLE, 10000, LocalDate.now().minusYears(16));
        MemberEntity member4 = new MemberEntity("Daughter A", Gender.FEMALE, MaritalStatus.SINGLE, 10000, LocalDate.now().minusYears(16).plusDays(1));
        householdEntity1.getMemberEntityList().add(member1);
        householdEntity1.getMemberEntityList().add(member2);
        householdEntity1.getMemberEntityList().add(member3);
        householdEntity1.getMemberEntityList().add(member4);
        member1.setHouseholdEntity(householdEntity1);
        member2.setHouseholdEntity(householdEntity1);
        member3.setHouseholdEntity(householdEntity1);
        member4.setHouseholdEntity(householdEntity1);
        householdEntity1 = householdRepository.save(householdEntity1);
        member1 = memberRepository.save(member1);
        member2 = memberRepository.save(member2);
        memberRepository.save(member3);
        memberRepository.save(member4);
        member1.setSpouse(member2);
        member2.setSpouse(member1);
        member1 = memberRepository.save(member1);
        member2 = memberRepository.save(member2);
    }

    /**
     * Mother - Exactly 28
     * Son - Exactly 16
     * Daughter - Exactly 16
     * Annual Income: 90000
     * Have Married Member: No
     * Stays In: LANDED
     */
    private void createHouseholdB() {
        HouseholdEntity householdEntity1 = new HouseholdEntity(HouseholdType.LANDED);
        MemberEntity member1 = new MemberEntity("Mother B", Gender.FEMALE, MaritalStatus.DIVORCED, 70000, LocalDate.now().minusYears(28));
        MemberEntity member2 = new MemberEntity("Son B", Gender.MALE, MaritalStatus.SINGLE, 20000, LocalDate.now().minusYears(16));
        MemberEntity member3 = new MemberEntity("Daughter B", Gender.FEMALE, MaritalStatus.SINGLE, 0, LocalDate.now().minusYears(16));
        householdEntity1.getMemberEntityList().add(member1);
        householdEntity1.getMemberEntityList().add(member2);
        householdEntity1.getMemberEntityList().add(member3);
        member1.setHouseholdEntity(householdEntity1);
        member2.setHouseholdEntity(householdEntity1);
        member3.setHouseholdEntity(householdEntity1);
        householdRepository.save(householdEntity1);
        memberRepository.save(member1);
        memberRepository.save(member2);
        memberRepository.save(member3);
    }

    /**
     * Father - Exactly 30
     * Mother - Exactly 28
     * Grandma - Exact 50
     * Annual Income: 60000
     * Have Married Member: Yes
     * Stays In: HDB
     */
    private void createHouseholdC() {
        HouseholdEntity householdEntity1 = new HouseholdEntity(HouseholdType.HDB);
        MemberEntity member1 = new MemberEntity("Father C", Gender.MALE, MaritalStatus.MARRIED, 30000, LocalDate.now().minusYears(30));
        MemberEntity member2 = new MemberEntity("Mother C", Gender.FEMALE, MaritalStatus.MARRIED, 30000, LocalDate.now().minusYears(28));
        MemberEntity member3 = new MemberEntity("Grandma C", Gender.FEMALE, MaritalStatus.MARRIED, 30000, LocalDate.now().minusYears(50));
        householdEntity1.getMemberEntityList().add(member1);
        householdEntity1.getMemberEntityList().add(member2);
        householdEntity1.getMemberEntityList().add(member3);
        member1.setHouseholdEntity(householdEntity1);
        member2.setHouseholdEntity(householdEntity1);
        member3.setHouseholdEntity(householdEntity1);
        householdRepository.save(householdEntity1);
        member1 = memberRepository.save(member1);
        member2 = memberRepository.save(member2);
        member3 = memberRepository.save(member3);
        member1.setSpouse(member2);
        member2.setSpouse(member1);
        member1 = memberRepository.save(member1);
        member2 = memberRepository.save(member2);
    }

    /**
     * Father - 49
     * Mother - 50
     * Son - Exactly 30
     * Annual Income: 90000
     * Have Married Member: Yes
     * Stays In: HDB
     */
    private void createHouseholdD() {
        HouseholdEntity householdEntity1 = new HouseholdEntity(HouseholdType.HDB);
        MemberEntity member1 = new MemberEntity("Father D", Gender.MALE, MaritalStatus.MARRIED, 30000, LocalDate.now().minusYears(50).plusDays(1));
        MemberEntity member2 = new MemberEntity("Mother D", Gender.FEMALE, MaritalStatus.MARRIED, 30000, LocalDate.now().minusYears(50).minusDays(1));
        MemberEntity member3 = new MemberEntity("Son D", Gender.MALE, MaritalStatus.SINGLE, 30000, LocalDate.now().minusYears(30));
        householdEntity1.getMemberEntityList().add(member1);
        householdEntity1.getMemberEntityList().add(member2);
        householdEntity1.getMemberEntityList().add(member3);
        member1.setHouseholdEntity(householdEntity1);
        member2.setHouseholdEntity(householdEntity1);
        member3.setHouseholdEntity(householdEntity1);
        householdRepository.save(householdEntity1);
        member1 = memberRepository.save(member1);
        member2 = memberRepository.save(member2);
        member3 = memberRepository.save(member3);
        member1.setSpouse(member2);
        member2.setSpouse(member1);
        member1 = memberRepository.save(member1);
        member2 = memberRepository.save(member2);
    }

    /**
     * Father - Exactly 30
     * Mother - Exactly 30
     * Daughter - 4
     * Daughter - Exactly 5
     * Son - 18
     * Annual Income: 152000
     * Have Married Member: Yes
     * Stays In: CONDOMINIUM
     */
    private void createHouseholdE() {
        HouseholdEntity householdEntity1 = new HouseholdEntity(HouseholdType.CONDOMINIUM);
        MemberEntity member1 = new MemberEntity("Father E", Gender.MALE, MaritalStatus.MARRIED, 100000, LocalDate.now().minusYears(30));
        MemberEntity member2 = new MemberEntity("Mother E", Gender.FEMALE, MaritalStatus.MARRIED, 52000, LocalDate.now().minusYears(30));
        MemberEntity member3 = new MemberEntity("Daughter E Younger", Gender.FEMALE, MaritalStatus.SINGLE, 0, LocalDate.now().minusYears(5).plusDays(1));
        MemberEntity member4 = new MemberEntity("Daughter E", Gender.FEMALE, MaritalStatus.SINGLE, 0, LocalDate.now().minusYears(5));
        MemberEntity member5 = new MemberEntity("Son E", Gender.MALE, MaritalStatus.SINGLE, 0, LocalDate.now().minusYears(18));
        householdEntity1.getMemberEntityList().add(member1);
        householdEntity1.getMemberEntityList().add(member2);
        householdEntity1.getMemberEntityList().add(member3);
        householdEntity1.getMemberEntityList().add(member4);
        householdEntity1.getMemberEntityList().add(member5);
        member1.setHouseholdEntity(householdEntity1);
        member2.setHouseholdEntity(householdEntity1);
        member3.setHouseholdEntity(householdEntity1);
        member4.setHouseholdEntity(householdEntity1);
        member5.setHouseholdEntity(householdEntity1);
        householdRepository.save(householdEntity1);
        member1 = memberRepository.save(member1);
        member2 = memberRepository.save(member2);
        member3 = memberRepository.save(member3);
        member4 = memberRepository.save(member4);
        member5 = memberRepository.save(member5);
        member1.setSpouse(member2);
        member2.setSpouse(member1);
        member1 = memberRepository.save(member1);
        member2 = memberRepository.save(member2);
    }
}