package com.msreport.repository;

import com.msreport.entities.ReporteIngresoPersonas;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReporteIngresoPersonasRepository extends JpaRepository<ReporteIngresoPersonas, Long> {

    @Query("SELECT r FROM ReporteIngresoPersonas r WHERE r.mes BETWEEN :mesInicio AND :mesFin AND r.anio = :anio ORDER BY r.rangoPersonas, r.mes")
    List<ReporteIngresoPersonas> findByRangoMesesAndAnio(@Param("mesInicio") Integer mesInicio,
                                                         @Param("mesFin") Integer mesFin,
                                                         @Param("anio") Integer anio);

    @Query("SELECT r FROM ReporteIngresoPersonas r WHERE r.rangoPersonas = :rangoPersonas AND r.mes = :mes AND r.anio = :anio")
    ReporteIngresoPersonas findByRangoPersonasAndMesAndAnio(@Param("rangoPersonas") String rangoPersonas,
                                                            @Param("mes") Integer mes,
                                                            @Param("anio") Integer anio);
}
