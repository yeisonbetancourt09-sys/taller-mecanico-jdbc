package com.tallermecanico.dao;

import com.tallermecanico.config.ConexionBD;
import com.tallermecanico.excepcion.AccesoDatosException;
import com.tallermecanico.modelo.Vehiculo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Implementación JDBC de VehiculoDao.
 */
public class VehiculoDaoJdbc implements VehiculoDao {

    private static final String SQL_INSERTAR =
            "INSERT INTO vehiculos (placa, marca, modelo, anio, color, id_cliente) VALUES (?, ?, ?, ?, ?, ?)";
    private static final String SQL_BUSCAR_ID =
            "SELECT * FROM vehiculos WHERE id_vehiculo = ?";
    private static final String SQL_BUSCAR_PLACA =
            "SELECT * FROM vehiculos WHERE placa = ?";
    private static final String SQL_LISTAR =
            "SELECT * FROM vehiculos ORDER BY placa";
    private static final String SQL_LISTAR_POR_CLIENTE =
            "SELECT * FROM vehiculos WHERE id_cliente = ? ORDER BY placa";
    private static final String SQL_ACTUALIZAR =
            "UPDATE vehiculos SET placa = ?, marca = ?, modelo = ?, anio = ?, color = ?, id_cliente = ? "
                    + "WHERE id_vehiculo = ?";
    private static final String SQL_ELIMINAR =
            "DELETE FROM vehiculos WHERE id_vehiculo = ?";

    @Override
    public int insertar(Vehiculo vehiculo) {
        try (Connection conexion = ConexionBD.obtenerConexion();
             PreparedStatement sentencia = conexion.prepareStatement(SQL_INSERTAR,
                     Statement.RETURN_GENERATED_KEYS)) {
            asignarParametros(sentencia, vehiculo);
            sentencia.executeUpdate();
            try (ResultSet claves = sentencia.getGeneratedKeys()) {
                return claves.next() ? claves.getInt(1) : 0;
            }
        } catch (SQLException e) {
            throw new AccesoDatosException("Error al insertar el vehículo: " + e.getMessage(), e);
        }
    }

    @Override
    public Optional<Vehiculo> buscarPorId(int id) {
        try (Connection conexion = ConexionBD.obtenerConexion();
             PreparedStatement sentencia = conexion.prepareStatement(SQL_BUSCAR_ID)) {
            sentencia.setInt(1, id);
            try (ResultSet resultado = sentencia.executeQuery()) {
                return resultado.next() ? Optional.of(mapearVehiculo(resultado)) : Optional.empty();
            }
        } catch (SQLException e) {
            throw new AccesoDatosException("Error al consultar el vehículo", e);
        }
    }

    @Override
    public Optional<Vehiculo> buscarPorPlaca(String placa) {
        try (Connection conexion = ConexionBD.obtenerConexion();
             PreparedStatement sentencia = conexion.prepareStatement(SQL_BUSCAR_PLACA)) {
            sentencia.setString(1, placa);
            try (ResultSet resultado = sentencia.executeQuery()) {
                return resultado.next() ? Optional.of(mapearVehiculo(resultado)) : Optional.empty();
            }
        } catch (SQLException e) {
            throw new AccesoDatosException("Error al consultar el vehículo por placa", e);
        }
    }

    @Override
    public List<Vehiculo> listarTodos() {
        List<Vehiculo> vehiculos = new ArrayList<>();
        try (Connection conexion = ConexionBD.obtenerConexion();
             PreparedStatement sentencia = conexion.prepareStatement(SQL_LISTAR);
             ResultSet resultado = sentencia.executeQuery()) {
            while (resultado.next()) {
                vehiculos.add(mapearVehiculo(resultado));
            }
        } catch (SQLException e) {
            throw new AccesoDatosException("Error al listar los vehículos", e);
        }
        return vehiculos;
    }

    @Override
    public List<Vehiculo> listarPorCliente(int idCliente) {
        List<Vehiculo> vehiculos = new ArrayList<>();
        try (Connection conexion = ConexionBD.obtenerConexion();
             PreparedStatement sentencia = conexion.prepareStatement(SQL_LISTAR_POR_CLIENTE)) {
            sentencia.setInt(1, idCliente);
            try (ResultSet resultado = sentencia.executeQuery()) {
                while (resultado.next()) {
                    vehiculos.add(mapearVehiculo(resultado));
                }
            }
        } catch (SQLException e) {
            throw new AccesoDatosException("Error al listar los vehículos del cliente", e);
        }
        return vehiculos;
    }

    @Override
    public boolean actualizar(Vehiculo vehiculo) {
        try (Connection conexion = ConexionBD.obtenerConexion();
             PreparedStatement sentencia = conexion.prepareStatement(SQL_ACTUALIZAR)) {
            asignarParametros(sentencia, vehiculo);
            sentencia.setInt(7, vehiculo.getIdVehiculo());
            return sentencia.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new AccesoDatosException("Error al actualizar el vehículo: " + e.getMessage(), e);
        }
    }

    @Override
    public boolean eliminar(int id) {
        try (Connection conexion = ConexionBD.obtenerConexion();
             PreparedStatement sentencia = conexion.prepareStatement(SQL_ELIMINAR)) {
            sentencia.setInt(1, id);
            return sentencia.executeUpdate() > 0;
        } catch (SQLIntegrityConstraintViolationException e) {
            throw new AccesoDatosException(
                    "No se puede eliminar el vehículo porque tiene órdenes de trabajo asociadas", e);
        } catch (SQLException e) {
            throw new AccesoDatosException("Error al eliminar el vehículo", e);
        }
    }

    private void asignarParametros(PreparedStatement sentencia, Vehiculo vehiculo) throws SQLException {
        sentencia.setString(1, vehiculo.getPlaca());
        sentencia.setString(2, vehiculo.getMarca());
        sentencia.setString(3, vehiculo.getModelo());
        sentencia.setInt(4, vehiculo.getAnio());
        sentencia.setString(5, vehiculo.getColor());
        sentencia.setInt(6, vehiculo.getIdCliente());
    }

    private Vehiculo mapearVehiculo(ResultSet resultado) throws SQLException {
        Vehiculo vehiculo = new Vehiculo();
        vehiculo.setIdVehiculo(resultado.getInt("id_vehiculo"));
        vehiculo.setPlaca(resultado.getString("placa"));
        vehiculo.setMarca(resultado.getString("marca"));
        vehiculo.setModelo(resultado.getString("modelo"));
        vehiculo.setAnio(resultado.getInt("anio"));
        vehiculo.setColor(resultado.getString("color"));
        vehiculo.setIdCliente(resultado.getInt("id_cliente"));
        return vehiculo;
    }
}
