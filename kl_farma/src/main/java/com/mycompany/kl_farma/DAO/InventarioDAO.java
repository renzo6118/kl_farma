package com.mycompany.kl_farma.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import com.mycompany.kl_farma.model.Inventario;

public class InventarioDAO {
    private Connection connection;

    public InventarioDAO(Connection connection) {
        this.connection = connection;
    }

    // Create
    public boolean agregarInventario(Inventario inventario) {
        String query = "INSERT INTO inventario (idProducto, stock, ubicacion) VALUES (?, ?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, inventario.getIdProducto());
            ps.setInt(2, inventario.getStock());
            ps.setString(3, inventario.getUbicacion());
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    // Read
    public List<Inventario> listarInventarios() {
        List<Inventario> inventarios = new ArrayList<>();
        String query = "SELECT * FROM inventario";
        try (PreparedStatement ps = connection.prepareStatement(query);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Inventario inventario = new Inventario();
                inventario.setIdInventario(rs.getInt("idInventario"));
                inventario.setIdProducto(rs.getInt("idProducto"));
                inventario.setStock(rs.getInt("stock"));
                inventario.setUbicacion(rs.getString("ubicacion"));
                inventarios.add(inventario);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return inventarios;
    }

    // Update
    public boolean actualizarInventario(Inventario inventario) {
        String query = "UPDATE inventario SET idProducto = ?, stock = ?, ubicacion = ? WHERE idInventario = ?";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, inventario.getIdProducto());
            ps.setInt(2, inventario.getStock());
            ps.setString(3, inventario.getUbicacion());
            ps.setInt(4, inventario.getIdInventario());
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    // Delete
    public boolean eliminarInventario(int idInventario) {
        String query = "DELETE FROM inventario WHERE idInventario = ?";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, idInventario);
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
