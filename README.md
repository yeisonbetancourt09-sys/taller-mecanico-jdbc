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

## Requisitos
JDK 17+, Maven 3.8+, MySQL 8+, **Apache Tomcat 10.1 o superior** (Jakarta EE).

## Ejecución (aplicación web)
1. Tener la base `taller_mecanico` en MySQL. Si no existe, ejecutar `database/taller_mecanico.sql` (estructura y datos reales del proyecto).
2. Ajustar URL, usuario y clave en `src/main/resources/db.properties`.
3. `mvn clean package` genera `target/taller-mecanico.war`.
4. Copiar el WAR a `webapps/` de Tomcat (o desplegarlo desde NetBeans / Eclipse / IntelliJ).
5. Abrir `http://localhost:8080/taller-mecanico/`.

Versión de consola: `mvn compile exec:java`.

## Servlets, GET y POST
| URL | GET | POST |
|---|---|---|
| `/clientes` | listar, buscar por documento (`?documento=`), formulario nuevo/editar (`?accion=nuevo`, `?accion=editar&id=`) | `accion=guardar` (insertar/actualizar), `accion=eliminar` |
| `/vehiculos` | listar, filtrar por cliente (`?idCliente=`), formulario nuevo/editar | `accion=guardar`, `accion=eliminar` |
| `/inicio` | resumen con totales | – |

GET solo consulta; POST modifica datos y responde con redirect (Post-Redirect-Get).

## Elementos de JSP utilizados
- Directivas: `page`, `taglib`, `include` (fragmentos `encabezado.jspf` y `pie.jspf`).
- Expression Language (`${...}`) y objetos implícitos (`param`, `sessionScope`, `pageContext`).
- JSTL: `c:out`, `c:if`, `c:choose/when/otherwise`, `c:forEach`, `c:set`, `c:remove`.
- Acción estándar: `jsp:forward` (en `index.jsp`).
- Scriptlet y expresión (`<% %>`, `<%= %>`) en `pie.jspf`.
- Comentarios JSP `<%-- --%>`.

## Estándares de codificación
- Paquetes en minúscula; clases en `PascalCase`; métodos y variables en `camelCase`; constantes en `MAYUSCULAS_CON_GUION`.
- `PreparedStatement` y `try-with-resources` en todo acceso a BD.
- Una responsabilidad por clase (DAO / Servicio / Vista).

## Versionamiento (Git)
```bash
git init
git add .
git commit -m "feat: estructura inicial del proyecto con Maven"
git branch -M main
git remote add origin https://github.com/<usuario>/taller-mecanico-jdbc.git
git push -u origin main
```
Convención de commits sugerida: `feat:`, `fix:`, `docs:`, `refactor:`.
