package com.mycompany.kl_farma.servlet;

import com.mycompany.kl_farma.DAO.ProveedorDAO;
import com.mycompany.kl_farma.model.Proveedor;
import com.mycompany.kl_farma.util.Conexion;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.Connection;
import java.util.List;

@WebServlet("/ProveedorServlet")
public class ProveedorServlet extends HttpServlet {
    private ProveedorDAO proveedorDAO;

    @Override
    public void init() throws ServletException {
        Connection connection = Conexion.getConnection();
        proveedorDAO = new ProveedorDAO(connection);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        try {
            if (action == null) {
                listarProveedores(request, response);
            } else {
                switch (action) {
                    case "edit":
                        mostrarFormularioEdicion(request, response);
                        break;
                    case "delete":
                        eliminarProveedor(request, response);
                        break;
                    default:
                        listarProveedores(request, response);
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
                response.sendRedirect("ProveedorServlet");
            } else {
                switch (action) {
                    case "add":
                        agregarProveedor(request, response);
                        break;
                    case "update":
                        actualizarProveedor(request, response);
                        break;
                    default:
                        response.sendRedirect("ProveedorServlet");
                        break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

    private void listarProveedores(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Proveedor> listaProveedores = proveedorDAO.listarProveedores();
        request.setAttribute("listaProveedores", listaProveedores);
        request.getRequestDispatcher("proveedores.jsp").forward(request, response);
    }

    private void agregarProveedor(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String nombre = request.getParameter("nombre");
        String direccion = request.getParameter("direccion");
        String telefono = request.getParameter("telefono");
        String correo = request.getParameter("correo");

        Proveedor proveedor = new Proveedor();
        proveedor.setNombre(nombre);
        proveedor.setDireccion(direccion);
        proveedor.setTelefono(telefono);
        proveedor.setCorreo(correo);

        proveedorDAO.agregarProveedor(proveedor);
        response.sendRedirect("ProveedorServlet");
    }

    private void mostrarFormularioEdicion(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int idProveedor = Integer.parseInt(request.getParameter("id"));
        Proveedor proveedorExistente = proveedorDAO.listarProveedores().stream()
                .filter(p -> p.getIdProveedor() == idProveedor)
                .findFirst()
                .orElse(null);

        request.setAttribute("proveedor", proveedorExistente);
        request.getRequestDispatcher("editarProveedor.jsp").forward(request, response);
    }

    private void actualizarProveedor(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int idProveedor = Integer.parseInt(request.getParameter("id"));
        String nombre = request.getParameter("nombre");
        String direccion = request.getParameter("direccion");
        String telefono = request.getParameter("telefono");
        String correo = request.getParameter("correo");

        Proveedor proveedor = new Proveedor();
        proveedor.setIdProveedor(idProveedor);
        proveedor.setNombre(nombre);
        proveedor.setDireccion(direccion);
        proveedor.setTelefono(telefono);
        proveedor.setCorreo(correo);

        proveedorDAO.actualizarProveedor(proveedor);
        response.sendRedirect("ProveedorServlet");
    }

    private void eliminarProveedor(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int idProveedor = Integer.parseInt(request.getParameter("id"));
        proveedorDAO.eliminarProveedor(idProveedor);
        response.sendRedirect("ProveedorServlet");
    }

    @Override
    public void destroy() {
    }
}
