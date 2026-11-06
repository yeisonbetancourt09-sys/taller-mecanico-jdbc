package com.tallermecanico.dao;

import java.util.List;
import java.util.Optional;

/**
 * Contrato genérico CRUD para la capa de acceso a datos.
 *
 * @param <T> tipo de entidad
 */
public interface CrudDao<T> {

    /** Inserta la entidad y retorna el id generado. */
    int insertar(T entidad);

    Optional<T> buscarPorId(int id);

    List<T> listarTodos();

    /** @return true si se actualizó algún registro */
    boolean actualizar(T entidad);

    /** @return true si se eliminó algún registro */
    boolean eliminar(int id);
}
