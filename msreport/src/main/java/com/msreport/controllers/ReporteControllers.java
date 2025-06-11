package com.msreport.controllers;
import com.msreport.models.ReportePersonasResponse;
import com.msreport.models.ReporteVueltasResponse;
import com.msreport.services.ReporteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/reportes")
public class ReporteControllers {

    @Autowired
    private ReporteService reporteService;

    /**
     * Endpoint para generar reporte de ingresos por número de vueltas o tiempo máximo
     * GET /api/v1/reportes/ingresos-vueltas?mesInicio=1&mesFin=12&anio=2025
     */
    @GetMapping("/ingresos-vueltas")
    public ResponseEntity<List<ReporteVueltasResponse>> generarReporteIngresosPorVueltas(
            @RequestParam Integer mesInicio,
            @RequestParam Integer mesFin,
            @RequestParam Integer anio) {

        try {
            // Validaciones básicas
            if (mesInicio < 1 || mesInicio > 12 || mesFin < 1 || mesFin > 12) {
                return ResponseEntity.badRequest().build();
            }

            if (mesInicio > mesFin) {
                return ResponseEntity.badRequest().build();
            }

            if (anio < 2020 || anio > 2030) {
                return ResponseEntity.badRequest().build();
            }

            List<ReporteVueltasResponse> reporte = reporteService.generarReporteIngresosPorVueltas(mesInicio, mesFin, anio);
            return ResponseEntity.ok(reporte);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Endpoint para generar reporte de ingresos por número de personas
     * GET /api/v1/reportes/ingresos-personas?mesInicio=1&mesFin=12&anio=2025
     */
    @GetMapping("/ingresos-personas")
    public ResponseEntity<List<ReportePersonasResponse>> generarReporteIngresosPorPersonas(
            @RequestParam Integer mesInicio,
            @RequestParam Integer mesFin,
            @RequestParam Integer anio) {

        try {
            // Validaciones básicas
            if (mesInicio < 1 || mesInicio > 12 || mesFin < 1 || mesFin > 12) {
                return ResponseEntity.badRequest().build();
            }

            if (mesInicio > mesFin) {
                return ResponseEntity.badRequest().build();
            }

            if (anio < 2020 || anio > 2030) {
                return ResponseEntity.badRequest().build();
            }

            List<ReportePersonasResponse> reporte = reporteService.generarReporteIngresosPorPersonas(mesInicio, mesFin, anio);
            return ResponseEntity.ok(reporte);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Endpoint adicional para obtener reporte de vueltas para un mes específico
     * GET /api/v1/reportes/ingresos-vueltas/mes?mes=1&anio=2025
     */
    @GetMapping("/ingresos-vueltas/mes")
    public ResponseEntity<List<ReporteVueltasResponse>> generarReporteIngresosPorVueltasMes(
            @RequestParam Integer mes,
            @RequestParam Integer anio) {

        try {
            if (mes < 1 || mes > 12) {
                return ResponseEntity.badRequest().build();
            }

            if (anio < 2020 || anio > 2030) {
                return ResponseEntity.badRequest().build();
            }

            List<ReporteVueltasResponse> reporte = reporteService.generarReporteIngresosPorVueltas(mes, mes, anio);
            return ResponseEntity.ok(reporte);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Endpoint adicional para obtener reporte de personas para un mes específico
     * GET /api/v1/reportes/ingresos-personas/mes?mes=1&anio=2025
     */
    @GetMapping("/ingresos-personas/mes")
    public ResponseEntity<List<ReportePersonasResponse>> generarReporteIngresosPorPersonasMes(
            @RequestParam Integer mes,
            @RequestParam Integer anio) {

        try {
            if (mes < 1 || mes > 12) {
                return ResponseEntity.badRequest().build();
            }

            if (anio < 2020 || anio > 2030) {
                return ResponseEntity.badRequest().build();
            }

            List<ReportePersonasResponse> reporte = reporteService.generarReporteIngresosPorPersonas(mes, mes, anio);
            return ResponseEntity.ok(reporte);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Endpoint para verificar el estado del servicio de reportes
     * GET /api/v1/reportes/health
     */
    @GetMapping("/health")
    public ResponseEntity<String> healthCheck() {
        return ResponseEntity.ok("Microservicio de Reportes funcionando correctamente");
    }
}