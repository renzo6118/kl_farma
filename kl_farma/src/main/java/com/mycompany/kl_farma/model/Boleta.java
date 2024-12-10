package com.mycompany.kl_farma.model;

public class Boleta {
    private int idBoleta;
    private int idVenta;
    private String fechaEmision;
    private double total;
    private double impuestos;

    // Constructor vacío
    public Boleta() {}

    // Constructor completo
    public Boleta(int idBoleta, int idVenta, String fechaEmision, double total, double impuestos) {
        this.idBoleta = idBoleta;
        this.idVenta = idVenta;
        this.fechaEmision = fechaEmision;
        this.total = total;
        this.impuestos = impuestos;
    }

    // Getters y Setters

    public int getIdBoleta() {
        return idBoleta;
    }

    public void setIdBoleta(int idBoleta) {
        this.idBoleta = idBoleta;
    }

    public int getIdVenta() {
        return idVenta;
    }

    public void setIdVenta(int idVenta) {
        this.idVenta = idVenta;
    }

    public String getFechaEmision() {
        return fechaEmision;
    }

    public void setFechaEmision(String fechaEmision) {
        this.fechaEmision = fechaEmision;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public double getImpuestos() {
        return impuestos;
    }

    public void setImpuestos(double impuestos) {
        this.impuestos = impuestos;
    }

}
