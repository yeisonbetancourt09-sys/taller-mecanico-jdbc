package com.tallermecanico.dao;

import com.tallermecanico.modelo.Cliente;

import java.util.Optional;

public interface ClienteDao extends CrudDao<Cliente> {

    Optional<Cliente> buscarPorDocumento(String documento);
}
