package com.assessment.entity;

import com.assessment.entity.enums.Gender;
import com.assessment.entity.enums.MaritalStatus;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Objects;

@Entity
public class MemberEntity {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    @JoinColumn(nullable = false)
    @ManyToOne
    private HouseholdEntity householdEntity;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private MaritalStatus maritalStatus;

    @JoinColumn(name = "spouse_id")
    @OneToOne
    private MemberEntity spouse;

    @Column(nullable = false)
    private Integer annualIncome;

    @Column(nullable = false)
    private LocalDate dateOfBirth;

    public MemberEntity() {
    }

    public MemberEntity(String name, Gender gender, MaritalStatus maritalStatus, Integer annualIncome, LocalDate dateOfBirth) {
        this.name = name;
        this.gender = gender;
        this.maritalStatus = maritalStatus;
        this.annualIncome = annualIncome;
        this.dateOfBirth = dateOfBirth;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public HouseholdEntity getHouseholdEntity() {
        return householdEntity;
    }

    public void setHouseholdEntity(HouseholdEntity householdEntity) {
        this.householdEntity = householdEntity;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public MaritalStatus getMaritalStatus() {
        return maritalStatus;
    }

    public void setMaritalStatus(MaritalStatus maritalStatus) {
        this.maritalStatus = maritalStatus;
    }

    public MemberEntity getSpouse() {
        return spouse;
    }

    public void setSpouse(MemberEntity spouse) {
        this.spouse = spouse;
    }

    public Integer getAnnualIncome() {
        return annualIncome;
    }

    public void setAnnualIncome(Integer annualIncome) {
        this.annualIncome = annualIncome;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MemberEntity member = (MemberEntity) o;
        return Objects.equals(id, member.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
