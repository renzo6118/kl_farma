package com.mycompany.kl_farma.model;

public class Producto {
    private int idProducto;
    private String nombre;
    private String descripcion;
    private double precio;
    private String fechaVencimiento;
    private int stock;
    private int idProveedor;

    // Constructor vacío
    public Producto() {}

    // Constructor completo
    public Producto(int idProducto, String nombre, double precio, String fechaVencimiento, int stock, int idProveedor) {
        this.idProducto = idProducto;
        this.nombre = nombre;
        this.precio = precio;
        this.fechaVencimiento = fechaVencimiento;
        this.stock = stock;
        this.idProveedor = idProveedor;
    }

    // Getters y Setters

    public int getIdProducto() {
        return idProducto;
    }

    public String getNombre() {
        return nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public double getPrecio() {
        return precio;
    }

    public String getFechaVencimiento() {
        return fechaVencimiento;
    }

    public int getStock() {
        return stock;
    }

    public int getIdProveedor() {
        return idProveedor;
    }

    public void setIdProducto(int idProducto) {
        this.idProducto = idProducto;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public void setFechaVencimiento(String fechaVencimiento) {
        this.fechaVencimiento = fechaVencimiento;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public void setIdProveedor(int idProveedor) {
        this.idProveedor = idProveedor;
    }
    
    }
