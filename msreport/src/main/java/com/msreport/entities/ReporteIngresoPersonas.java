package com.msreport.entities;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "reporte_ingresos_personas")
public class ReporteIngresoPersonas {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String rangoPersonas; // "1-2 personas", "3-5 personas", etc.
    private Integer mes;
    private Integer anio;
    private Double montoTotal;
    private LocalDate fechaGeneracion;

    // Constructores
    public ReporteIngresoPersonas() {
        this.fechaGeneracion = LocalDate.now();
    }

    public ReporteIngresoPersonas(String rangoPersonas, Integer mes, Integer anio, Double montoTotal) {
        this.rangoPersonas = rangoPersonas;
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

    public String getRangoPersonas() {
        return rangoPersonas;
    }

    public void setRangoPersonas(String rangoPersonas) {
        this.rangoPersonas = rangoPersonas;
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
