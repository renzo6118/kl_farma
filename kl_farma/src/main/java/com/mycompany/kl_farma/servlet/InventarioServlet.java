package com.mycompany.kl_farma.servlet;

import com.mycompany.kl_farma.DAO.InventarioDAO;
import com.mycompany.kl_farma.model.Inventario;
import com.mycompany.kl_farma.util.Conexion;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.Connection;
import java.util.List;

@WebServlet("/InventarioServlet")
public class InventarioServlet extends HttpServlet {
    private InventarioDAO inventarioDAO;

    @Override
    public void init() throws ServletException {
        Connection connection = Conexion.getConnection();
        inventarioDAO = new InventarioDAO(connection);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        try {
            if (action == null) {
                listarInventarios(request, response);
            } else {
                switch (action) {
                    case "edit":
                        mostrarFormularioEdicion(request, response);
                        break;
                    case "delete":
                        eliminarInventario(request, response);
                        break;
                    default:
                        listarInventarios(request, response);
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
                response.sendRedirect("InventarioServlet");
            } else {
                switch (action) {
                    case "add":
                        agregarInventario(request, response);
                        break;
                    case "update":
                        actualizarInventario(request, response);
                        break;
                    default:
                        response.sendRedirect("InventarioServlet");
                        break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

    private void listarInventarios(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Inventario> listaInventarios = inventarioDAO.listarInventarios();
        request.setAttribute("listaInventarios", listaInventarios);
        request.getRequestDispatcher("inventarios.jsp").forward(request, response);
    }

    private void agregarInventario(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int idProducto = Integer.parseInt(request.getParameter("idProducto"));
        String sucursal = request.getParameter("sucursal");
        int stock = Integer.parseInt(request.getParameter("stock"));
        String ubicacion = request.getParameter("ubicacion");

        Inventario inventario = new Inventario();
        inventario.setIdProducto(idProducto);
        inventario.setSucursal(sucursal);
        inventario.setStock(stock);
        inventario.setUbicacion(ubicacion);

        inventarioDAO.agregarInventario(inventario);
        response.sendRedirect("InventarioServlet");
    }

    private void mostrarFormularioEdicion(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int idInventario = Integer.parseInt(request.getParameter("id"));
        Inventario inventarioExistente = inventarioDAO.listarInventarios().stream()
                .filter(i -> i.getIdInventario() == idInventario)
                .findFirst()
                .orElse(null);

        request.setAttribute("inventario", inventarioExistente);
        request.getRequestDispatcher("editarInventario.jsp").forward(request, response);
    }

    private void actualizarInventario(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int idInventario = Integer.parseInt(request.getParameter("id"));
        int idProducto = Integer.parseInt(request.getParameter("idProducto"));
        String sucursal = request.getParameter("sucursal");
        int stock = Integer.parseInt(request.getParameter("stock"));
        String ubicacion = request.getParameter("ubicacion");

        Inventario inventario = new Inventario();
        inventario.setIdInventario(idInventario);
        inventario.setIdProducto(idProducto);
        inventario.setSucursal(sucursal);
        inventario.setStock(stock);
        inventario.setUbicacion(ubicacion);

        inventarioDAO.actualizarInventario(inventario);
        response.sendRedirect("InventarioServlet");
    }

    private void eliminarInventario(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int idInventario = Integer.parseInt(request.getParameter("id"));
        inventarioDAO.eliminarInventario(idInventario);
        response.sendRedirect("InventarioServlet");
    }

    @Override
    public void destroy() {
    }
}
