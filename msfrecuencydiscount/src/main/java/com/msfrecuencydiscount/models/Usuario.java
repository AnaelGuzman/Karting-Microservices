package com.msfrecuencydiscount.models;

import jakarta.persistence.Column;

import java.time.LocalDate;

public class Usuario {
    private Long id;
    private String rut;
    private String nombre;
    private String apellido;
    private String email;
    private String telefono;
    private LocalDate fechaNacimiento;
    private LocalDate fechaUltimaReserva;
    private Integer frecuenciaReserva;
    //DESCUENTO ASOCIADO
    private Long descuentoId;
    private String nombreDescuento;
    //RESERVATION ASOCIADA
    private Long reservationId;
    private Double tarifaPersonal;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRut() {
        return rut;
    }

    public void setRut(String rut) {
        this.rut = rut;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public LocalDate getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(LocalDate fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public LocalDate getFechaUltimaReserva() {
        return fechaUltimaReserva;
    }

    public void setFechaUltimaReserva(LocalDate fechaUltimaReserva) {
        this.fechaUltimaReserva = fechaUltimaReserva;
    }

    public Integer getFrecuenciaReserva() {
        return frecuenciaReserva;
    }

    public void setFrecuenciaReserva(Integer frecuenciaReserva) {
        this.frecuenciaReserva = frecuenciaReserva;
    }

    public Long getDescuentoId() {
        return descuentoId;
    }

    public void setDescuentoId(Long descuentoId) {
        this.descuentoId = descuentoId;
    }

    public String getNombreDescuento() {
        return nombreDescuento;
    }

    public void setNombreDescuento(String nombreDescuento) {
        this.nombreDescuento = nombreDescuento;
    }

    public Long getReservationId() {
        return reservationId;
    }

    public void setReservationId(Long reservationId) {
        this.reservationId = reservationId;
    }

    public Double getTarifaPersonal() {
        return tarifaPersonal;
    }

    public void setTarifaPersonal(Double tarifaPersonal) {
        this.tarifaPersonal = tarifaPersonal;
    }
}
