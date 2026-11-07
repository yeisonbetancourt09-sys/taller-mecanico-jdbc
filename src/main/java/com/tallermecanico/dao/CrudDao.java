package com.tallermecanico.dao;

import java.util.List;
import java.util.Optional;

public interface CrudDao<T> {

    int insertar(T entidad);

    Optional<T> buscarPorId(int id);

    List<T> listarTodos();

    boolean actualizar(T entidad);

    boolean eliminar(int id);
}
