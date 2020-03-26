package com.assessment.service;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest
class HouseholdServiceTest {

    @Autowired
    private HouseholdService householdService;

    @Autowired
    private MemberService memberService;

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void createHousehold() {
    }

    @Test
    void deleteHousehold() {
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