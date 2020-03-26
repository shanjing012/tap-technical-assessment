package com.assessment.mapper;

import com.assessment.entity.MemberEntity;
import com.assessment.model.Member;

import java.util.function.Function;

public class MemberMapper {
    public static final Function<MemberEntity, Member> toModel =
            memberEntity -> new Member(
                    memberEntity.getId(),
                    memberEntity.getName(),
                    memberEntity.getGender(),
                    memberEntity.getMaritalStatus(),
                    memberEntity.getSpouse() != null ? memberEntity.getSpouse().getId() : null,
                    memberEntity.getAnnualIncome(),
                    memberEntity.getDateOfBirth()
            );
}
