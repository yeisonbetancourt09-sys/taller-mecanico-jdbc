package com.tallermecanico.servicio;

import com.tallermecanico.dao.ClienteDao;
import com.tallermecanico.dao.ClienteDaoJdbc;
import com.tallermecanico.dao.VehiculoDao;
import com.tallermecanico.dao.VehiculoDaoJdbc;
import com.tallermecanico.modelo.Cliente;

import java.util.List;
import java.util.Optional;

public class ClienteServicio {

    private final ClienteDao clienteDao;
    private final VehiculoDao vehiculoDao;

    public ClienteServicio() {
        this.clienteDao = new ClienteDaoJdbc();
        this.vehiculoDao = new VehiculoDaoJdbc();
    }

    public int registrarCliente(Cliente cliente) {
        validarCliente(cliente);
        if (clienteDao.buscarPorDocumento(cliente.getDocumento()).isPresent()) {
            throw new IllegalArgumentException("Ya existe un cliente con ese documento");
        }
        return clienteDao.insertar(cliente);
    }

    public Optional<Cliente> consultarPorId(int idCliente) {
        return clienteDao.buscarPorId(idCliente);
    }

    public Optional<Cliente> consultarPorDocumento(String documento) {
        return clienteDao.buscarPorDocumento(documento);
    }

    public List<Cliente> listarClientes() {
        return clienteDao.listarTodos();
    }

    public boolean actualizarCliente(Cliente cliente) {
        validarCliente(cliente);
        return clienteDao.actualizar(cliente);
    }

    public boolean eliminarCliente(int idCliente) {
        if (!vehiculoDao.listarPorCliente(idCliente).isEmpty()) {
            throw new IllegalArgumentException(
                    "El cliente tiene vehículos registrados; elimínelos primero para poder borrarlo");
        }
        return clienteDao.eliminar(idCliente);
    }

    private void validarCliente(Cliente cliente) {
        if (esVacio(cliente.getDocumento()) || esVacio(cliente.getNombre())
                || esVacio(cliente.getTelefono())) {
            throw new IllegalArgumentException("Documento, nombre y teléfono son obligatorios");
        }
        if (!esVacio(cliente.getEmail())
                && !cliente.getEmail().matches("^[\\w.+-]+@[\\w-]+\\.[\\w.-]+$")) {
            throw new IllegalArgumentException("El correo electrónico no tiene un formato válido");
        }
    }

    private boolean esVacio(String texto) {
        return texto == null || texto.trim().isEmpty();
    }
}
