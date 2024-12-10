package com.mycompany.kl_farma.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import com.mycompany.kl_farma.model.Boleta;

public class BoletaDAO {
    private Connection connection;

    public BoletaDAO(Connection connection) {
        this.connection = connection;
    }

    // Create
    public boolean agregarBoleta(Boleta boleta) {
        String query = "INSERT INTO boletas (idVenta, fechaEmision, total, impuestos) VALUES (?, ?, ?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, boleta.getIdVenta());
            ps.setString(2, boleta.getFechaEmision());
            ps.setDouble(3, boleta.getTotal());
            ps.setDouble(4, boleta.getImpuestos());
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    // Read
    public List<Boleta> listarBoletas() {
        List<Boleta> boletas = new ArrayList<>();
        String query = "SELECT * FROM boletas";
        try (PreparedStatement ps = connection.prepareStatement(query);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Boleta boleta = new Boleta();
                boleta.setIdBoleta(rs.getInt("idBoleta"));
                boleta.setIdVenta(rs.getInt("idVenta"));
                boleta.setFechaEmision(rs.getString("fechaEmision"));
                boleta.setTotal(rs.getDouble("total"));
                boleta.setImpuestos(rs.getDouble("impuestos"));
                boletas.add(boleta);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return boletas;
    }

    // Update
    public boolean actualizarBoleta(Boleta boleta) {
        String query = "UPDATE boletas SET idVenta = ?, fechaEmision = ?, total = ?, impuestos = ? WHERE idBoleta = ?";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, boleta.getIdVenta());
            ps.setString(2, boleta.getFechaEmision());
            ps.setDouble(3, boleta.getTotal());
            ps.setDouble(4, boleta.getImpuestos());
            ps.setInt(5, boleta.getIdBoleta());
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    // Delete
    public boolean eliminarBoleta(int idBoleta) {
        String query = "DELETE FROM boletas WHERE idBoleta = ?";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, idBoleta);
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
