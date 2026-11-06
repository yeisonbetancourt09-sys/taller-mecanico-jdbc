package com.tallermecanico.excepcion;

/**
 * Excepción propia de la capa de acceso a datos. Envuelve las SQLException.
 */
public class AccesoDatosException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public AccesoDatosException(String mensaje, Throwable causa) {
        super(mensaje, causa);
    }

    public AccesoDatosException(String mensaje) {
        super(mensaje);
    }
}
