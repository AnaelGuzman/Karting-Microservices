package com.msreport.models;
import java.util.Map;

public class ReportePersonasResponse {
    private String rangoPersonas;
    private Map<String, Double> montosPorMes; // "2025-01" -> monto

    public ReportePersonasResponse() {}

    public ReportePersonasResponse(String rangoPersonas, Map<String, Double> montosPorMes) {
        this.rangoPersonas = rangoPersonas;
        this.montosPorMes = montosPorMes;
    }

    public String getRangoPersonas() { return rangoPersonas; }
    public void setRangoPersonas(String rangoPersonas) { this.rangoPersonas = rangoPersonas; }

    public Map<String, Double> getMontosPorMes() { return montosPorMes; }
    public void setMontosPorMes(Map<String, Double> montosPorMes) { this.montosPorMes = montosPorMes; }
}
