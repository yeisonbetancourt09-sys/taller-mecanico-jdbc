package com.tallermecanico.controlador;

import com.tallermecanico.excepcion.AccesoDatosException;
import com.tallermecanico.modelo.Cliente;
import com.tallermecanico.modelo.Vehiculo;
import com.tallermecanico.servicio.ClienteServicio;
import com.tallermecanico.servicio.VehiculoServicio;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@WebServlet(name = "VehiculoServlet", urlPatterns = "/vehiculos")
public class VehiculoServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private static final String VISTA_LISTAR = "/WEB-INF/jsp/vehiculos/listar.jsp";
    private static final String VISTA_FORMULARIO = "/WEB-INF/jsp/vehiculos/formulario.jsp";
    private static final String VISTA_ERROR = "/WEB-INF/jsp/error.jsp";

    private final transient VehiculoServicio vehiculoServicio = new VehiculoServicio();
    private final transient ClienteServicio clienteServicio = new ClienteServicio();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String accion = obtenerParametro(request, "accion", "listar");
        try {
            switch (accion) {
                case "nuevo" -> mostrarFormulario(request, response, new Vehiculo());
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
        int idCliente = convertirAEntero(request.getParameter("idCliente"));
        List<Vehiculo> vehiculos = (idCliente == 0)
                ? vehiculoServicio.listarVehiculos()
                : vehiculoServicio.listarVehiculosDeCliente(idCliente);
        List<Cliente> clientes = clienteServicio.listarClientes();

        Map<Integer, String> nombresClientes = new HashMap<>();
        for (Cliente cliente : clientes) {
            nombresClientes.put(cliente.getIdCliente(), cliente.getNombre());
        }

        request.setAttribute("vehiculos", vehiculos);
        request.setAttribute("clientes", clientes);
        request.setAttribute("nombresClientes", nombresClientes);
        request.getRequestDispatcher(VISTA_LISTAR).forward(request, response);
    }

    private void editar(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int idVehiculo = convertirAEntero(request.getParameter("id"));
        Optional<Vehiculo> vehiculo = vehiculoServicio.consultarPorId(idVehiculo);
        if (vehiculo.isEmpty()) {
            registrarMensaje(request, "El vehículo solicitado no existe", "error");
            response.sendRedirect(request.getContextPath() + "/vehiculos");
            return;
        }
        mostrarFormulario(request, response, vehiculo.get());
    }

    private void mostrarFormulario(HttpServletRequest request, HttpServletResponse response, Vehiculo vehiculo)
            throws ServletException, IOException {
        request.setAttribute("vehiculo", vehiculo);
        request.setAttribute("clientes", clienteServicio.listarClientes());
        request.getRequestDispatcher(VISTA_FORMULARIO).forward(request, response);
    }

    private void guardar(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Vehiculo vehiculo = leerVehiculo(request);
        try {
            if (vehiculo.getIdVehiculo() == 0) {
                vehiculoServicio.registrarVehiculo(vehiculo);
                registrarMensaje(request, "Vehículo registrado correctamente", "exito");
            } else {
                vehiculoServicio.actualizarVehiculo(vehiculo);
                registrarMensaje(request, "Vehículo actualizado correctamente", "exito");
            }
            response.sendRedirect(request.getContextPath() + "/vehiculos");
        } catch (IllegalArgumentException | AccesoDatosException e) {
            request.setAttribute("error", e.getMessage());
            mostrarFormulario(request, response, vehiculo);
        }
    }

    private void eliminar(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int idVehiculo = convertirAEntero(request.getParameter("id"));
        try {
            if (vehiculoServicio.eliminarVehiculo(idVehiculo)) {
                registrarMensaje(request, "Vehículo eliminado correctamente", "exito");
            } else {
                registrarMensaje(request, "El vehículo no existe", "error");
            }
        } catch (AccesoDatosException e) {
            registrarMensaje(request, e.getMessage(), "error");
        }
        response.sendRedirect(request.getContextPath() + "/vehiculos");
    }

    private Vehiculo leerVehiculo(HttpServletRequest request) {
        Vehiculo vehiculo = new Vehiculo();
        vehiculo.setIdVehiculo(convertirAEntero(request.getParameter("idVehiculo")));
        vehiculo.setPlaca(obtenerParametro(request, "placa", ""));
        vehiculo.setMarca(obtenerParametro(request, "marca", ""));
        vehiculo.setModelo(obtenerParametro(request, "modelo", ""));
        vehiculo.setAnio(convertirAEntero(request.getParameter("anio")));
        vehiculo.setColor(obtenerParametro(request, "color", ""));
        vehiculo.setIdCliente(convertirAEntero(request.getParameter("idCliente")));
        return vehiculo;
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
