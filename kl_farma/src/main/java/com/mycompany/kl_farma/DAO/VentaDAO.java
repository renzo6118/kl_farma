package com.mycompany.kl_farma.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import com.mycompany.kl_farma.model.Venta;

public class VentaDAO {
    private Connection connection;

    public VentaDAO(Connection connection) {
        this.connection = connection;
    }

    // Create
    public boolean agregarVenta(Venta venta) {
        String query = "INSERT INTO ventas (fecha, id_cliente, id_empleado, total, tipo_comprobante) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, venta.getFecha());
            ps.setInt(2, venta.getIdCliente());
            ps.setInt(3, venta.getIdEmpleado());
            ps.setDouble(4, venta.getTotal());
            ps.setString(5, venta.getTipoComprobante());
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    // Read
    public List<Venta> listarVentas() {
        List<Venta> ventas = new ArrayList<>();
        String query = "SELECT * FROM ventas";
        try (PreparedStatement ps = connection.prepareStatement(query);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Venta venta = new Venta();
                venta.setIdVenta(rs.getInt("id_venta"));
                venta.setFecha(rs.getString("fecha"));
                venta.setIdCliente(rs.getInt("id_cliente"));
                venta.setIdEmpleado(rs.getInt("id_empleado"));
                venta.setTotal(rs.getDouble("total"));
                venta.setTipoComprobante(rs.getString("tipo_comprobante"));
                ventas.add(venta);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ventas;
    }

    // Update
    public boolean actualizarVenta(Venta venta) {
        String query = "UPDATE ventas SET fecha = ?, id_cliente = ?, id_empleado = ?, total = ?, tipo_comprobante = ? WHERE id_venta = ?";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, venta.getFecha());
            ps.setInt(2, venta.getIdCliente());
            ps.setInt(3, venta.getIdEmpleado());
            ps.setDouble(4, venta.getTotal());
            ps.setString(5, venta.getTipoComprobante());
            ps.setInt(6, venta.getIdVenta());
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    // Delete
    public boolean eliminarVenta(int idVenta) {
        String query = "DELETE FROM ventas WHERE id_venta = ?";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, idVenta);
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
