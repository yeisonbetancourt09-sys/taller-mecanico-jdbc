package com.tallermecanico.controlador;

import com.tallermecanico.excepcion.AccesoDatosException;
import com.tallermecanico.servicio.ClienteServicio;
import com.tallermecanico.servicio.VehiculoServicio;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

/**
 * Página de inicio: muestra el total de clientes y vehículos registrados.
 */
@WebServlet(name = "InicioServlet", urlPatterns = "/inicio")
public class InicioServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    private final transient ClienteServicio clienteServicio = new ClienteServicio();
    private final transient VehiculoServicio vehiculoServicio = new VehiculoServicio();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            request.setAttribute("totalClientes", clienteServicio.listarClientes().size());
            request.setAttribute("totalVehiculos", vehiculoServicio.listarVehiculos().size());
        } catch (AccesoDatosException e) {
            request.setAttribute("errorConexion", e.getMessage());
        }
        request.getRequestDispatcher("/WEB-INF/jsp/inicio.jsp").forward(request, response);
    }
}
