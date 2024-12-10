package com.mycompany.kl_farma.servlet;

import com.mycompany.kl_farma.DAO.EmpleadoDAO;
import com.mycompany.kl_farma.model.Empleado;
import com.mycompany.kl_farma.util.Conexion;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.Connection;
import java.util.List;

@WebServlet("/EmpleadoServlet")
public class EmpleadoServlet extends HttpServlet {
    private EmpleadoDAO empleadoDAO;

    @Override
    public void init() throws ServletException {
        Connection connection = Conexion.getConnection();
        empleadoDAO = new EmpleadoDAO(connection);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        try {
            if (action == null) {
                listarEmpleados(request, response);
            } else {
                switch (action) {
                    case "edit":
                        mostrarFormularioEdicion(request, response);
                        break;
                    case "delete":
                        eliminarEmpleado(request, response);
                        break;
                    default:
                        listarEmpleados(request, response);
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
                response.sendRedirect("EmpleadoServlet");
            } else {
                switch (action) {
                    case "add":
                        agregarEmpleado(request, response);
                        break;
                    case "update":
                        actualizarEmpleado(request, response);
                        break;
                    default:
                        response.sendRedirect("EmpleadoServlet");
                        break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

    private void listarEmpleados(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Empleado> listaEmpleados = empleadoDAO.listarEmpleados();
        request.setAttribute("listaEmpleados", listaEmpleados);
        request.getRequestDispatcher("empleados.jsp").forward(request, response);
    }

    private void agregarEmpleado(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String nombre = request.getParameter("nombre");
        String apellido = request.getParameter("apellido");
        String rol = request.getParameter("rol");
        String usuario = request.getParameter("usuario");
        String contrasena = request.getParameter("contrasena");

        Empleado empleado = new Empleado();
        empleado.setNombre(nombre);
        empleado.setApellido(apellido);
        empleado.setRol(rol);
        empleado.setUsuario(usuario);
        empleado.setContrasena(contrasena);

        empleadoDAO.agregarEmpleado(empleado);
        response.sendRedirect("EmpleadoServlet");
    }

    private void mostrarFormularioEdicion(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int idEmpleado = Integer.parseInt(request.getParameter("id"));
        Empleado empleadoExistente = empleadoDAO.listarEmpleados().stream()
                .filter(e -> e.getIdEmpleado() == idEmpleado)
                .findFirst()
                .orElse(null);

        request.setAttribute("empleado", empleadoExistente);
        request.getRequestDispatcher("editarEmpleado.jsp").forward(request, response);
    }

    private void actualizarEmpleado(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int idEmpleado = Integer.parseInt(request.getParameter("id"));
        String nombre = request.getParameter("nombre");
        String apellido = request.getParameter("apellido");
        String rol = request.getParameter("rol");
        String usuario = request.getParameter("usuario");
        String contrasena = request.getParameter("contrasena");

        Empleado empleado = new Empleado();
        empleado.setIdEmpleado(idEmpleado);
        empleado.setNombre(nombre);
        empleado.setApellido(apellido);
        empleado.setRol(rol);
        empleado.setUsuario(usuario);
        empleado.setContrasena(contrasena);

        empleadoDAO.actualizarEmpleado(empleado);
        response.sendRedirect("EmpleadoServlet");
    }

    private void eliminarEmpleado(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int idEmpleado = Integer.parseInt(request.getParameter("id"));
        empleadoDAO.eliminarEmpleado(idEmpleado);
        response.sendRedirect("EmpleadoServlet");
    }

    @Override
    public void destroy() {
    }
}
