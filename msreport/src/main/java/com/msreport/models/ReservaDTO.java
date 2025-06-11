package com.msreport.models;


import java.time.LocalDate;
import java.time.LocalTime;

public class ReservaDTO {
    private Long id;
    private LocalDate fechaReserva;
    private LocalTime horaInicio;
    private LocalTime horaFin;
    private Long idFee;
    private String decripcionFee;
    private String intervaloParticipantes;
    private Integer particiapantes;
    private Double impuesto;
    private Double precioTotal;

    // Constructores
    public ReservaDTO() {}

    // Getters y Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public LocalDate getFechaReserva() { return fechaReserva; }
    public void setFechaReserva(LocalDate fechaReserva) { this.fechaReserva = fechaReserva; }

    public LocalTime getHoraInicio() { return horaInicio; }
    public void setHoraInicio(LocalTime horaInicio) { this.horaInicio = horaInicio; }

    public LocalTime getHoraFin() { return horaFin; }
    public void setHoraFin(LocalTime horaFin) { this.horaFin = horaFin; }

    public Long getIdFee() { return idFee; }
    public void setIdFee(Long idFee) { this.idFee = idFee; }

    public String getDecripcionFee() { return decripcionFee; }
    public void setDecripcionFee(String decripcionFee) { this.decripcionFee = decripcionFee; }

    public String getIntervaloParticipantes() { return intervaloParticipantes; }
    public void setIntervaloParticipantes(String intervaloParticipantes) { this.intervaloParticipantes = intervaloParticipantes; }

    public Integer getParticiapantes() { return particiapantes; }
    public void setParticiapantes(Integer particiapantes) { this.particiapantes = particiapantes; }

    public Double getImpuesto() { return impuesto; }
    public void setImpuesto(Double impuesto) { this.impuesto = impuesto; }

    public Double getPrecioTotal() { return precioTotal; }
    public void setPrecioTotal(Double precioTotal) { this.precioTotal = precioTotal; }
}
