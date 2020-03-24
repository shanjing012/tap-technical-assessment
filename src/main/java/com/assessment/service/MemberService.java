package com.assessment.service;

import com.assessment.entity.HouseholdEntity;
import com.assessment.entity.MemberEntity;
import com.assessment.model.Member;
import com.assessment.repository.HouseholdRepository;
import com.assessment.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Component
public class MemberService {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private HouseholdService householdService;

    public Long createHouseholdMember(Long householdId, Member member) {
        //find household to ensure it exists
        HouseholdEntity householdEntity = householdService.findHouseholdEntity(householdId);

        MemberEntity memberEntity = new MemberEntity(
                member.getName(),
                member.getGender(),
                member.getMaritalStatus(),
                member.getAnnualIncome(),
                member.getDateOfBirth()
        );

        //find member to ensure it exists (family can stay apart but be spouse)
        if(member.getSpouseId() != null) {
            MemberEntity spouseEntity = findMemberEntity(member.getSpouseId());
            //within persistence context, can set

            //dont make 3 people marry each other
            if(spouseEntity.getSpouse() != null)
                spouseEntity.getSpouse().setSpouse(null);

            memberEntity.setSpouse(spouseEntity);
            spouseEntity.setSpouse(memberEntity);
        }

        //associate household
        householdEntity.getMemberEntityList().add(memberEntity);
        memberEntity.setHouseholdEntity(householdEntity);

        return memberRepository.save(memberEntity).getId();
    }

    public MemberEntity findMemberEntity(Long memberId) {
        Optional<MemberEntity> optionalMemberEntity = memberRepository.findById(memberId);
        return optionalMemberEntity.orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid member ID"));
    }
}