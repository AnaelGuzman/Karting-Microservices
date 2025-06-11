package com.mskart.repository;

import com.mskart.entities.KartEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface KartRepository extends JpaRepository<KartEntity,Long> {
    KartEntity findKartEntityByCode(String code);

    List<KartEntity> findKartEntityByModel(String model);
    @Query(value = "SELECT * FROM kart WHERE kart.status = :status", nativeQuery = true)
    List<KartEntity> findKartEntityByStatus(@Param("status") String status);
}