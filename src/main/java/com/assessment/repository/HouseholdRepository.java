package com.assessment.repository;

import com.assessment.entity.HouseholdEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
@Transactional
@Component
public interface HouseholdRepository extends JpaRepository<HouseholdEntity, Long> {

}
