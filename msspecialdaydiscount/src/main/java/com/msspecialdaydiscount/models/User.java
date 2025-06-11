package com.msspecialdaydiscount.models;

import jakarta.persistence.Column;

import java.time.LocalDate;

public class User {
    private Long id;
    private String rut;
    private String nombre;
    private String apellido;
    private String email;
    private String telefono;
    private LocalDate fechaNacimiento;
    private LocalDate fechaUltimaReserva;
    private Integer frecuenciaReserva;
    //FEE ASOCIADA
    private Long feeId;
    //RESERVATION ASOCIADA
    private Long reservationId;
    private Double tarifaPersonal;

    public User() {}
    public User(Long id, String rut, String nombre, String apellido, String email, String telefono, LocalDate fechaNacimiento, Integer frecuenciaReserva, Double tarifaPersonal) {
        this.id = id;
        this.rut = rut;
        this.nombre = nombre;
        this.apellido = apellido;
        this.email = email;
        this.telefono = telefono;
        this.fechaNacimiento = fechaNacimiento;
        this.frecuenciaReserva = frecuenciaReserva;
        this.tarifaPersonal = tarifaPersonal;
    }

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

    public Integer getFrecuenciaReserva() {
        return frecuenciaReserva;
    }

    public void setFrecuenciaReserva(Integer frecuenciaReserva) {
        this.frecuenciaReserva = frecuenciaReserva;
    }

    public Double getTarifaPersonal() {
        return tarifaPersonal;
    }

    public void setTarifaPersonal(Double tarifaPersonal) {
        this.tarifaPersonal = tarifaPersonal;
    }

    public Long getFeeId() {
        return feeId;
    }

    public void setFeeId(Long feeId) {
        this.feeId = feeId;
    }

    public Long getReservationId() {
        return reservationId;
    }

    public void setReservationId(Long reservationId) {
        this.reservationId = reservationId;
    }
}