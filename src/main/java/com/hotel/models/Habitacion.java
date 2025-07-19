package com.hotel.models;

public class Habitacion {
    private String numero;
    private String tipo;
    private int capacidad;
    private double precioBase;
    private boolean disponible;

    public Habitacion(String numero, String tipo, int capacidad, double precioBase) {
        this.numero = numero;
        this.tipo = tipo;
        this.capacidad = capacidad;
        this.precioBase = precioBase;
        this.disponible = true;
    }

    // Getters
    public String getNumero() { return numero; }
    public String getTipo() { return tipo; }
    public int getCapacidad() { return capacidad; }
    public double getPrecioBase() { return precioBase; }
    public boolean isDisponible() { return disponible; }

    // Setters
    public void setTipo(String tipo) { this.tipo = tipo; }
    public void setCapacidad(int capacidad) { this.capacidad = capacidad; }
    public void setPrecioBase(double precioBase) { this.precioBase = precioBase; }
    public void setDisponible(boolean disponible) { this.disponible = disponible; }

    @Override
    public String toString() {
        return "Habitacion{" +
               "numero='" + numero + '\'' +
               ", tipo='" + tipo + '\'' +
               ", capacidad=" + capacidad +
               ", precioBase=" + precioBase +
               ", disponible=" + disponible +
               '}';
    }
}
