package com.hotel.models;

import java.util.ArrayList;
import java.util.List;

public class Cliente {
    private String id;
    private String nombre;
    private String email;
    private String telefono;
    private List<Reserva> historialReservas;

    public Cliente(String id, String nombre, String email, String telefono) {
        this.id = id;
        this.nombre = nombre;
        this.email = email;
        this.telefono = telefono;
        this.historialReservas = new ArrayList<>();
    }

    // Getters
    public String getId() { return id; }
    public String getNombre() { return nombre; }
    public String getEmail() { return email; }
    public String getTelefono() { return telefono; }
    public List<Reserva> getHistorialReservas() { return historialReservas; }

    // Setters
    public void setNombre(String nombre) { this.nombre = nombre; }
    public void setEmail(String email) { this.email = email; }
    public void setTelefono(String telefono) { this.telefono = telefono; }

    public void agregarReserva(Reserva reserva) {
        this.historialReservas.add(reserva);
    }

    @Override
    public String toString() {
        return "Cliente{" +
               "id='" + id + '\'' +
               ", nombre='" + nombre + '\'' +
               ", email='" + email + '\'' +
               ", telefono='" + telefono + '\'' +
               '}';
    }
}
