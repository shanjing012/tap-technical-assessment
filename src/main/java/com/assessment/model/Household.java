package com.assessment.model;

import com.assessment.entity.enums.HouseholdType;

import java.util.ArrayList;
import java.util.List;

public class Household {

    private Long id;
    private HouseholdType householdType;
    private List<Member> memberList;

    public Household(Long id, HouseholdType householdType) {
        this.id = id;
        this.householdType = householdType;
        this.memberList = new ArrayList<>();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public HouseholdType getHouseholdType() {
        return householdType;
    }

    public void setHouseholdType(HouseholdType householdType) {
        this.householdType = householdType;
    }

    public List<Member> getMemberList() {
        return memberList;
    }

    public void setMemberList(List<Member> memberList) {
        this.memberList = memberList;
    }
}
