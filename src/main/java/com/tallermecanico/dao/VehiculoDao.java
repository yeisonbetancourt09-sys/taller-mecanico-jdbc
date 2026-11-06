package com.tallermecanico.dao;

import com.tallermecanico.modelo.Vehiculo;

import java.util.List;
import java.util.Optional;

public interface VehiculoDao extends CrudDao<Vehiculo> {

    Optional<Vehiculo> buscarPorPlaca(String placa);

    List<Vehiculo> listarPorCliente(int idCliente);
}
