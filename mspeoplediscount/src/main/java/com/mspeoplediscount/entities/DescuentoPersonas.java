package com.mspeoplediscount.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "descuentos_personas")
public class DescuentoPersonas {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Integer minPersonas;
    private Integer maxPersonas;
    private Double porcentajeDescuento;
    private Double TotalFinal;

    // Constructores
    public DescuentoPersonas() {
    }

    public DescuentoPersonas(Integer minPersonas, Integer maxPersonas, Double porcentajeDescuento) {
        this.minPersonas = minPersonas;
        this.maxPersonas = maxPersonas;
        this.porcentajeDescuento = porcentajeDescuento;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getMinPersonas() {
        return minPersonas;
    }

    public void setMinPersonas(Integer minPersonas) {
        this.minPersonas = minPersonas;
    }

    public Integer getMaxPersonas() {
        return maxPersonas;
    }

    public void setMaxPersonas(Integer maxPersonas) {
        this.maxPersonas = maxPersonas;
    }

    public Double getPorcentajeDescuento() {
        return porcentajeDescuento;
    }

    public void setPorcentajeDescuento(Double porcentajeDescuento) {
        this.porcentajeDescuento = porcentajeDescuento;
    }

    public Double getTotalFinal() {
        return TotalFinal;
    }

    public void setTotalFinal(Double totalFinal) {
        TotalFinal = totalFinal;
    }
}