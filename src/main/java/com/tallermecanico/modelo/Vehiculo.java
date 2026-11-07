package com.tallermecanico.modelo;

public class Vehiculo {

    private int idVehiculo;
    private String placa;
    private String marca;
    private String modelo;
    private int anio;
    private String color;
    private int idCliente;

    public Vehiculo() {
    }

    public Vehiculo(String placa, String marca, String modelo, int anio, String color, int idCliente) {
        this.placa = placa;
        this.marca = marca;
        this.modelo = modelo;
        this.anio = anio;
        this.color = color;
        this.idCliente = idCliente;
    }

    public int getIdVehiculo() { return idVehiculo; }
    public void setIdVehiculo(int idVehiculo) { this.idVehiculo = idVehiculo; }
    public String getPlaca() { return placa; }
    public void setPlaca(String placa) { this.placa = placa; }
    public String getMarca() { return marca; }
    public void setMarca(String marca) { this.marca = marca; }
    public String getModelo() { return modelo; }
    public void setModelo(String modelo) { this.modelo = modelo; }
    public int getAnio() { return anio; }
    public void setAnio(int anio) { this.anio = anio; }
    public String getColor() { return color; }
    public void setColor(String color) { this.color = color; }
    public int getIdCliente() { return idCliente; }
    public void setIdCliente(int idCliente) { this.idCliente = idCliente; }

    @Override
    public String toString() {
        return String.format("[%d] %s | %s %s (%d) | %s | Cliente id: %d",
                idVehiculo, placa, marca, modelo, anio, color, idCliente);
    }
}
