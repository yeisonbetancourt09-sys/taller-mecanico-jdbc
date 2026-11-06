# Taller Mecánico – Módulo Clientes y Vehículos (Java + JDBC)

CRUD completo (insertar, consultar, actualizar, eliminar) con arquitectura en capas:

| Paquete | Responsabilidad |
|---|---|
| `com.tallermecanico.config` | Conexión JDBC (`ConexionBD`) |
| `com.tallermecanico.modelo` | Entidades (`Cliente`, `Vehiculo`) |
| `com.tallermecanico.dao` | Acceso a datos (interfaces + implementaciones JDBC) |
| `com.tallermecanico.servicio` | Reglas de negocio y validaciones |
| `com.tallermecanico.vista` | Menú de consola |
| `com.tallermecanico.excepcion` | Excepciones propias |

## Requisitos
JDK 17+, Maven 3.8+, MySQL 8+.

## Ejecución
1. Ejecutar `database/taller_mecanico.sql` en MySQL Workbench.
2. Ajustar usuario y clave en `src/main/resources/db.properties`.
3. `mvn compile exec:java`

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
