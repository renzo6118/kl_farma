package com.mycompany.kl_farma.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import com.mycompany.kl_farma.model.Proveedor;

public class ProveedorDAO {
    private Connection connection;

    public ProveedorDAO(Connection connection) {
        this.connection = connection;
    }

    // Create
    public boolean agregarProveedor(Proveedor proveedor) {
        String query = "INSERT INTO proveedores (nombre, direccion, telefono, email) VALUES (?, ?, ?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, proveedor.getNombre());
            ps.setString(2, proveedor.getDireccion());
            ps.setString(3, proveedor.getTelefono());
            ps.setString(4, proveedor.getCorreo());
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    // Read
    public List<Proveedor> listarProveedores() {
        List<Proveedor> proveedores = new ArrayList<>();
        String query = "SELECT * FROM proveedores";
        try (PreparedStatement ps = connection.prepareStatement(query);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Proveedor proveedor = new Proveedor();
                proveedor.setIdProveedor(rs.getInt("id"));
                proveedor.setNombre(rs.getString("nombre"));
                proveedor.setDireccion(rs.getString("direccion"));
                proveedor.setTelefono(rs.getString("telefono"));
                proveedor.setCorreo(rs.getString("email"));
                proveedores.add(proveedor);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return proveedores;
    }

    // Update
    public boolean actualizarProveedor(Proveedor proveedor) {
        String query = "UPDATE proveedores SET nombre = ?, direccion = ?, telefono = ?, email = ? WHERE id = ?";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, proveedor.getNombre());
            ps.setString(2, proveedor.getDireccion());
            ps.setString(3, proveedor.getTelefono());
            ps.setString(4, proveedor.getCorreo());
            ps.setInt(5, proveedor.getIdProveedor());
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    // Delete
    public boolean eliminarProveedor(int id) {
        String query = "DELETE FROM proveedores WHERE id = ?";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
