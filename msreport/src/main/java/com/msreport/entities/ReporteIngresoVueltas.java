package com.msreport.entities;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "reporte_ingresos_vueltas")
public class ReporteIngresoVueltas {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String tipoTarifa; // "10 vueltas o máx 10 min", etc.
    private Integer mes;
    private Integer anio;
    private Double montoTotal;
    private LocalDate fechaGeneracion;

    // Constructores
    public ReporteIngresoVueltas() {
        this.fechaGeneracion = LocalDate.now();
    }

    public ReporteIngresoVueltas(String tipoTarifa, Integer mes, Integer anio, Double montoTotal) {
        this.tipoTarifa = tipoTarifa;
        this.mes = mes;
        this.anio = anio;
        this.montoTotal = montoTotal;
        this.fechaGeneracion = LocalDate.now();
    }

    // Getters y Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTipoTarifa() {
        return tipoTarifa;
    }

    public void setTipoTarifa(String tipoTarifa) {
        this.tipoTarifa = tipoTarifa;
    }

    public Integer getMes() {
        return mes;
    }

    public void setMes(Integer mes) {
        this.mes = mes;
    }

    public Integer getAnio() {
        return anio;
    }

    public void setAnio(Integer anio) {
        this.anio = anio;
    }

    public Double getMontoTotal() {
        return montoTotal;
    }

    public void setMontoTotal(Double montoTotal) {
        this.montoTotal = montoTotal;
    }

    public LocalDate getFechaGeneracion() {
        return fechaGeneracion;
    }

    public void setFechaGeneracion(LocalDate fechaGeneracion) {
        this.fechaGeneracion = fechaGeneracion;
    }
}