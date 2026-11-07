package com.tallermecanico.vista;

import com.tallermecanico.excepcion.AccesoDatosException;
import com.tallermecanico.modelo.Cliente;
import com.tallermecanico.modelo.Vehiculo;
import com.tallermecanico.servicio.ClienteServicio;
import com.tallermecanico.servicio.VehiculoServicio;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class MenuPrincipal {

    private final Scanner lector = new Scanner(System.in);
    private final ClienteServicio clienteServicio = new ClienteServicio();
    private final VehiculoServicio vehiculoServicio = new VehiculoServicio();

    public static void main(String[] args) {
        new MenuPrincipal().iniciar();
    }

    public void iniciar() {
        int opcion;
        do {
            System.out.println("\n===== TALLER MECÁNICO =====");
            System.out.println("1. Gestión de clientes");
            System.out.println("2. Gestión de vehículos");
            System.out.println("0. Salir");
            opcion = leerEntero("Seleccione una opción: ");
            switch (opcion) {
                case 1 -> menuClientes();
                case 2 -> menuVehiculos();
                case 0 -> System.out.println("Hasta luego.");
                default -> System.out.println("Opción inválida.");
            }
        } while (opcion != 0);
    }

    private void menuClientes() {
        int opcion;
        do {
            System.out.println("\n--- CLIENTES ---");
            System.out.println("1. Registrar  2. Listar  3. Consultar por documento");
            System.out.println("4. Actualizar 5. Eliminar 0. Volver");
            opcion = leerEntero("Opción: ");
            try {
                switch (opcion) {
                    case 1 -> registrarCliente();
                    case 2 -> clienteServicio.listarClientes().forEach(System.out::println);
                    case 3 -> consultarCliente();
                    case 4 -> actualizarCliente();
                    case 5 -> eliminarCliente();
                    case 0 -> { }
                    default -> System.out.println("Opción inválida.");
                }
            } catch (IllegalArgumentException | AccesoDatosException e) {
                System.out.println("Aviso: " + e.getMessage());
            }
        } while (opcion != 0);
    }

    private void registrarCliente() {
        Cliente cliente = leerDatosCliente(new Cliente());
        int id = clienteServicio.registrarCliente(cliente);
        System.out.println("Cliente registrado con id " + id);
    }

    private void consultarCliente() {
        String documento = leerTexto("Documento: ");
        Optional<Cliente> cliente = clienteServicio.consultarPorDocumento(documento);
        System.out.println(cliente.map(Cliente::toString).orElse("Cliente no encontrado."));
    }

    private void actualizarCliente() {
        int id = leerEntero("Id del cliente a actualizar: ");
        Optional<Cliente> existente = clienteServicio.consultarPorId(id);
        if (existente.isEmpty()) {
            System.out.println("Cliente no encontrado.");
            return;
        }
        Cliente cliente = leerDatosCliente(existente.get());
        System.out.println(clienteServicio.actualizarCliente(cliente)
                ? "Cliente actualizado." : "No se actualizó ningún registro.");
    }

    private void eliminarCliente() {
        int id = leerEntero("Id del cliente a eliminar: ");
        System.out.println(clienteServicio.eliminarCliente(id)
                ? "Cliente eliminado." : "Cliente no encontrado.");
    }

    private Cliente leerDatosCliente(Cliente cliente) {
        cliente.setDocumento(leerTexto("Documento: "));
        cliente.setNombre(leerTexto("Nombre completo: "));
        cliente.setTelefono(leerTexto("Teléfono: "));
        cliente.setEmail(leerTexto("Correo (opcional): "));
        cliente.setDireccion(leerTexto("Dirección (opcional): "));
        return cliente;
    }

    private void menuVehiculos() {
        int opcion;
        do {
            System.out.println("\n--- VEHÍCULOS ---");
            System.out.println("1. Registrar  2. Listar  3. Consultar por placa");
            System.out.println("4. Listar por cliente 5. Actualizar 6. Eliminar 0. Volver");
            opcion = leerEntero("Opción: ");
            try {
                switch (opcion) {
                    case 1 -> registrarVehiculo();
                    case 2 -> vehiculoServicio.listarVehiculos().forEach(System.out::println);
                    case 3 -> consultarVehiculo();
                    case 4 -> listarVehiculosDeCliente();
                    case 5 -> actualizarVehiculo();
                    case 6 -> eliminarVehiculo();
                    case 0 -> { }
                    default -> System.out.println("Opción inválida.");
                }
            } catch (IllegalArgumentException | AccesoDatosException e) {
                System.out.println("Aviso: " + e.getMessage());
            }
        } while (opcion != 0);
    }

    private void registrarVehiculo() {
        Vehiculo vehiculo = leerDatosVehiculo(new Vehiculo());
        int id = vehiculoServicio.registrarVehiculo(vehiculo);
        System.out.println("Vehículo registrado con id " + id);
    }

    private void consultarVehiculo() {
        String placa = leerTexto("Placa: ");
        Optional<Vehiculo> vehiculo = vehiculoServicio.consultarPorPlaca(placa);
        System.out.println(vehiculo.map(Vehiculo::toString).orElse("Vehículo no encontrado."));
    }

    private void listarVehiculosDeCliente() {
        int idCliente = leerEntero("Id del cliente: ");
        List<Vehiculo> vehiculos = vehiculoServicio.listarVehiculosDeCliente(idCliente);
        if (vehiculos.isEmpty()) {
            System.out.println("El cliente no tiene vehículos.");
        } else {
            vehiculos.forEach(System.out::println);
        }
    }

    private void actualizarVehiculo() {
        int id = leerEntero("Id del vehículo a actualizar: ");
        Optional<Vehiculo> existente = vehiculoServicio.consultarPorId(id);
        if (existente.isEmpty()) {
            System.out.println("Vehículo no encontrado.");
            return;
        }
        Vehiculo vehiculo = leerDatosVehiculo(existente.get());
        System.out.println(vehiculoServicio.actualizarVehiculo(vehiculo)
                ? "Vehículo actualizado." : "No se actualizó ningún registro.");
    }

    private void eliminarVehiculo() {
        int id = leerEntero("Id del vehículo a eliminar: ");
        System.out.println(vehiculoServicio.eliminarVehiculo(id)
                ? "Vehículo eliminado." : "Vehículo no encontrado.");
    }

    private Vehiculo leerDatosVehiculo(Vehiculo vehiculo) {
        vehiculo.setPlaca(leerTexto("Placa: "));
        vehiculo.setMarca(leerTexto("Marca: "));
        vehiculo.setModelo(leerTexto("Modelo: "));
        vehiculo.setAnio(leerEntero("Año: "));
        vehiculo.setColor(leerTexto("Color: "));
        vehiculo.setIdCliente(leerEntero("Id del cliente propietario: "));
        return vehiculo;
    }

    private String leerTexto(String mensaje) {
        System.out.print(mensaje);
        return lector.nextLine().trim();
    }

    private int leerEntero(String mensaje) {
        while (true) {
            try {
                return Integer.parseInt(leerTexto(mensaje));
            } catch (NumberFormatException e) {
                System.out.println("Ingrese un número válido.");
            }
        }
    }
}
