package com.hotel.core;

import com.hotel.models.Cliente;
import com.hotel.models.Habitacion;
import com.hotel.models.Reserva;
import com.hotel.observer.NotificadorReservas;
import com.hotel.observer.ClienteObserver;
import com.hotel.observer.LoggerObserver;
import com.hotel.exceptions.*;
import com.hotel.strategy.PoliticaPrecio;
import com.hotel.interfaces.Servicio;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.time.LocalDate;
import java.util.stream.Collectors;

public class GestorReservas {
    private static GestorReservas instancia;
    private List<Reserva> reservas;
    private List<Cliente> clientes;
    private List<Habitacion> habitaciones;
    private NotificadorReservas notificador;

    private GestorReservas() {
        reservas = new ArrayList<>();
        clientes = new ArrayList<>();
        habitaciones = new ArrayList<>();
        notificador = new NotificadorReservas();
        notificador.agregarObservador(new LoggerObserver()); // Siempre logear eventos
    }

    public static GestorReservas getInstance() {
        if (instancia == null) {
            instancia = new GestorReservas();
        }
        return instancia;
    }

    public NotificadorReservas getNotificador() {
        return notificador;
    }

    public void registrarCliente(Cliente cliente) throws ClienteDuplicadoException {
        if (clientes.stream().anyMatch(c -> c.getId().equals(cliente.getId()))) {
            throw new ClienteDuplicadoException("El cliente con ID " + cliente.getId() + " ya está registrado.");
        }
        clientes.add(cliente);
        notificador.agregarObservador(new ClienteObserver(cliente)); // Cliente se suscribe a notificaciones
        notificador.notificar("Nuevo cliente registrado: " + cliente.getNombre() + " (ID: " + cliente.getId() + ")");
    }

    public Optional<Cliente> obtenerCliente(String idCliente) {
        return clientes.stream().filter(c -> c.getId().equals(idCliente)).findFirst();
    }

    public List<Cliente> obtenerTodosLosClientes() {
        return new ArrayList<>(clientes);
    }

    public void registrarHabitacion(Habitacion habitacion) {
        habitaciones.add(habitacion);
        notificador.notificar("Nueva habitación registrada: " + habitacion.getNumero() + " (" + habitacion.getTipo() + ")");
    }

    public Optional<Habitacion> obtenerHabitacion(String numeroHabitacion) {
        return habitaciones.stream().filter(h -> h.getNumero().equals(numeroHabitacion)).findFirst();
    }

    public List<Habitacion> obtenerTodasLasHabitaciones() {
        return new ArrayList<>(habitaciones);
    }

    public Reserva crearReserva(String idReserva, Cliente cliente, Habitacion habitacion, LocalDate fechaLlegada, LocalDate fechaSalida, List<Servicio> serviciosAdicionales) throws ReservaInvalidaException, HabitacionNoDisponibleException {
        if (idReserva == null || idReserva.trim().isEmpty()) {
            throw new ReservaInvalidaException("El ID de la reserva no puede ser nulo o vacío.");
        }
        if (obtenerReserva(idReserva).isPresent()) {
            throw new ReservaInvalidaException("Ya existe una reserva con el ID: " + idReserva);
        }
        if (cliente == null || obtenerCliente(cliente.getId()).isEmpty()) {
            throw new ReservaInvalidaException("Cliente no válido o no registrado.");
        }
        if (habitacion == null || obtenerHabitacion(habitacion.getNumero()).isEmpty()) {
            throw new ReservaInvalidaException("Habitación no válida o no registrada.");
        }
        if (!habitacion.isDisponible()) {
            throw new HabitacionNoDisponibleException("La habitación " + habitacion.getNumero() + " no está disponible.");
        }
        if (fechaLlegada.isAfter(fechaSalida) || fechaLlegada.isBefore(LocalDate.now())) {
            throw new ReservaInvalidaException("Fechas de reserva inválidas.");
        }

        // Comprobar solapamiento de fechas para la misma habitación
        boolean isOverlapping = reservas.stream()
            .filter(r -> r.getHabitacion().equals(habitacion) && !r.getNombreEstado().equals("Cancelada"))
            .anyMatch(r ->
                (fechaLlegada.isBefore(r.getFechaSalida()) && fechaSalida.isAfter(r.getFechaLlegada()))
            );

        if (isOverlapping) {
            throw new HabitacionNoDisponibleException("La habitación " + habitacion.getNumero() + " ya está reservada para las fechas seleccionadas.");
        }

        Reserva nuevaReserva = new Reserva(idReserva, cliente, habitacion, fechaLlegada, fechaSalida, serviciosAdicionales);
        reservas.add(nuevaReserva);
        cliente.agregarReserva(nuevaReserva);
        habitacion.setDisponible(false); // Marcar habitación como no disponible
        notificador.notificar("Reserva creada (ID: " + nuevaReserva.getId() + ") para " + cliente.getNombre() + " en " + habitacion.getNumero());
        return nuevaReserva;
    }

    public void confirmarReserva(String idReserva) throws ConfirmacionReservaException, EstadoReservaInvalidoException {
        Reserva reserva = obtenerReserva(idReserva)
                .orElseThrow(() -> new ConfirmacionReservaException("Reserva no encontrada con ID: " + idReserva));
        reserva.confirmar();
        notificador.notificar("Reserva confirmada: " + idReserva);
    }

    public void cancelarReserva(String idReserva) throws EstadoReservaInvalidoException {
        Reserva reserva = obtenerReserva(idReserva)
                .orElseThrow(() -> new EstadoReservaInvalidoException("Reserva no encontrada con ID: " + idReserva));
        reserva.cancelar();
        reserva.getHabitacion().setDisponible(true); // Liberar habitación
        notificador.notificar("Reserva cancelada: " + idReserva);
    }

    public Optional<Reserva> obtenerReserva(String idReserva) {
        return reservas.stream().filter(r -> r.getId().equals(idReserva)).findFirst();
    }

    public List<Reserva> obtenerTodasLasReservas() {
        return new ArrayList<>(reservas);
    }

    public double aplicarPoliticaPrecio(Reserva reserva, PoliticaPrecio politica) {
        double costo = reserva.aplicarPoliticaPrecio(politica);
        notificador.notificar("Política de precio aplicada a Reserva " + reserva.getId() + ". Nuevo costo: " + String.format("%.2f", costo));
        return costo;
    }
}
