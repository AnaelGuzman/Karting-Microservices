package com.msrack.model;

import java.time.LocalDate;
import java.time.LocalTime;

public class Reserva {
    private Long id;
    private LocalDate fechaReserva;
    private LocalTime horaInicio;
    private LocalTime horaFin;

    //ASOCIACION CON UNA FEE
    private Long idFee;// o especial dias
    private String decripcionFee;// Ej: "10 vueltas o máx 10 min o tarifa especial para los reportes"

    //ASOCIACION CON DESCUENTO GRUPAÑ
    private String intervaloParticipantes;// intervalo de participantes para los reportes
    private Integer particiapantes;//sacar de los usuarios asociados a la reserva
    //FINAL
    private Double Impuesto;
    private Double precioTotal;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getFechaReserva() {
        return fechaReserva;
    }

    public void setFechaReserva(LocalDate fechaReserva) {
        this.fechaReserva = fechaReserva;
    }

    public LocalTime getHoraInicio() {
        return horaInicio;
    }

    public void setHoraInicio(LocalTime horaInicio) {
        this.horaInicio = horaInicio;
    }

    public String getDecripcionFee() {
        return decripcionFee;
    }

    public void setDecripcionFee(String decripcionFee) {
        this.decripcionFee = decripcionFee;
    }

    public LocalTime getHoraFin() {
        return horaFin;
    }

    public void setHoraFin(LocalTime horaFin) {
        this.horaFin = horaFin;
    }

    public Long getIdFee() {
        return idFee;
    }

    public void setIdFee(Long idFee) {
        this.idFee = idFee;
    }

    public String getIntervaloParticipantes() {
        return intervaloParticipantes;
    }

    public void setIntervaloParticipantes(String intervaloParticipantes) {
        this.intervaloParticipantes = intervaloParticipantes;
    }

    public Integer getParticiapantes() {
        return particiapantes;
    }

    public void setParticiapantes(Integer particiapantes) {
        this.particiapantes = particiapantes;
    }

    public Double getImpuesto() {
        return Impuesto;
    }

    public void setImpuesto(Double impuesto) {
        Impuesto = impuesto;
    }

    public Double getPrecioTotal() {
        return precioTotal;
    }

    public void setPrecioTotal(Double precioTotal) {
        this.precioTotal = precioTotal;
    }
}
