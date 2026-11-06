package com.tallermecanico.servicio;

import com.tallermecanico.dao.ClienteDao;
import com.tallermecanico.dao.ClienteDaoJdbc;
import com.tallermecanico.dao.VehiculoDao;
import com.tallermecanico.dao.VehiculoDaoJdbc;
import com.tallermecanico.modelo.Vehiculo;

import java.time.Year;
import java.util.List;
import java.util.Optional;

/**
 * Reglas de negocio y validaciones del módulo de vehículos.
 */
public class VehiculoServicio {

    private static final int ANIO_MINIMO = 1950;

    private final VehiculoDao vehiculoDao;
    private final ClienteDao clienteDao;

    public VehiculoServicio() {
        this.vehiculoDao = new VehiculoDaoJdbc();
        this.clienteDao = new ClienteDaoJdbc();
    }

    public int registrarVehiculo(Vehiculo vehiculo) {
        validarVehiculo(vehiculo);
        if (vehiculoDao.buscarPorPlaca(vehiculo.getPlaca()).isPresent()) {
            throw new IllegalArgumentException("Ya existe un vehículo con esa placa");
        }
        return vehiculoDao.insertar(vehiculo);
    }

    public Optional<Vehiculo> consultarPorId(int idVehiculo) {
        return vehiculoDao.buscarPorId(idVehiculo);
    }

    public Optional<Vehiculo> consultarPorPlaca(String placa) {
        return vehiculoDao.buscarPorPlaca(placa.trim().toUpperCase());
    }

    public List<Vehiculo> listarVehiculos() {
        return vehiculoDao.listarTodos();
    }

    public List<Vehiculo> listarVehiculosDeCliente(int idCliente) {
        return vehiculoDao.listarPorCliente(idCliente);
    }

    public boolean actualizarVehiculo(Vehiculo vehiculo) {
        validarVehiculo(vehiculo);
        return vehiculoDao.actualizar(vehiculo);
    }

    public boolean eliminarVehiculo(int idVehiculo) {
        return vehiculoDao.eliminar(idVehiculo);
    }

    private void validarVehiculo(Vehiculo vehiculo) {
        if (vehiculo.getPlaca() == null || vehiculo.getPlaca().trim().isEmpty()
                || vehiculo.getMarca() == null || vehiculo.getMarca().trim().isEmpty()
                || vehiculo.getModelo() == null || vehiculo.getModelo().trim().isEmpty()) {
            throw new IllegalArgumentException("Placa, marca y modelo son obligatorios");
        }
        vehiculo.setPlaca(vehiculo.getPlaca().trim().toUpperCase());
        int anioActual = Year.now().getValue();
        if (vehiculo.getAnio() < ANIO_MINIMO || vehiculo.getAnio() > anioActual + 1) {
            throw new IllegalArgumentException(
                    "El año debe estar entre " + ANIO_MINIMO + " y " + (anioActual + 1));
        }
        if (clienteDao.buscarPorId(vehiculo.getIdCliente()).isEmpty()) {
            throw new IllegalArgumentException("El cliente asociado no existe");
        }
    }
}
