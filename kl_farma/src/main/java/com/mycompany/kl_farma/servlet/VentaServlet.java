package com.mycompany.kl_farma.servlet;

import com.mycompany.kl_farma.DAO.VentaDAO;
import com.mycompany.kl_farma.model.Venta;
import com.mycompany.kl_farma.util.Conexion;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.Connection;
import java.util.List;

@WebServlet("/VentaServlet")
public class VentaServlet extends HttpServlet {
    private VentaDAO ventaDAO;

    @Override
    public void init() throws ServletException {
        Connection connection = Conexion.getConnection();
        ventaDAO = new VentaDAO(connection);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        try {
            if (action == null) {
                listarVentas(request, response);
            } else {
                switch (action) {
                    case "edit":
                        mostrarFormularioEdicion(request, response);
                        break;
                    case "delete":
                        eliminarVenta(request, response);
                        break;
                    default:
                        listarVentas(request, response);
                        break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        try {
            if (action == null) {
                response.sendRedirect("VentaServlet");
            } else {
                switch (action) {
                    case "add":
                        agregarVenta(request, response);
                        break;
                    case "update":
                        actualizarVenta(request, response);
                        break;
                    default:
                        response.sendRedirect("VentaServlet");
                        break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

    private void listarVentas(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Venta> listaVentas = ventaDAO.listarVentas();
        request.setAttribute("listaVentas", listaVentas);
        request.getRequestDispatcher("ventas.jsp").forward(request, response);
    }

    private void agregarVenta(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String fecha = request.getParameter("fecha");
        int idCliente = Integer.parseInt(request.getParameter("idCliente"));
        int idEmpleado = Integer.parseInt(request.getParameter("idEmpleado"));
        double total = Double.parseDouble(request.getParameter("total"));
        String tipoComprobante = request.getParameter("tipoComprobante");

        Venta venta = new Venta();
        venta.setFecha(fecha);
        venta.setIdCliente(idCliente);
        venta.setIdEmpleado(idEmpleado);
        venta.setTotal(total);
        venta.setTipoComprobante(tipoComprobante);

        ventaDAO.agregarVenta(venta);
        response.sendRedirect("VentaServlet");
    }

    private void mostrarFormularioEdicion(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int idVenta = Integer.parseInt(request.getParameter("id"));
        Venta ventaExistente = ventaDAO.listarVentas().stream()
                .filter(v -> v.getIdVenta() == idVenta)
                .findFirst()
                .orElse(null);

        request.setAttribute("venta", ventaExistente);
        request.getRequestDispatcher("editarVenta.jsp").forward(request, response);
    }

    private void actualizarVenta(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int idVenta = Integer.parseInt(request.getParameter("id"));
        String fecha = request.getParameter("fecha");
        int idCliente = Integer.parseInt(request.getParameter("idCliente"));
        int idEmpleado = Integer.parseInt(request.getParameter("idEmpleado"));
        double total = Double.parseDouble(request.getParameter("total"));
        String tipoComprobante = request.getParameter("tipoComprobante");

        Venta venta = new Venta();
        venta.setIdVenta(idVenta);
        venta.setFecha(fecha);
        venta.setIdCliente(idCliente);
        venta.setIdEmpleado(idEmpleado);
        venta.setTotal(total);
        venta.setTipoComprobante(tipoComprobante);

        ventaDAO.actualizarVenta(venta);
        response.sendRedirect("VentaServlet");
    }

    private void eliminarVenta(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int idVenta = Integer.parseInt(request.getParameter("id"));
        ventaDAO.eliminarVenta(idVenta);
        response.sendRedirect("VentaServlet");
    }

    @Override
    public void destroy() {
    }
}
