package com.mycompany.kl_farma.model;

public class Empleado {
    private int idEmpleado;
    private String nombre;
    private String apellido;
    private String rol;
    private String usuario;
    private String contrasena;

    // Constructor vacío
    public Empleado() {}

    // Constructor completo
    public Empleado(int idEmpleado, String nombre, String apellido, String rol, String usuario, String contrasena) {
        this.idEmpleado = idEmpleado;
        this.nombre = nombre;
        this.apellido = apellido;
        this.rol = rol;
        this.usuario = usuario;
        this.contrasena = contrasena;
    }

    // Getters y Setters

    public int getIdEmpleado() {
        return idEmpleado;
    }

    public void setIdEmpleado(int idEmpleado) {
        this.idEmpleado = idEmpleado;
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

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }
    
}
