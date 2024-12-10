package com.mycompany.kl_farma.DAO;

import com.mycompany.kl_farma.model.Empleado;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class EmpleadoDAO {
    private Connection connection;

    public EmpleadoDAO(Connection connection) {
        this.connection = connection;
    }

    // Create
    public boolean agregarEmpleado(Empleado empleado) {
        String query = "INSERT INTO empleados (nombre, apellido, rol, usuario, contrasena) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, empleado.getNombre());
            ps.setString(2, empleado.getApellido());
            ps.setString(3, empleado.getRol());
            ps.setString(4, empleado.getUsuario());
            ps.setString(5, empleado.getContrasena());
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    // Read
    public List<Empleado> listarEmpleados() {
        List<Empleado> empleados = new ArrayList<>();
        String query = "SELECT * FROM empleados";
        try (PreparedStatement ps = connection.prepareStatement(query);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Empleado empleado = new Empleado();
                empleado.setIdEmpleado(rs.getInt("idEmpleado"));
                empleado.setNombre(rs.getString("nombre"));
                empleado.setApellido(rs.getString("apellido"));
                empleado.setRol(rs.getString("rol"));
                empleado.setUsuario(rs.getString("usuario"));
                empleado.setContrasena(rs.getString("contrasena"));
                empleados.add(empleado);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return empleados;
    }

    // Update
    public boolean actualizarEmpleado(Empleado empleado) {
        String query = "UPDATE empleados SET nombre = ?, apellido = ?, rol = ?, usuario = ?, contrasena = ? WHERE idEmpleado = ?";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, empleado.getNombre());
            ps.setString(2, empleado.getApellido());
            ps.setString(3, empleado.getRol());
            ps.setString(4, empleado.getUsuario());
            ps.setString(5, empleado.getContrasena());
            ps.setInt(6, empleado.getIdEmpleado());
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    // Delete
    public boolean eliminarEmpleado(int idEmpleado) {
        String query = "DELETE FROM empleados WHERE idEmpleado = ?";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, idEmpleado);
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
