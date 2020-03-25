package com.assessment.controller;

import com.assessment.entity.enums.HouseholdType;
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

    @GetMapping("studentEncouragementBonusGrant")
    @ApiOperation("Search for family members within households eligible for Student Encouragement Bonus")
    public List<Household> getStudentEncouragementBonusGrantRecipients(@RequestParam Long ageLessThan, @RequestParam Long annualIncomeLessThan) {
        return householdService.getStudentEncouragementBonusRecipients(ageLessThan, annualIncomeLessThan);
    }

    @GetMapping("familyTogethernessSchemeGrant")
    @ApiOperation("Search for family members within households eligible for Family Togetherness Scheme")
    public List<Household> getFamilyTogethernessSchemeGrantRecipients(@RequestParam Long ageLessThan) {
        return householdService.getFamilyTogethernessSchemeRecipients(ageLessThan);
    }
}
