package com.msspecialdaydiscount.repository;

import com.msspecialdaydiscount.entities.SpecialDayDiscountEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SpecialDayDiscountRepository extends JpaRepository<SpecialDayDiscountEntity,Long> {
    Optional<SpecialDayDiscountEntity> findByType(String type);
    Optional<SpecialDayDiscountEntity> findByIdUserBirthday(Long idUserBirthday);
}
