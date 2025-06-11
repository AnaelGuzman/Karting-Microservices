package com.msreport.repository;

import com.msreport.entities.ReporteIngresoVueltas;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReporteIngresoVueltasRepository extends JpaRepository<ReporteIngresoVueltas, Long> {

    @Query("SELECT r FROM ReporteIngresoVueltas r WHERE r.mes BETWEEN :mesInicio AND :mesFin AND r.anio = :anio ORDER BY r.tipoTarifa, r.mes")
    List<ReporteIngresoVueltas> findByRangoMesesAndAnio(@Param("mesInicio") Integer mesInicio,
                                                        @Param("mesFin") Integer mesFin,
                                                        @Param("anio") Integer anio);

    @Query("SELECT r FROM ReporteIngresoVueltas r WHERE r.tipoTarifa = :tipoTarifa AND r.mes = :mes AND r.anio = :anio")
    ReporteIngresoVueltas findByTipoTarifaAndMesAndAnio(@Param("tipoTarifa") String tipoTarifa,
                                                        @Param("mes") Integer mes,
                                                        @Param("anio") Integer anio);
}