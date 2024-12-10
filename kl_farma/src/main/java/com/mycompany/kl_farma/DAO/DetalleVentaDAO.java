package com.mycompany.kl_farma.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import com.mycompany.kl_farma.model.DetalleVenta;

public class DetalleVentaDAO {
    private Connection connection;

    public DetalleVentaDAO(Connection connection) {
        this.connection = connection;
    }

    // Create
    public boolean agregarDetalleVenta(DetalleVenta detalleVenta) {
        String query = "INSERT INTO detalle_ventas (idVenta, idProducto, cantidad, precioUnitario) VALUES (?, ?, ?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, detalleVenta.getIdVenta());
            ps.setInt(2, detalleVenta.getIdProducto());
            ps.setInt(3, detalleVenta.getCantidad());
            ps.setDouble(4, detalleVenta.getPrecioUnitario());
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    // Read
    public List<DetalleVenta> listarDetalleVentas() {
        List<DetalleVenta> detalleVentas = new ArrayList<>();
        String query = "SELECT * FROM detalle_ventas";
        try (PreparedStatement ps = connection.prepareStatement(query);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                DetalleVenta detalleVenta = new DetalleVenta();
                detalleVenta.setIdDetalle(rs.getInt("idDetalleVenta"));
                detalleVenta.setIdVenta(rs.getInt("idVenta"));
                detalleVenta.setIdProducto(rs.getInt("idProducto"));
                detalleVenta.setCantidad(rs.getInt("cantidad"));
                detalleVenta.setPrecioUnitario(rs.getDouble("precioUnitario"));
                detalleVentas.add(detalleVenta);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return detalleVentas;
    }

    // Update
    public boolean actualizarDetalleVenta(DetalleVenta detalleVenta) {
        String query = "UPDATE detalle_ventas SET idVenta = ?, idProducto = ?, cantidad = ?, precioUnitario = ? WHERE idDetalleVenta = ?";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, detalleVenta.getIdVenta());
            ps.setInt(2, detalleVenta.getIdProducto());
            ps.setInt(3, detalleVenta.getCantidad());
            ps.setDouble(4, detalleVenta.getPrecioUnitario());
            ps.setInt(5, detalleVenta.getIdDetalle());
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    // Delete
    public boolean eliminarDetalleVenta(int idDetalleVenta) {
        String query = "DELETE FROM detalle_ventas WHERE idDetalleVenta = ?";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, idDetalleVenta);
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
