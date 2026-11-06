package com.tallermecanico.dao;

import com.tallermecanico.config.ConexionBD;
import com.tallermecanico.excepcion.AccesoDatosException;
import com.tallermecanico.modelo.Cliente;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Implementación JDBC de ClienteDao. Usa PreparedStatement para evitar inyección SQL.
 */
public class ClienteDaoJdbc implements ClienteDao {

    private static final String SQL_INSERTAR =
            "INSERT INTO clientes (documento, nombre, telefono, email, direccion) VALUES (?, ?, ?, ?, ?)";
    private static final String SQL_BUSCAR_ID =
            "SELECT * FROM clientes WHERE id_cliente = ?";
    private static final String SQL_BUSCAR_DOCUMENTO =
            "SELECT * FROM clientes WHERE documento = ?";
    private static final String SQL_LISTAR =
            "SELECT * FROM clientes ORDER BY nombre";
    private static final String SQL_ACTUALIZAR =
            "UPDATE clientes SET documento = ?, nombre = ?, telefono = ?, email = ?, direccion = ? "
                    + "WHERE id_cliente = ?";
    private static final String SQL_ELIMINAR =
            "DELETE FROM clientes WHERE id_cliente = ?";

    @Override
    public int insertar(Cliente cliente) {
        try (Connection conexion = ConexionBD.obtenerConexion();
             PreparedStatement sentencia = conexion.prepareStatement(SQL_INSERTAR,
                     Statement.RETURN_GENERATED_KEYS)) {
            sentencia.setString(1, cliente.getDocumento());
            sentencia.setString(2, cliente.getNombre());
            sentencia.setString(3, cliente.getTelefono());
            sentencia.setString(4, cliente.getEmail());
            sentencia.setString(5, cliente.getDireccion());
            sentencia.executeUpdate();
            try (ResultSet claves = sentencia.getGeneratedKeys()) {
                return claves.next() ? claves.getInt(1) : 0;
            }
        } catch (SQLException e) {
            throw new AccesoDatosException("Error al insertar el cliente: " + e.getMessage(), e);
        }
    }

    @Override
    public Optional<Cliente> buscarPorId(int id) {
        try (Connection conexion = ConexionBD.obtenerConexion();
             PreparedStatement sentencia = conexion.prepareStatement(SQL_BUSCAR_ID)) {
            sentencia.setInt(1, id);
            try (ResultSet resultado = sentencia.executeQuery()) {
                return resultado.next() ? Optional.of(mapearCliente(resultado)) : Optional.empty();
            }
        } catch (SQLException e) {
            throw new AccesoDatosException("Error al consultar el cliente", e);
        }
    }

    @Override
    public Optional<Cliente> buscarPorDocumento(String documento) {
        try (Connection conexion = ConexionBD.obtenerConexion();
             PreparedStatement sentencia = conexion.prepareStatement(SQL_BUSCAR_DOCUMENTO)) {
            sentencia.setString(1, documento);
            try (ResultSet resultado = sentencia.executeQuery()) {
                return resultado.next() ? Optional.of(mapearCliente(resultado)) : Optional.empty();
            }
        } catch (SQLException e) {
            throw new AccesoDatosException("Error al consultar el cliente por documento", e);
        }
    }

    @Override
    public List<Cliente> listarTodos() {
        List<Cliente> clientes = new ArrayList<>();
        try (Connection conexion = ConexionBD.obtenerConexion();
             PreparedStatement sentencia = conexion.prepareStatement(SQL_LISTAR);
             ResultSet resultado = sentencia.executeQuery()) {
            while (resultado.next()) {
                clientes.add(mapearCliente(resultado));
            }
        } catch (SQLException e) {
            throw new AccesoDatosException("Error al listar los clientes", e);
        }
        return clientes;
    }

    @Override
    public boolean actualizar(Cliente cliente) {
        try (Connection conexion = ConexionBD.obtenerConexion();
             PreparedStatement sentencia = conexion.prepareStatement(SQL_ACTUALIZAR)) {
            sentencia.setString(1, cliente.getDocumento());
            sentencia.setString(2, cliente.getNombre());
            sentencia.setString(3, cliente.getTelefono());
            sentencia.setString(4, cliente.getEmail());
            sentencia.setString(5, cliente.getDireccion());
            sentencia.setInt(6, cliente.getIdCliente());
            return sentencia.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new AccesoDatosException("Error al actualizar el cliente: " + e.getMessage(), e);
        }
    }

    @Override
    public boolean eliminar(int id) {
        try (Connection conexion = ConexionBD.obtenerConexion();
             PreparedStatement sentencia = conexion.prepareStatement(SQL_ELIMINAR)) {
            sentencia.setInt(1, id);
            return sentencia.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new AccesoDatosException(
                    "No se pudo eliminar el cliente (verifique que no tenga vehículos asociados)", e);
        }
    }

    private Cliente mapearCliente(ResultSet resultado) throws SQLException {
        Cliente cliente = new Cliente();
        cliente.setIdCliente(resultado.getInt("id_cliente"));
        cliente.setDocumento(resultado.getString("documento"));
        cliente.setNombre(resultado.getString("nombre"));
        cliente.setTelefono(resultado.getString("telefono"));
        cliente.setEmail(resultado.getString("email"));
        cliente.setDireccion(resultado.getString("direccion"));
        return cliente;
    }
}
