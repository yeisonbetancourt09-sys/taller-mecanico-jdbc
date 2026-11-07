package com.tallermecanico.config;

import com.tallermecanico.excepcion.AccesoDatosException;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 * Administra la conexión JDBC a MySQL leyendo los datos de db.properties.
 */
public final class ConexionBD {

    private static final String DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final String ARCHIVO_PROPIEDADES = "db.properties";
    private static final Properties PROPIEDADES = cargarPropiedades();

    private ConexionBD() {
    }

    private static Properties cargarPropiedades() {
        Properties propiedades = new Properties();
        try (InputStream entrada = ConexionBD.class.getClassLoader()
                .getResourceAsStream(ARCHIVO_PROPIEDADES)) {
            if (entrada == null) {
                throw new AccesoDatosException("No se encontró " + ARCHIVO_PROPIEDADES);
            }
            propiedades.load(entrada);
        } catch (IOException e) {
            throw new AccesoDatosException("Error leyendo la configuración de BD", e);
        }
        return propiedades;
    }

    /** Retorna una nueva conexión; quien la reciba debe cerrarla (try-with-resources). */
    public static Connection obtenerConexion() {
        try {
            Class.forName(DRIVER);
            return DriverManager.getConnection(
                    PROPIEDADES.getProperty("db.url"),
                    PROPIEDADES.getProperty("db.usuario"),
                    PROPIEDADES.getProperty("db.clave"));
        } catch (ClassNotFoundException e) {
            throw new AccesoDatosException("No se encontró el driver JDBC de MySQL", e);
        } catch (SQLException e) {
            throw new AccesoDatosException("No fue posible conectar con la base de datos", e);
        }
    }
}
