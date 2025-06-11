package com.msreport.models;

import java.util.Map;

public class ReporteVueltasResponse {
    private String tipoTarifa;
    private Map<String, Double> montosPorMes; // "2025-01" -> monto

    public ReporteVueltasResponse() {}

    public ReporteVueltasResponse(String tipoTarifa, Map<String, Double> montosPorMes) {
        this.tipoTarifa = tipoTarifa;
        this.montosPorMes = montosPorMes;
    }

    public String getTipoTarifa() { return tipoTarifa; }
    public void setTipoTarifa(String tipoTarifa) { this.tipoTarifa = tipoTarifa; }

    public Map<String, Double> getMontosPorMes() { return montosPorMes; }
    public void setMontosPorMes(Map<String, Double> montosPorMes) { this.montosPorMes = montosPorMes; }
}