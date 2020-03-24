package com.assessment.controller;

import com.assessment.entity.enums.HouseholdType;
import com.assessment.model.Household;
import com.assessment.request.AddMemberToHouseholdRequest;
import com.assessment.service.HouseholdService;
import com.assessment.service.MemberService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
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
    private String createHousehold(@RequestParam HouseholdType householdType) {
        return "Successfully created household: ID " + householdService.createHousehold(householdType);
    }

    @PostMapping("/member")
    @ApiOperation("Add Member to Household")
    private String createMember(@RequestBody AddMemberToHouseholdRequest addMemberToHouseholdRequest) {
        return "Successfully created member: ID " + memberService.createHouseholdMember(addMemberToHouseholdRequest.getHouseholdId(), addMemberToHouseholdRequest.getMember());
    }

    @GetMapping("/household")
    @ApiOperation("Create Household")
    private List<Household> getAllHousehold() {
        return householdService.findAllHousehold();
    }

    @GetMapping("/household/{householdId}")
    @ApiOperation("Add Member to Household")
    private Household getHousehold(@PathVariable Long householdId) {
        return householdService.findHousehold(householdId);
    }


}
