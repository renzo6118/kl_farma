package com.mycompany.kl_farma.servlet;

import com.mycompany.kl_farma.DAO.ClienteDAO;
import com.mycompany.kl_farma.model.Cliente;
import com.mycompany.kl_farma.util.Conexion;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.util.List;

@WebServlet("/ClienteServlet")
public class ClienteServlet extends HttpServlet {
    private ClienteDAO clienteDAO;
    
    @Override
    public void init() throws ServletException {
        Connection connection = Conexion.getConnection();
        clienteDAO = new ClienteDAO(connection);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        try {
            if (action == null) {
                listarClientes(request, response);
            } else {
                switch (action) {
                    case "edit":
                        mostrarFormularioEdicion(request, response);
                        break;
                    case "delete":
                        eliminarCliente(request, response);
                        break;
                    default:
                        listarClientes(request, response);
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
                response.sendRedirect("ClienteServlet");
            } else {
                switch (action) {
                    case "add":
                        agregarCliente(request, response);
                        break;
                    case "update":
                        actualizarCliente(request, response);
                        break;
                    default:
                        response.sendRedirect("ClienteServlet");
                        break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

    private void listarClientes(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Cliente> listaClientes = clienteDAO.listarClientes();
        request.setAttribute("listaClientes", listaClientes);
        request.getRequestDispatcher("clientes.jsp").forward(request, response);
    }

    private void agregarCliente(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String nombre = request.getParameter("nombre");
        String direccion = request.getParameter("direccion");
        String telefono = request.getParameter("telefono");
        String email = request.getParameter("email");

        Cliente cliente = new Cliente();
        cliente.setNombre(nombre);
        cliente.setDireccion(direccion);
        cliente.setTelefono(telefono);
        cliente.setEmail(email);

        clienteDAO.agregarCliente(cliente);
        response.sendRedirect("ClienteServlet");
    }

    private void mostrarFormularioEdicion(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int idCliente = Integer.parseInt(request.getParameter("id"));
        Cliente clienteExistente = clienteDAO.listarClientes().stream()
                .filter(c -> c.getIdCliente() == idCliente)
                .findFirst()
                .orElse(null);

        request.setAttribute("cliente", clienteExistente);
        request.getRequestDispatcher("editarCliente.jsp").forward(request, response);
    }

    private void actualizarCliente(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int idCliente = Integer.parseInt(request.getParameter("id"));
        String nombre = request.getParameter("nombre");
        String direccion = request.getParameter("direccion");
        String telefono = request.getParameter("telefono");
        String email = request.getParameter("email");

        Cliente cliente = new Cliente();
        cliente.setIdCliente(idCliente);
        cliente.setNombre(nombre);
        cliente.setDireccion(direccion);
        cliente.setTelefono(telefono);
        cliente.setEmail(email);

        clienteDAO.actualizarCliente(cliente);
        response.sendRedirect("ClienteServlet");
    }

    private void eliminarCliente(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int idCliente = Integer.parseInt(request.getParameter("id"));
        clienteDAO.eliminarCliente(idCliente);
        response.sendRedirect("ClienteServlet");
    }

    @Override
    public void destroy() {
    }
}
