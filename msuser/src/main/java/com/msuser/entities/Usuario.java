package com.msuser.entities;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.Date;

@Entity
@Table(name = "usuarios")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String rut;
    private String nombre;
    private String apellido;
    private String email;
    private String telefono;
    @Column(name = "fecha_nacimiento")
    private LocalDate fechaNacimiento;
    private LocalDate fechaUltimaReserva;
    private Integer frecuenciaReserva;
    //DESCUENTO ASOCIADO
    private Long descuentoId;
    private String nombreDescuento;
    //RESERVATION ASOCIADA
    private Long reservationId;
    private Double tarifaPersonal;
    // Constructores
    public Usuario() {
    }

    public Usuario(String rut,String nombre, String apellido, String email, String telefono, LocalDate fechaNacimiento) {
        this.rut = rut;
        this.nombre = nombre;
        this.apellido = apellido;
        this.email = email;
        this.telefono = telefono;
        this.fechaNacimiento = fechaNacimiento;
        this.frecuenciaReserva = 0;
        this.tarifaPersonal = 0.0;
    }

    public String getRut() {
        return rut;
    }
    public void setRut(String rut) {
        this.rut = rut;
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

    // Getters y Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Long getDescuentoId() {
        return descuentoId;
    }

    public void setReservationId(Long reservationId) {
        this.reservationId = reservationId;
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

    // Método para verificar si es su cumpleaños
    public boolean esCumpleanios() {
        if (fechaNacimiento == null) return false;

        LocalDate hoy = LocalDate.now();
        return fechaNacimiento.getMonth() == hoy.getMonth() &&
                fechaNacimiento.getDayOfMonth() == hoy.getDayOfMonth();
    }

    public LocalDate getFechaUltimaReserva() {
        return fechaUltimaReserva;
    }

    public void setFechaUltimaReserva(LocalDate fechaUltimaReserva) {
        this.fechaUltimaReserva = fechaUltimaReserva;
    }
}