package com.hotel.models;

import com.hotel.interfaces.Servicio;
import com.hotel.state.ReservaState;
import com.hotel.state.ReservaPendiente;
import com.hotel.strategy.PoliticaPrecio;
import com.hotel.strategy.PagoStrategy;
import com.hotel.exceptions.ConfirmacionReservaException;
import com.hotel.exceptions.PagoNoProcesadoException;
import com.hotel.exceptions.EstadoReservaInvalidoException;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Reserva {
    private String id;
    private Cliente cliente;
    private Habitacion habitacion;
    private LocalDate fechaLlegada;
    private LocalDate fechaSalida;
    private List<Servicio> serviciosAdicionales;
    private double costoTotal;
    private ReservaState estadoActual;

    public Reserva(String id, Cliente cliente, Habitacion habitacion, LocalDate fechaLlegada, LocalDate fechaSalida, List<Servicio> serviciosAdicionales) {
        this.id = id;
        this.cliente = cliente;
        this.habitacion = habitacion;
        this.fechaLlegada = fechaLlegada;
        this.fechaSalida = fechaSalida;
        this.serviciosAdicionales = serviciosAdicionales != null ? new ArrayList<>(serviciosAdicionales) : new ArrayList<>();
        this.estadoActual = new ReservaPendiente(); // Estado inicial
        this.costoTotal = 0.0; // Se calculará después
    }

    // Getters
    public String getId() { return id; }
    public Cliente getCliente() { return cliente; }
    public Habitacion getHabitacion() { return habitacion; }
    public LocalDate getFechaLlegada() { return fechaLlegada; }
    public LocalDate getFechaSalida() { return fechaSalida; }
    public List<Servicio> getServiciosAdicionales() { return serviciosAdicionales; }
    public double getCostoTotal() { return costoTotal; }
    public ReservaState getEstadoActual() { return estadoActual; }

    // Setters
    public void setId(String id) { this.id = id; }
    public void setCostoTotal(double costoTotal) { this.costoTotal = costoTotal; }
    public void setEstadoActual(ReservaState estadoActual) { this.estadoActual = estadoActual; }
    public void setCliente(Cliente cliente) { this.cliente = cliente; }
    public void setHabitacion(Habitacion habitacion) { this.habitacion = habitacion; }
    public void setFechaLlegada(LocalDate fechaLlegada) { this.fechaLlegada = fechaLlegada; }
    public void setFechaSalida(LocalDate fechaSalida) { this.fechaSalida = fechaSalida; }
    public void setServiciosAdicionales(List<Servicio> serviciosAdicionales) { this.serviciosAdicionales = serviciosAdicionales; }

    // Métodos delegados al estado
    public void confirmar() throws ConfirmacionReservaException, EstadoReservaInvalidoException {
        estadoActual.confirmar(this);
    }

    public void cancelar() throws EstadoReservaInvalidoException {
        estadoActual.cancelar(this);
    }

    public void pendificar() throws EstadoReservaInvalidoException {
        estadoActual.pendificar(this);
    }

    public String getNombreEstado() {
        return estadoActual.obtenerNombreEstado();
    }

    // Métodos para estrategias
    public double aplicarPoliticaPrecio(PoliticaPrecio politica) {
        long dias = java.time.temporal.ChronoUnit.DAYS.between(fechaLlegada, fechaSalida);
        double precioBaseHabitacionPorDias = habitacion.getPrecioBase() * dias;
        this.costoTotal = politica.calcularPrecio(precioBaseHabitacionPorDias, (int) dias);
        for (Servicio servicio : serviciosAdicionales) {
            this.costoTotal += servicio.getCosto();
        }
        return this.costoTotal;
    }

    public boolean procesarPago(PagoStrategy pagoStrategy) throws PagoNoProcesadoException {
        return pagoStrategy.procesarPago(this.costoTotal);
    }

    public void agregarServicioAdicional(Servicio servicio) {
        this.serviciosAdicionales.add(servicio);
    }

    @Override
    public String toString() {
        return "Reserva{" +
               "id='" + id + '\'' +
               ", cliente=" + (cliente != null ? cliente.getNombre() : "N/A") +
               ", habitacion=" + (habitacion != null ? habitacion.getNumero() : "N/A") +
               ", fechaLlegada=" + fechaLlegada +
               ", fechaSalida=" + fechaSalida +
               ", estado='" + getNombreEstado() + '\'' +
               ", costoTotal=" + String.format("%.2f", costoTotal) +
               ", serviciosAdicionales=" + serviciosAdicionales.size() +
               '}';
    }
}
