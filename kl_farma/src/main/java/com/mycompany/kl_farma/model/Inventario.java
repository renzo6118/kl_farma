package com.mycompany.kl_farma.model;

public class Inventario {
    private int idInventario;
    private int idProducto;
    private String sucursal;
    private int stock;
    private String ultimaActualizacion;
    private String ubicacion;

    // Constructor vacío
    public Inventario() {}

    // Constructor completo
    public Inventario(int idInventario, int idProducto, String sucursal, int cantidadDisponible, String ultimaActualizacion) {
        this.idInventario = idInventario;
        this.idProducto = idProducto;
        this.sucursal = sucursal;
        this.stock = cantidadDisponible;
        this.ultimaActualizacion = ultimaActualizacion;
    }

    // Getters y Setters

    public int getIdInventario() {
        return idInventario;
    }

    public int getIdProducto() {
        return idProducto;
    }

    public String getSucursal() {
        return sucursal;
    }

    public int getStock() {
        return stock;
    }

    public String getUltimaActualizacion() {
        return ultimaActualizacion;
    }

    public void setIdInventario(int idInventario) {
        this.idInventario = idInventario;
    }

    public void setIdProducto(int idProducto) {
        this.idProducto = idProducto;
    }

    public void setSucursal(String sucursal) {
        this.sucursal = sucursal;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public void setUltimaActualizacion(String ultimaActualizacion) {
        this.ultimaActualizacion = ultimaActualizacion;
    }

    public String getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }
    
}
