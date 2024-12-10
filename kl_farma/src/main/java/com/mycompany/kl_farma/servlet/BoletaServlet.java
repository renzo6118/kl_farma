package com.mycompany.kl_farma.servlet;

import com.mycompany.kl_farma.DAO.BoletaDAO;
import com.mycompany.kl_farma.model.Boleta;
import com.mycompany.kl_farma.util.Conexion;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.Connection;
import java.util.List;

@WebServlet("/BoletaServlet")
public class BoletaServlet extends HttpServlet {
    private BoletaDAO boletaDAO;

    @Override
    public void init() throws ServletException {
        Connection connection = Conexion.getConnection();
        boletaDAO = new BoletaDAO(connection);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        try {
            if (action == null) {
                listarBoletas(request, response);
            } else {
                switch (action) {
                    case "edit":
                        mostrarFormularioEdicion(request, response);
                        break;
                    case "delete":
                        eliminarBoleta(request, response);
                        break;
                    default:
                        listarBoletas(request, response);
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
                response.sendRedirect("BoletaServlet");
            } else {
                switch (action) {
                    case "add":
                        agregarBoleta(request, response);
                        break;
                    case "update":
                        actualizarBoleta(request, response);
                        break;
                    default:
                        response.sendRedirect("BoletaServlet");
                        break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

    private void listarBoletas(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Boleta> listaBoletas = boletaDAO.listarBoletas();
        request.setAttribute("listaBoletas", listaBoletas);
        request.getRequestDispatcher("boletas.jsp").forward(request, response);
    }

    private void agregarBoleta(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int idVenta = Integer.parseInt(request.getParameter("idVenta"));
        String fechaEmision = request.getParameter("fechaEmision");
        double total = Double.parseDouble(request.getParameter("total"));
        double impuestos = Double.parseDouble(request.getParameter("impuestos"));

        Boleta boleta = new Boleta();
        boleta.setIdVenta(idVenta);
        boleta.setFechaEmision(fechaEmision);
        boleta.setTotal(total);
        boleta.setImpuestos(impuestos);

        boletaDAO.agregarBoleta(boleta);
        response.sendRedirect("BoletaServlet");
    }

    private void mostrarFormularioEdicion(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int idBoleta = Integer.parseInt(request.getParameter("id"));
        Boleta boletaExistente = boletaDAO.listarBoletas().stream()
                .filter(b -> b.getIdBoleta() == idBoleta)
                .findFirst()
                .orElse(null);

        request.setAttribute("boleta", boletaExistente);
        request.getRequestDispatcher("editarBoleta.jsp").forward(request, response);
    }

    private void actualizarBoleta(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int idBoleta = Integer.parseInt(request.getParameter("id"));
        int idVenta = Integer.parseInt(request.getParameter("idVenta"));
        String fechaEmision = request.getParameter("fechaEmision");
        double total = Double.parseDouble(request.getParameter("total"));
        double impuestos = Double.parseDouble(request.getParameter("impuestos"));

        Boleta boleta = new Boleta();
        boleta.setIdBoleta(idBoleta);
        boleta.setIdVenta(idVenta);
        boleta.setFechaEmision(fechaEmision);
        boleta.setTotal(total);
        boleta.setImpuestos(impuestos);

        boletaDAO.actualizarBoleta(boleta);
        response.sendRedirect("BoletaServlet");
    }

    private void eliminarBoleta(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int idBoleta = Integer.parseInt(request.getParameter("id"));
        boletaDAO.eliminarBoleta(idBoleta);
        response.sendRedirect("BoletaServlet");
    }

    @Override
    public void destroy() {
    }
}
