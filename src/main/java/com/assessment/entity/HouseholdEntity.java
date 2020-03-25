package com.assessment.entity;

import com.assessment.entity.enums.HouseholdType;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
public class HouseholdEntity {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private HouseholdType householdType;

    @OneToMany(mappedBy = "householdEntity")
    private List<MemberEntity> memberEntityList;

    public HouseholdEntity() {
    }

    public HouseholdEntity(HouseholdType householdType) {
        this.householdType = householdType;
        this.memberEntityList = new ArrayList<>();
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

    public List<MemberEntity> getMemberEntityList() {
        return memberEntityList;
    }

    public void setMemberEntityList(List<MemberEntity> memberEntityList) {
        this.memberEntityList = memberEntityList;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        HouseholdEntity householdEntity = (HouseholdEntity) o;
        return Objects.equals(id, householdEntity.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
