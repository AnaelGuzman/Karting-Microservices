package com.msreport.services;

import com.msreport.entities.ReporteIngresoPersonas;
import com.msreport.entities.ReporteIngresoVueltas;
import com.msreport.models.ReportePersonasResponse;
import com.msreport.models.ReporteVueltasResponse;
import com.msreport.models.ReservaDTO;
import com.msreport.models.TarifaDTO;
import com.msreport.repository.ReporteIngresoPersonasRepository;
import com.msreport.repository.ReporteIngresoVueltasRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ReporteService {

    @Autowired
    private ReporteIngresoVueltasRepository reporteVueltasRepository;

    @Autowired
    private ReporteIngresoPersonasRepository reportePersonasRepository;

    @Autowired
    private RestTemplate restTemplate;

    // Generar reporte de ingresos por vueltas/tiempo
    public List<ReporteVueltasResponse> generarReporteIngresosPorVueltas(Integer mesInicio, Integer mesFin, Integer anio) {
        // Primero procesamos las reservas para generar los datos del reporte
        procesarReservasParaReporteVueltas(mesInicio, mesFin, anio);

        // Obtenemos todas las tarifas disponibles
        TarifaDTO[] tarifas = restTemplate.getForObject("http://msfee/api/v1/fees", TarifaDTO[].class);

        // Obtenemos los datos del reporte desde la BD
        List<ReporteIngresoVueltas> reportes = reporteVueltasRepository.findByRangoMesesAndAnio(mesInicio, mesFin, anio);

        // Agrupamos por tipo de tarifa
        Map<String, List<ReporteIngresoVueltas>> reportesPorTarifa = reportes.stream()
                .collect(Collectors.groupingBy(ReporteIngresoVueltas::getTipoTarifa));

        // Construimos la respuesta
        List<ReporteVueltasResponse> respuesta = new ArrayList<>();

        // Aseguramos que todas las tarifas aparezcan en el reporte
        for (TarifaDTO tarifa : tarifas) {
            Map<String, Double> montosPorMes = new LinkedHashMap<>();

            // Inicializamos todos los meses con 0
            for (int mes = mesInicio; mes <= mesFin; mes++) {
                String claveMes = anio + "-" + String.format("%02d", mes);
                montosPorMes.put(claveMes, 0.0);
            }

            // Llenamos con los montos reales si existen
            List<ReporteIngresoVueltas> reportesTarifa = reportesPorTarifa.get(tarifa.getDescripcion());
            if (reportesTarifa != null) {
                for (ReporteIngresoVueltas reporte : reportesTarifa) {
                    String claveMes = reporte.getAnio() + "-" + String.format("%02d", reporte.getMes());
                    montosPorMes.put(claveMes, reporte.getMontoTotal());
                }
            }

            respuesta.add(new ReporteVueltasResponse(tarifa.getDescripcion(), montosPorMes));
        }

        return respuesta;
    }

    // Generar reporte de ingresos por número de personas
    public List<ReportePersonasResponse> generarReporteIngresosPorPersonas(Integer mesInicio, Integer mesFin, Integer anio) {
        // Primero procesamos las reservas para generar los datos del reporte
        procesarReservasParaReportePersonas(mesInicio, mesFin, anio);

        // Definimos los rangos de personas según las especificaciones
        String[] rangosPersonas = {"1-2 Personas", "3-5 Personas", "6-10 Personas", "11-15 Personas"};

        // Obtenemos los datos del reporte desde la BD
        List<ReporteIngresoPersonas> reportes = reportePersonasRepository.findByRangoMesesAndAnio(mesInicio, mesFin, anio);

        // Agrupamos por rango de personas
        Map<String, List<ReporteIngresoPersonas>> reportesPorRango = reportes.stream()
                .collect(Collectors.groupingBy(ReporteIngresoPersonas::getRangoPersonas));

        // Construimos la respuesta
        List<ReportePersonasResponse> respuesta = new ArrayList<>();

        // Aseguramos que todos los rangos aparezcan en el reporte
        for (String rango : rangosPersonas) {
            Map<String, Double> montosPorMes = new LinkedHashMap<>();

            // Inicializamos todos los meses con 0
            for (int mes = mesInicio; mes <= mesFin; mes++) {
                String claveMes = anio + "-" + String.format("%02d", mes);
                montosPorMes.put(claveMes, 0.0);
            }

            // Llenamos con los montos reales si existen
            List<ReporteIngresoPersonas> reportesRango = reportesPorRango.get(rango);
            if (reportesRango != null) {
                for (ReporteIngresoPersonas reporte : reportesRango) {
                    String claveMes = reporte.getAnio() + "-" + String.format("%02d", reporte.getMes());
                    montosPorMes.put(claveMes, reporte.getMontoTotal());
                }
            }

            respuesta.add(new ReportePersonasResponse(rango, montosPorMes));
        }

        return respuesta;
    }

    // Procesar reservas para generar datos del reporte de vueltas
    private void procesarReservasParaReporteVueltas(Integer mesInicio, Integer mesFin, Integer anio) {
        // Obtenemos todas las reservas
        ReservaDTO[] reservas = restTemplate.getForObject("http://msreservation/api/v1/reservas", ReservaDTO[].class);

        // Filtramos reservas por el rango de fechas
        List<ReservaDTO> reservasFiltradas = Arrays.stream(reservas)
                .filter(r -> r.getFechaReserva() != null)
                .filter(r -> r.getFechaReserva().getYear() == anio)
                .filter(r -> r.getFechaReserva().getMonthValue() >= mesInicio && r.getFechaReserva().getMonthValue() <= mesFin)
                .collect(Collectors.toList());

        // Agrupamos por mes y tipo de tarifa
        Map<String, Map<Integer, Double>> ingresosPorTarifaYMes = new HashMap<>();

        for (ReservaDTO reserva : reservasFiltradas) {
            String tipoTarifa = reserva.getDecripcionFee();
            Integer mes = reserva.getFechaReserva().getMonthValue();
            Double monto = reserva.getPrecioTotal() != null ? reserva.getPrecioTotal() : 0.0;

            ingresosPorTarifaYMes
                    .computeIfAbsent(tipoTarifa, k -> new HashMap<>())
                    .merge(mes, monto, Double::sum);
        }

        // Guardamos o actualizamos en la base de datos
        for (Map.Entry<String, Map<Integer, Double>> entryTarifa : ingresosPorTarifaYMes.entrySet()) {
            String tipoTarifa = entryTarifa.getKey();

            for (Map.Entry<Integer, Double> entryMes : entryTarifa.getValue().entrySet()) {
                Integer mes = entryMes.getKey();
                Double monto = entryMes.getValue();

                ReporteIngresoVueltas reporte = reporteVueltasRepository
                        .findByTipoTarifaAndMesAndAnio(tipoTarifa, mes, anio);

                if (reporte == null) {
                    reporte = new ReporteIngresoVueltas(tipoTarifa, mes, anio, monto);
                } else {
                    reporte.setMontoTotal(monto);
                    reporte.setFechaGeneracion(LocalDate.now());
                }

                reporteVueltasRepository.save(reporte);
            }
        }
    }

    // Procesar reservas para generar datos del reporte de personas
    private void procesarReservasParaReportePersonas(Integer mesInicio, Integer mesFin, Integer anio) {
        // Obtenemos todas las reservas
        ReservaDTO[] reservas = restTemplate.getForObject("http://msreservation/api/v1/reservas", ReservaDTO[].class);

        // Filtramos reservas por el rango de fechas
        List<ReservaDTO> reservasFiltradas = Arrays.stream(reservas)
                .filter(r -> r.getFechaReserva() != null)
                .filter(r -> r.getFechaReserva().getYear() == anio)
                .filter(r -> r.getFechaReserva().getMonthValue() >= mesInicio && r.getFechaReserva().getMonthValue() <= mesFin)
                .collect(Collectors.toList());

        // Agrupamos por mes y rango de personas
        Map<String, Map<Integer, Double>> ingresosPorRangoYMes = new HashMap<>();

        for (ReservaDTO reserva : reservasFiltradas) {
            String rangoPersonas = determinarRangoPersonas(reserva.getParticiapantes());
            Integer mes = reserva.getFechaReserva().getMonthValue();
            Double monto = reserva.getPrecioTotal() != null ? reserva.getPrecioTotal() : 0.0;

            ingresosPorRangoYMes
                    .computeIfAbsent(rangoPersonas, k -> new HashMap<>())
                    .merge(mes, monto, Double::sum);
        }

        // Guardamos o actualizamos en la base de datos
        for (Map.Entry<String, Map<Integer, Double>> entryRango : ingresosPorRangoYMes.entrySet()) {
            String rangoPersonas = entryRango.getKey();

            for (Map.Entry<Integer, Double> entryMes : entryRango.getValue().entrySet()) {
                Integer mes = entryMes.getKey();
                Double monto = entryMes.getValue();

                ReporteIngresoPersonas reporte = reportePersonasRepository
                        .findByRangoPersonasAndMesAndAnio(rangoPersonas, mes, anio);

                if (reporte == null) {
                    reporte = new ReporteIngresoPersonas(rangoPersonas, mes, anio, monto);
                } else {
                    reporte.setMontoTotal(monto);
                    reporte.setFechaGeneracion(LocalDate.now());
                }

                reportePersonasRepository.save(reporte);
            }
        }
    }

    // Determinar el rango de personas según las especificaciones
    private String determinarRangoPersonas(Integer participantes) {
        if (participantes == null) return "1-2 Personas";

        if (participantes >= 1 && participantes <= 2) {
            return "1-2 Personas";
        } else if (participantes >= 3 && participantes <= 5) {
            return "3-5 Personas";
        } else if (participantes >= 6 && participantes <= 10) {
            return "6-10 Personas";
        } else if (participantes >= 11 && participantes <= 15) {
            return "11-15 Personas";
        } else {
            return "1-2 Personas"; // Por defecto
        }
    }
}