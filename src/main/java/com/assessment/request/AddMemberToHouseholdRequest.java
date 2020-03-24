package com.assessment.request;

import com.assessment.model.Member;

public class AddMemberToHouseholdRequest {
    private Long householdId;
    private Member member;

    public Long getHouseholdId() {
        return householdId;
    }

    public void setHouseholdId(Long householdId) {
        this.householdId = householdId;
    }

    public Member getMember() {
        return member;
    }

    public void setMember(Member member) {
        this.member = member;
    }
}
