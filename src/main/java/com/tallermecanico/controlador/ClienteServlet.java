package com.tallermecanico.controlador;

import com.tallermecanico.excepcion.AccesoDatosException;
import com.tallermecanico.modelo.Cliente;
import com.tallermecanico.servicio.ClienteServicio;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@WebServlet(name = "ClienteServlet", urlPatterns = "/clientes")
public class ClienteServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private static final String VISTA_LISTAR = "/WEB-INF/jsp/clientes/listar.jsp";
    private static final String VISTA_FORMULARIO = "/WEB-INF/jsp/clientes/formulario.jsp";
    private static final String VISTA_ERROR = "/WEB-INF/jsp/error.jsp";

    private final transient ClienteServicio clienteServicio = new ClienteServicio();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String accion = obtenerParametro(request, "accion", "listar");
        try {
            switch (accion) {
                case "nuevo" -> mostrarFormulario(request, response, new Cliente());
                case "editar" -> editar(request, response);
                default -> listar(request, response);
            }
        } catch (AccesoDatosException e) {
            mostrarError(request, response, e.getMessage());
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String accion = obtenerParametro(request, "accion", "");
        try {
            switch (accion) {
                case "guardar" -> guardar(request, response);
                case "eliminar" -> eliminar(request, response);
                default -> response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Acción no válida");
            }
        } catch (AccesoDatosException e) {
            mostrarError(request, response, e.getMessage());
        }
    }

    private void listar(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String documento = obtenerParametro(request, "documento", "");
        List<Cliente> clientes;
        if (documento.isEmpty()) {
            clientes = clienteServicio.listarClientes();
        } else {
            clientes = clienteServicio.consultarPorDocumento(documento).map(List::of).orElse(List.of());
        }
        request.setAttribute("clientes", clientes);
        request.getRequestDispatcher(VISTA_LISTAR).forward(request, response);
    }

    private void editar(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int idCliente = convertirAEntero(request.getParameter("id"));
        Optional<Cliente> cliente = clienteServicio.consultarPorId(idCliente);
        if (cliente.isEmpty()) {
            registrarMensaje(request, "El cliente solicitado no existe", "error");
            response.sendRedirect(request.getContextPath() + "/clientes");
            return;
        }
        mostrarFormulario(request, response, cliente.get());
    }

    private void mostrarFormulario(HttpServletRequest request, HttpServletResponse response, Cliente cliente)
            throws ServletException, IOException {
        request.setAttribute("cliente", cliente);
        request.getRequestDispatcher(VISTA_FORMULARIO).forward(request, response);
    }

    // ------------------------- acciones POST -------------------------

    private void guardar(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Cliente cliente = leerCliente(request);
        try {
            if (cliente.getIdCliente() == 0) {
                clienteServicio.registrarCliente(cliente);
                registrarMensaje(request, "Cliente registrado correctamente", "exito");
            } else {
                clienteServicio.actualizarCliente(cliente);
                registrarMensaje(request, "Cliente actualizado correctamente", "exito");
            }
            response.sendRedirect(request.getContextPath() + "/clientes");
        } catch (IllegalArgumentException e) {
            request.setAttribute("error", e.getMessage());
            mostrarFormulario(request, response, cliente);
        }
    }

    private void eliminar(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int idCliente = convertirAEntero(request.getParameter("id"));
        try {
            if (clienteServicio.eliminarCliente(idCliente)) {
                registrarMensaje(request, "Cliente eliminado correctamente", "exito");
            } else {
                registrarMensaje(request, "El cliente no existe", "error");
            }
        } catch (IllegalArgumentException | AccesoDatosException e) {
            registrarMensaje(request, e.getMessage(), "error");
        }
        response.sendRedirect(request.getContextPath() + "/clientes");
    }

    private Cliente leerCliente(HttpServletRequest request) {
        Cliente cliente = new Cliente();
        cliente.setIdCliente(convertirAEntero(request.getParameter("idCliente")));
        cliente.setDocumento(obtenerParametro(request, "documento", ""));
        cliente.setNombre(obtenerParametro(request, "nombre", ""));
        cliente.setTelefono(obtenerParametro(request, "telefono", ""));
        cliente.setEmail(obtenerParametro(request, "email", ""));
        cliente.setDireccion(obtenerParametro(request, "direccion", ""));
        return cliente;
    }

    private String obtenerParametro(HttpServletRequest request, String nombre, String valorPorDefecto) {
        String valor = request.getParameter(nombre);
        return (valor == null || valor.trim().isEmpty()) ? valorPorDefecto : valor.trim();
    }

    private int convertirAEntero(String texto) {
        try {
            return Integer.parseInt(texto);
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    private void registrarMensaje(HttpServletRequest request, String mensaje, String tipo) {
        request.getSession().setAttribute("mensaje", mensaje);
        request.getSession().setAttribute("tipoMensaje", tipo);
    }

    private void mostrarError(HttpServletRequest request, HttpServletResponse response, String mensaje)
            throws ServletException, IOException {
        request.setAttribute("mensajeError", mensaje);
        request.getRequestDispatcher(VISTA_ERROR).forward(request, response);
    }
}
