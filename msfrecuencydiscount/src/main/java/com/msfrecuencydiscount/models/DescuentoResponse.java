package com.msfrecuencydiscount.models;

public class DescuentoResponse {
    private String categoriaCliente;
    private Double porcentajeDescuento;
    private String mensaje;

    // Constructores
    public DescuentoResponse() {
    }

    public DescuentoResponse(String categoriaCliente, Double porcentajeDescuento, String mensaje) {
        this.categoriaCliente = categoriaCliente;
        this.porcentajeDescuento = porcentajeDescuento;
        this.mensaje = mensaje;
    }

    // Getters y Setters
    public String getCategoriaCliente() {
        return categoriaCliente;
    }

    public void setCategoriaCliente(String categoriaCliente) {
        this.categoriaCliente = categoriaCliente;
    }

    public Double getPorcentajeDescuento() {
        return porcentajeDescuento;
    }

    public void setPorcentajeDescuento(Double porcentajeDescuento) {
        this.porcentajeDescuento = porcentajeDescuento;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }
}
