package com.mycompany.kl_farma.servlet;

import com.mycompany.kl_farma.DAO.ProductoDAO;
import com.mycompany.kl_farma.model.Producto;
import com.mycompany.kl_farma.util.Conexion;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.Connection;
import java.util.List;

@WebServlet("/ProductoServlet")
public class ProductoServlet extends HttpServlet {
    private ProductoDAO productoDAO;

    @Override
    public void init() throws ServletException {
        Connection connection = Conexion.getConnection();
        productoDAO = new ProductoDAO(connection);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        try {
            if (action == null) {
                listarProductos(request, response);
            } else {
                switch (action) {
                    case "edit":
                        mostrarFormularioEdicion(request, response);
                        break;
                    case "delete":
                        eliminarProducto(request, response);
                        break;
                    default:
                        listarProductos(request, response);
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
                response.sendRedirect("ProductoServlet");
            } else {
                switch (action) {
                    case "add":
                        agregarProducto(request, response);
                        break;
                    case "update":
                        actualizarProducto(request, response);
                        break;
                    default:
                        response.sendRedirect("ProductoServlet");
                        break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

    private void listarProductos(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Producto> listaProductos = productoDAO.listarProductos();
        request.setAttribute("listaProductos", listaProductos);
        request.getRequestDispatcher("productos.jsp").forward(request, response);
    }

    private void agregarProducto(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String nombre = request.getParameter("nombre");
        String descripcion = request.getParameter("descripcion");
        double precio = Double.parseDouble(request.getParameter("precio"));
        int stock = Integer.parseInt(request.getParameter("stock"));

        Producto producto = new Producto();
        producto.setNombre(nombre);
        producto.setDescripcion(descripcion);
        producto.setPrecio(precio);
        producto.setStock(stock);

        productoDAO.agregarProducto(producto);
        response.sendRedirect("ProductoServlet");
    }

    private void mostrarFormularioEdicion(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int idProducto = Integer.parseInt(request.getParameter("id"));
        Producto productoExistente = productoDAO.listarProductos().stream()
                .filter(p -> p.getIdProducto() == idProducto)
                .findFirst()
                .orElse(null);

        request.setAttribute("producto", productoExistente);
        request.getRequestDispatcher("editarProducto.jsp").forward(request, response);
    }

    private void actualizarProducto(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int idProducto = Integer.parseInt(request.getParameter("id"));
        String nombre = request.getParameter("nombre");
        String descripcion = request.getParameter("descripcion");
        double precio = Double.parseDouble(request.getParameter("precio"));
        int stock = Integer.parseInt(request.getParameter("stock"));

        Producto producto = new Producto();
        producto.setIdProducto(idProducto);
        producto.setNombre(nombre);
        producto.setDescripcion(descripcion);
        producto.setPrecio(precio);
        producto.setStock(stock);

        productoDAO.actualizarProducto(producto);
        response.sendRedirect("ProductoServlet");
    }

    private void eliminarProducto(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int idProducto = Integer.parseInt(request.getParameter("id"));
        productoDAO.eliminarProducto(idProducto);
        response.sendRedirect("ProductoServlet");
    }

    @Override
    public void destroy() {
        Conexion.closeConnection(Conexion.getConnection());

    }
}
