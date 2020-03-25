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
            "SELECT h FROM HouseholdEntity h " +
                    "INNER JOIN h.memberEntityList m1 " +
                    "INNER JOIN h.memberEntityList m2 " +
                    "WHERE m1.spouse = m2 " +
                    "AND m1.maritalStatus = 'MARRIED' " +
                    "AND m2.maritalStatus = 'MARRIED' " +
                    "GROUP BY h.id"
    )
    List<HouseholdEntity> selectFamilyTogethernessSchemeHousehold(Long age);

    @Query(
            "SELECT h FROM HouseholdEntity h " +
                    "INNER JOIN h.memberEntityList m1 " +
                    "INNER JOIN h.memberEntityList m2 " +
                    "INNER JOIN h.memberEntityList m " +
                    "WHERE m1.spouse = m2 " +
                    "AND m1.maritalStatus = 'MARRIED' " +
                    "AND m2.maritalStatus = 'MARRIED' " +
                    "GROUP BY h.id " +
                    "HAVING CAST(MIN(DATE_FORMAT(FROM_DAYS(DATEDIFF(now(), m.dateOfBirth)), '%Y')) as long) < :age"
    )
    List<HouseholdEntity> selectFamilyTogethernessSchemeHouseholdV2(Long age);

    @Query(
            "SELECT h FROM HouseholdEntity h " +
                    "INNER JOIN h.memberEntityList m " +
                    "WHERE CAST(DATE_FORMAT(FROM_DAYS(DATEDIFF(now(), m.dateOfBirth)), '%Y') as long) > :age " +
                    "GROUP BY h.id"
    )
    List<HouseholdEntity> selectElderBonusHousehold(Long age);

    @Query(
            "SELECT h FROM HouseholdEntity h " +
                    "INNER JOIN h.memberEntityList m " +
                    "WHERE CAST(DATE_FORMAT(FROM_DAYS(DATEDIFF(now(), m.dateOfBirth)), '%Y') as long) < :age " +
                    "GROUP BY h.id"
    )
    List<HouseholdEntity> selectBabySunshineGrantHousehold(Long age);

    @Query(
            "SELECT h FROM HouseholdEntity h INNER JOIN h.memberEntityList m " +
                    "GROUP BY h.id " +
                    "HAVING CAST(SUM(m.annualIncome) as long) < :annualIncome"
    )
    List<HouseholdEntity> selectYoloGSTGrantHousehold(Long annualIncome);

}
