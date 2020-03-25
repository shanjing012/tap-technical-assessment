package com.assessment.controller;

import com.assessment.entity.enums.HouseholdType;
import com.assessment.entity.enums.MaritalStatus;
import com.assessment.model.Household;
import com.assessment.request.AddMemberToHouseholdRequest;
import com.assessment.service.HouseholdService;
import com.assessment.service.MemberService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ApiController {

    @Autowired
    private HouseholdService householdService;

    @Autowired
    private MemberService memberService;

    @PostMapping("/household")
    @ApiOperation("Create Household")
    public String createHousehold(@RequestParam HouseholdType householdType) {
        return "Successfully created household: ID " + householdService.createHousehold(householdType);
    }

    @PostMapping("/member")
    @ApiOperation("Add Member to Household")
    public String createMember(@RequestBody AddMemberToHouseholdRequest addMemberToHouseholdRequest) {
        return "Successfully created member: ID " + memberService.createHouseholdMember(addMemberToHouseholdRequest.getHouseholdId(), addMemberToHouseholdRequest.getMember());
    }

    @GetMapping("/household")
    @ApiOperation("Show all Households")
    public List<Household> getAllHousehold() {
        return householdService.findAllHousehold();
    }

    @GetMapping("/household/{householdId}")
    @ApiOperation("Show a Household")
    public Household getHousehold(@PathVariable Long householdId) {
        return householdService.findHousehold(householdId);
    }

    @GetMapping("studentEncouragementBonus")
    @ApiOperation("Search for family members within households eligible for Student Encouragement Bonus")
    public List<Household> getStudentEncouragementBonusRecipients(@RequestParam Long ageLessThan, @RequestParam Long annualIncomeLessThan) {
        return householdService.getStudentEncouragementBonusRecipients(ageLessThan, annualIncomeLessThan);
    }

    @GetMapping("familyTogethernessScheme")
    @ApiOperation("Search for family members within households eligible for Family Togetherness Scheme")
    public List<Household> getFamilyTogethernessSchemeRecipients(@RequestParam Long ageLessThan) {
        return householdService.getFamilyTogethernessSchemeRecipients(ageLessThan);
    }

    @GetMapping("elderBonus")
    @ApiOperation("Search for family members within HDB households eligible for Elder Bonus")
    public List<Household> getElderBonusRecipients(@RequestParam Long ageMoreThan) {
        return householdService.getElderBonusRecipients(ageMoreThan);
    }

    @GetMapping("babySunshineGrant")
    @ApiOperation("Search for family members within households eligible for Baby Sunshine Grant")
    public List<Household> getBabySunshineGrantRecipients(@RequestParam Long ageLessThan) {
        return householdService.getBabySunshineGrantRecipients(ageLessThan);
    }

    @GetMapping("yoloGstGrant")
    @ApiOperation("Search for family members within HDB households eligible for YOLO GST Grant")
    public List<Household> getYoloGSTGrantRecipients(@RequestParam Long annualIncomeLessThan) {
        return householdService.getYoloGSTGrantRecipients(annualIncomeLessThan);
    }

    @DeleteMapping("member")
    @ApiOperation("Delete a family member (Provide the family member's spouse's new marital if applicable)")
    public void deleteMember(@RequestParam Long memberId, @RequestParam MaritalStatus spouseMaritalStatus) {
        memberService.deleteMember(memberId, spouseMaritalStatus);
    }

    @DeleteMapping("household")
    @ApiOperation("Delete a household and all associated family members")
    public void deleteHousehold(@RequestParam Long householdId) {
        householdService.deleteHousehold(householdId);
    }
}
