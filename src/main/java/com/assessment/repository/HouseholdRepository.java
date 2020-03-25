package com.assessment.repository;

import com.assessment.entity.HouseholdEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
@Component
public interface HouseholdRepository extends JpaRepository<HouseholdEntity, Long> {

    @Query(
            "SELECT h FROM HouseholdEntity h INNER JOIN h.memberEntityList m " +
            "GROUP BY h.id " +
            "HAVING CAST(SUM(m.annualIncome) as long) < :annualIncome " +
            "AND CAST(MIN(DATE_FORMAT(FROM_DAYS(DATEDIFF(now(), m.dateOfBirth)), '%Y')) as long) < :age"
    )
    List<HouseholdEntity> selectStudentEncouragementBonusHousehold(Long age, Long annualIncome);

    @Query(
            "SELECT h FROM HouseholdEntity h INNER JOIN h.memberEntityList m " +
                    "GROUP BY h.id " +
                    "HAVING CAST(SUM(m.annualIncome) as long) < :annualIncome " +
                    "AND MIN(DATE_FORMAT(FROM_DAYS(DATEDIFF(now(), m.dateOfBirth)), '%Y')) < :age"
    )
    List<HouseholdEntity> selectFamilyTogethernessSchemeHousehold(Integer annualIncome, Long age);

    List<HouseholdEntity> selectElderBonusHousehold(Integer annualIncome, Long age);

    List<HouseholdEntity> selectBabySunshineGrantHousehold(Integer annualIncome, Long age);

    List<HouseholdEntity> selectYoloGSTGrantHousehold(Integer annualIncome, Long age);

}
