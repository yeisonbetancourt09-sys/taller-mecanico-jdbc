package com.tallermecanico.modelo;

public class Cliente {

    private int idCliente;
    private String documento;
    private String nombre;
    private String telefono;
    private String email;
    private String direccion;

    public Cliente() {
    }

    public Cliente(String documento, String nombre, String telefono, String email, String direccion) {
        this.documento = documento;
        this.nombre = nombre;
        this.telefono = telefono;
        this.email = email;
        this.direccion = direccion;
    }

    public int getIdCliente() { return idCliente; }
    public void setIdCliente(int idCliente) { this.idCliente = idCliente; }
    public String getDocumento() { return documento; }
    public void setDocumento(String documento) { this.documento = documento; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getDireccion() { return direccion; }
    public void setDireccion(String direccion) { this.direccion = direccion; }

    @Override
    public String toString() {
        return String.format("[%d] %s | %s | Tel: %s | %s | %s",
                idCliente, documento, nombre, telefono, email, direccion);
    }
}
