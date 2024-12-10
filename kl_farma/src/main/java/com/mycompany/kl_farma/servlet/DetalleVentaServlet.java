package com.mycompany.kl_farma.servlet;

import com.mycompany.kl_farma.DAO.DetalleVentaDAO;
import com.mycompany.kl_farma.model.DetalleVenta;
import com.mycompany.kl_farma.util.Conexion;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.Connection;
import java.util.List;

@WebServlet("/DetalleVentaServlet")
public class DetalleVentaServlet extends HttpServlet {
    private DetalleVentaDAO detalleVentaDAO;

    @Override
    public void init() throws ServletException {
        Connection connection = Conexion.getConnection();
        detalleVentaDAO = new DetalleVentaDAO(connection);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        try {
            if (action == null) {
                listarDetalleVentas(request, response);
            } else {
                switch (action) {
                    case "edit":
                        mostrarFormularioEdicion(request, response);
                        break;
                    case "delete":
                        eliminarDetalleVenta(request, response);
                        break;
                    default:
                        listarDetalleVentas(request, response);
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
                response.sendRedirect("DetalleVentaServlet");
            } else {
                switch (action) {
                    case "add":
                        agregarDetalleVenta(request, response);
                        break;
                    case "update":
                        actualizarDetalleVenta(request, response);
                        break;
                    default:
                        response.sendRedirect("DetalleVentaServlet");
                        break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

    private void listarDetalleVentas(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<DetalleVenta> listaDetalleVentas = detalleVentaDAO.listarDetalleVentas();
        request.setAttribute("listaDetalleVentas", listaDetalleVentas);
        request.getRequestDispatcher("detalleVentas.jsp").forward(request, response);
    }

    private void agregarDetalleVenta(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int idVenta = Integer.parseInt(request.getParameter("idVenta"));
        int idProducto = Integer.parseInt(request.getParameter("idProducto"));
        int cantidad = Integer.parseInt(request.getParameter("cantidad"));
        double precioUnitario = Double.parseDouble(request.getParameter("precioUnitario"));

        DetalleVenta detalleVenta = new DetalleVenta();
        detalleVenta.setIdVenta(idVenta);
        detalleVenta.setIdProducto(idProducto);
        detalleVenta.setCantidad(cantidad);
        detalleVenta.setPrecioUnitario(precioUnitario);

        detalleVentaDAO.agregarDetalleVenta(detalleVenta);
        response.sendRedirect("DetalleVentaServlet");
    }

    private void mostrarFormularioEdicion(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int idDetalle = Integer.parseInt(request.getParameter("id"));
        DetalleVenta detalleVentaExistente = detalleVentaDAO.listarDetalleVentas().stream()
                .filter(dv -> dv.getIdDetalle() == idDetalle)
                .findFirst()
                .orElse(null);

        request.setAttribute("detalleVenta", detalleVentaExistente);
        request.getRequestDispatcher("editarDetalleVenta.jsp").forward(request, response);
    }

    private void actualizarDetalleVenta(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int idDetalle = Integer.parseInt(request.getParameter("id"));
        int idVenta = Integer.parseInt(request.getParameter("idVenta"));
        int idProducto = Integer.parseInt(request.getParameter("idProducto"));
        int cantidad = Integer.parseInt(request.getParameter("cantidad"));
        double precioUnitario = Double.parseDouble(request.getParameter("precioUnitario"));

        DetalleVenta detalleVenta = new DetalleVenta();
        detalleVenta.setIdDetalle(idDetalle);
        detalleVenta.setIdVenta(idVenta);
        detalleVenta.setIdProducto(idProducto);
        detalleVenta.setCantidad(cantidad);
        detalleVenta.setPrecioUnitario(precioUnitario);

        detalleVentaDAO.actualizarDetalleVenta(detalleVenta);
        response.sendRedirect("DetalleVentaServlet");
    }

    private void eliminarDetalleVenta(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int idDetalle = Integer.parseInt(request.getParameter("id"));
        detalleVentaDAO.eliminarDetalleVenta(idDetalle);
        response.sendRedirect("DetalleVentaServlet");
    }

    @Override
    public void destroy() {
    }
}
