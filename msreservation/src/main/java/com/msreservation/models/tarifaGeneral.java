package com.msreservation.models;

public class tarifaGeneral {
    private String descripcion;
    private String nombre;
    private String tipo;
    private Double precio;

    public tarifaGeneral(){}
    public tarifaGeneral(String descripcion, String nombre, String tipo, Double precio) {
        this.descripcion = descripcion;
        this.nombre = nombre;
        this.tipo = tipo;
        this.precio = precio;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public Double getPrecio() {
        return precio;
    }

    public void setPrecio(Double precio) {
        this.precio = precio;
    }
}
