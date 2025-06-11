package com.msfee.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "tarifas")
public class Tarifa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String descripcion; // Ej: "10 vueltas o máx 10 min"
    private Integer vueltas;
    private Integer minutosMaximos;
    private Double precio;
    private Integer duracionTotal; // en minutos

    // Constructores
    public Tarifa() {
    }

    public Tarifa(String descripcion, Integer vueltas, Integer minutosMaximos, Double precio, Integer duracionTotal) {
        this.descripcion = descripcion;
        this.vueltas = vueltas;
        this.minutosMaximos = minutosMaximos;
        this.precio = precio;
        this.duracionTotal = duracionTotal;
    }

    // Getters y Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Integer getVueltas() {
        return vueltas;
    }

    public void setVueltas(Integer vueltas) {
        this.vueltas = vueltas;
    }

    public Integer getMinutosMaximos() {
        return minutosMaximos;
    }

    public void setMinutosMaximos(Integer minutosMaximos) {
        this.minutosMaximos = minutosMaximos;
    }

    public Double getPrecio() {
        return precio;
    }

    public void setPrecio(Double precio) {
        this.precio = precio;
    }

    public Integer getDuracionTotal() {
        return duracionTotal;
    }

    public void setDuracionTotal(Integer duracionTotal) {
        this.duracionTotal = duracionTotal;
    }
}