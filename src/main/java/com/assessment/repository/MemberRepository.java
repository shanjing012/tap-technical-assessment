package com.assessment.repository;

import com.assessment.entity.MemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
@Transactional
@Component
public interface MemberRepository extends JpaRepository<MemberEntity, Long> {

}
