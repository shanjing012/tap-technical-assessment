package com.assessment.entity;

import com.assessment.entity.enums.HouseholdType;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
public class Household {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private HouseholdType householdType;

    @OneToMany(mappedBy = "household")
    private List<Member> memberList;

    public Household(HouseholdType householdType) {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Household household = (Household) o;
        return Objects.equals(id, household.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
