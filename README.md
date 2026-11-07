# Taller Mecánico – Módulo Clientes y Vehículos (Java + JDBC + Servlets + JSP)

CRUD completo (insertar, consultar, actualizar, eliminar) con arquitectura en capas:

| Paquete | Responsabilidad |
|---|---|
| `com.tallermecanico.config` | Conexión JDBC (`ConexionBD`) |
| `com.tallermecanico.modelo` | Entidades (`Cliente`, `Vehiculo`) |
| `com.tallermecanico.dao` | Acceso a datos (interfaces + implementaciones JDBC) |
| `com.tallermecanico.servicio` | Reglas de negocio y validaciones |
| `com.tallermecanico.controlador` | Servlets (`InicioServlet`, `ClienteServlet`, `VehiculoServlet`) |
| `com.tallermecanico.filtro` | Filtro UTF-8 |
| `com.tallermecanico.vista` | Menú de consola (versión alternativa) |
| `com.tallermecanico.excepcion` | Excepciones propias |

Requisitos
JDK 21, Maven 3.8+, MySQL 8+, apache-tomcat-10.1.61
