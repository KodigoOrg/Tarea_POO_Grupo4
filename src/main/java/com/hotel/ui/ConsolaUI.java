package com.hotel.ui;

import com.hotel.core.GestorReservas;
import com.hotel.models.Cliente;
import com.hotel.models.Habitacion;
import com.hotel.models.Reserva;
import com.hotel.builder.ReservaBuilder;
import com.hotel.factory.HabitacionFactory;
import com.hotel.factory.ServicioFactory;
import com.hotel.interfaces.Servicio;
import com.hotel.services.ServicioBasico;
import com.hotel.decorator.DesayunoDecorator;
import com.hotel.decorator.SpaDecorator;
import com.hotel.decorator.MascotaDecorator;
import com.hotel.strategy.PoliticaPrecio;
import com.hotel.strategy.DescuentoTemporadaAlta;
import com.hotel.strategy.DescuentoClienteVIP;
import com.hotel.strategy.PagoStrategy;
import com.hotel.strategy.PagoTarjetaStrategy;
import com.hotel.strategy.PagoEfectivoStrategy;
import com.hotel.command.Command;
import com.hotel.command.ConfirmarReservaCommand;
import com.hotel.command.CancelarReservaCommand;
import com.hotel.exceptions.*;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.UUID;

public class ConsolaUI {
    private GestorReservas gestor;
    private Scanner scanner;

    public ConsolaUI(GestorReservas gestor) {
        this.gestor = gestor;
        this.scanner = new Scanner(System.in);
    }

    public void mostrarMenu() {
        int opcion;
        do {
            System.out.println("\n--- Sistema de Reservas de Hotel ---");
            System.out.println("1. Registrar Cliente");
            System.out.println("2. Registrar Habitación");
            System.out.println("3. Crear Reserva");
            System.out.println("4. Confirmar/Cancelar Reserva");
            System.out.println("5. Ver Todas las Reservas");
            System.out.println("6. Aplicar Política de Precio a Reserva");
            System.out.println("7. Aplicar Servicios Adicionales a Reserva");
            System.out.println("8. Procesar Pago de Reserva");
            System.out.println("9. Ver Notificaciones de Auditoría");
            System.out.println("0. Salir");
            System.out.print("Seleccione una opción: ");
            opcion = leerOpcion();

            try {
                switch (opcion) {
                    case 1: registrarCliente(); break;
                    case 2: registrarHabitacion(); break;
                    case 3: crearReserva(); break;
                    case 4: confirmarCancelarReserva(); break;
                    case 5: verTodasLasReservas(); break;
                    case 6: aplicarPoliticaPrecioAReserva(); break;
                    case 7: aplicarServiciosAdicionalesAReserva(); break;
                    case 8: procesarPagoReserva(); break;
                    case 9: verNotificacionesAuditoria(); break;
                    case 0: System.out.println("Saliendo del sistema..."); break;
                    default: System.out.println("Opción no válida. Intente de nuevo.");
                }
            } catch (Exception e) {
                System.err.println("Error: " + e.getMessage());
            }
        } while (opcion != 0);
    }

    private int leerOpcion() {
        while (!scanner.hasNextInt()) {
            System.out.println("Entrada inválida. Por favor, ingrese un número.");
            scanner.next(); // Consume la entrada inválida
            System.out.print("Seleccione una opción: ");
        }
        int opcion = scanner.nextInt();
        scanner.nextLine(); // Consume la nueva línea
        return opcion;
    }

    private String leerCadena(String mensaje) {
        System.out.print(mensaje);
        return scanner.nextLine();
    }

    private double leerDouble(String mensaje) {
        while (true) {
            System.out.print(mensaje);
            if (scanner.hasNextDouble()) {
                double valor = scanner.nextDouble();
                scanner.nextLine();
                return valor;
            } else {
                System.out.println("Entrada inválida. Por favor, ingrese un número decimal.");
                scanner.next();
            }
        }
    }

    private int leerInt(String mensaje) {
        while (true) {
            System.out.print(mensaje);
            if (scanner.hasNextInt()) {
                int valor = scanner.nextInt();
                scanner.nextLine();
                return valor;
            } else {
                System.out.println("Entrada inválida. Por favor, ingrese un número entero.");
                scanner.next();
            }
        }
    }

    private LocalDate leerFecha(String mensaje) {
        while (true) {
            System.out.print(mensaje + " (YYYY-MM-DD): ");
            String fechaStr = scanner.nextLine();
            try {
                return LocalDate.parse(fechaStr);
            } catch (DateTimeParseException e) {
                System.out.println("Formato de fecha inválido. Use YYYY-MM-DD.");
            }
        }
    }

    private void registrarCliente() {
        System.out.println("\n--- Registrar Cliente ---");
        String id = leerCadena("ID del Cliente: ");
        String nombre = leerCadena("Nombre del Cliente: ");
        String email = leerCadena("Email del Cliente: ");
        String telefono = leerCadena("Teléfono del Cliente: ");

        try {
            Cliente nuevoCliente = new Cliente(id, nombre, email, telefono);
            gestor.registrarCliente(nuevoCliente);
            System.out.println("Cliente " + nombre + " registrado con éxito.");
        } catch (ClienteDuplicadoException e) {
            System.err.println("Error al registrar cliente: " + e.getMessage());
        }
    }

    private void registrarHabitacion() {
        System.out.println("\n--- Registrar Habitación ---");
        String numero = leerCadena("Número de Habitación: ");
        String tipo = leerCadena("Tipo de Habitación (simple, doble, suite): ");
        int capacidad = leerInt("Capacidad de la Habitación: ");
        double precioBase = leerDouble("Precio Base por Noche: ");

        HabitacionFactory factory = new HabitacionFactory();
        try {
            Habitacion nuevaHabitacion = factory.crearHabitacion(numero, tipo, capacidad, precioBase);
            gestor.registrarHabitacion(nuevaHabitacion);
            System.out.println("Habitación " + numero + " (" + tipo + ") registrada con éxito.");
        } catch (CapacidadHabitacionExcedidaException e) {
            System.err.println("Error al registrar habitación: " + e.getMessage());
        }
    }

    private void crearReserva() {
        System.out.println("\n--- Crear Nueva Reserva ---");
        String idReserva = UUID.randomUUID().toString().substring(0, 8); // Generar un ID simple
        System.out.println("ID de la reserva generada: " + idReserva);

        String idCliente = leerCadena("ID del Cliente: ");
        Cliente cliente = gestor.obtenerCliente(idCliente)
                                .orElse(null);
        if (cliente == null) {
            System.out.println("Cliente no encontrado. Por favor, registre el cliente primero.");
            return;
        }

        String numeroHabitacion = leerCadena("Número de Habitación: ");
        Habitacion habitacion = gestor.obtenerHabitacion(numeroHabitacion)
                                        .orElse(null);
        if (habitacion == null) {
            System.out.println("Habitación no encontrada. Por favor, registre la habitación primero.");
            return;
        }

        LocalDate fechaLlegada = leerFecha("Fecha de Llegada");
        LocalDate fechaSalida = leerFecha("Fecha de Salida");

        ReservaBuilder builder = new ReservaBuilder()
                                    .conId(idReserva)
                                    .conCliente(cliente)
                                    .conHabitacion(habitacion)
                                    .conFechas(fechaLlegada, fechaSalida);

        agregarServiciosABuilder(builder);

        try {
            Reserva nuevaReserva = gestor.crearReserva(idReserva, cliente, habitacion, fechaLlegada, fechaSalida, builder.build().getServiciosAdicionales());
            System.out.println("Reserva " + nuevaReserva.getId() + " creada con éxito para " + cliente.getNombre() + " en la habitación " + habitacion.getNumero() + ".");
            // Recalcular el costo total inicial con una política base (ej. temporada baja por defecto)
            nuevaReserva.aplicarPoliticaPrecio(new DescuentoTemporadaAlta()); // Se puede cambiar por una política por defecto
            System.out.println("Costo inicial de la reserva: " + String.format("%.2f", nuevaReserva.getCostoTotal()));
        } catch (ReservaInvalidaException | HabitacionNoDisponibleException e) {
            System.err.println("Error al crear reserva: " + e.getMessage());
        }
    }

    private void agregarServiciosABuilder(ReservaBuilder builder) {
        ServicioFactory servicioFactory = new ServicioFactory();
        Servicio servicioActual = new ServicioBasico("Estancia Base", 0.0); // Punto de partida para los decoradores

        System.out.println("¿Desea agregar servicios adicionales? (sí/no)");
        String respuesta = scanner.nextLine().trim().toLowerCase();

        while (respuesta.equals("sí") || respuesta.equals("si")) {
            System.out.println("Servicios disponibles: desayuno, spa, mascota");
            String tipoServicio = leerCadena("Ingrese tipo de servicio a agregar (o 'listo' para terminar): ");
            if (tipoServicio.equalsIgnoreCase("listo")) {
                break;
            }
            try {
                servicioActual = servicioFactory.crearServicio(tipoServicio, servicioActual);
                System.out.println("Servicio '" + tipoServicio + "' agregado.");
                builder.conServicio(servicioActual); // El builder solo necesita una referencia al servicio final
                                                     // aunque el decorator envuelve al anterior
            } catch (ServicioNoDisponibleException e) {
                System.err.println("Error: " + e.getMessage());
            }
            System.out.println("¿Desea agregar otro servicio? (sí/no)");
            respuesta = scanner.nextLine().trim().toLowerCase();
        }
    }


    private void confirmarCancelarReserva() {
        System.out.println("\n--- Confirmar/Cancelar Reserva ---");
        String idReserva = leerCadena("ID de la Reserva: ");

        System.out.println("¿Qué acción desea realizar? (confirmar/cancelar)");
        String accion = leerCadena("Acción: ").toLowerCase();

        try {
            Command command;
            if (accion.equals("confirmar")) {
                System.out.println("Seleccione estrategia de pago:");
                System.out.println("1. Tarjeta");
                System.out.println("2. Efectivo");
                int opcionPago = leerInt("Opción de pago: ");
                PagoStrategy pagoStrategy;
                if (opcionPago == 1) {
                    pagoStrategy = new PagoTarjetaStrategy();
                } else if (opcionPago == 2) {
                    pagoStrategy = new PagoEfectivoStrategy();
                } else {
                    System.out.println("Opción de pago inválida. Usando Tarjeta por defecto.");
                    pagoStrategy = new PagoTarjetaStrategy();
                }
                command = new ConfirmarReservaCommand(gestor, idReserva, pagoStrategy);
            } else if (accion.equals("cancelar")) {
                command = new CancelarReservaCommand(gestor, idReserva);
            } else {
                System.out.println("Acción no válida.");
                return;
            }
            command.execute();
        } catch (Exception e) { // Captura excepciones de los comandos
            System.err.println("Error al ejecutar la acción: " + e.getMessage());
        }
    }

    private void verTodasLasReservas() {
        System.out.println("\n--- Todas las Reservas ---");
        List<Reserva> reservas = gestor.obtenerTodasLasReservas();
        if (reservas.isEmpty()) {
            System.out.println("No hay reservas registradas.");
            return;
        }
        reservas.forEach(System.out::println);
    }

    private void aplicarPoliticaPrecioAReserva() {
        System.out.println("\n--- Aplicar Política de Precio ---");
        String idReserva = leerCadena("ID de la Reserva: ");
        Reserva reserva = gestor.obtenerReserva(idReserva).orElse(null);
        if (reserva == null) {
            System.out.println("Reserva no encontrada.");
            return;
        }

        System.out.println("Seleccione Política de Precio:");
        System.out.println("1. Temporada Alta (incremento)");
        System.out.println("2. Cliente VIP (descuento)");
        int opcion = leerInt("Opción: ");

        PoliticaPrecio politica;
        if (opcion == 1) {
            politica = new DescuentoTemporadaAlta();
        } else if (opcion == 2) {
            politica = new DescuentoClienteVIP();
        } else {
            System.out.println("Opción no válida. No se aplicará política.");
            return;
        }

        double nuevoCosto = gestor.aplicarPoliticaPrecio(reserva, politica);
        System.out.println("El nuevo costo total de la reserva " + idReserva + " es: " + String.format("%.2f", nuevoCosto));
    }

    private void aplicarServiciosAdicionalesAReserva() {
        System.out.println("\n--- Aplicar Servicios Adicionales a Reserva ---");
        String idReserva = leerCadena("ID de la Reserva: ");
        Reserva reserva = gestor.obtenerReserva(idReserva).orElse(null);
        if (reserva == null) {
            System.out.println("Reserva no encontrada.");
            return;
        }

        ServicioFactory servicioFactory = new ServicioFactory();
        Servicio servicioBase = new ServicioBasico("Estancia Base", 0.0); // Usar un servicio base para la cadena de decoradores

        // Si la reserva ya tiene servicios, los usamos como base
        if (!reserva.getServiciosAdicionales().isEmpty()) {
            // Encuentra el último decorador en la cadena o el servicio base si solo hay uno
            servicioBase = reserva.getServiciosAdicionales().get(reserva.getServiciosAdicionales().size() - 1);
        }

        System.out.println("Servicios disponibles: desayuno, spa, mascota");
        String tipoServicio = leerCadena("Ingrese tipo de servicio a agregar (o 'listo' para terminar): ");

        while (!tipoServicio.equalsIgnoreCase("listo")) {
            try {
                // Crear el nuevo decorador envolviendo la cadena de servicios existente
                Servicio nuevoServicioDecorado = servicioFactory.crearServicio(tipoServicio, servicioBase);
                reserva.agregarServicioAdicional(nuevoServicioDecorado); // Añadir a la lista de servicios de la reserva
                servicioBase = nuevoServicioDecorado; // Actualizar el servicio base para el siguiente decorador
                System.out.println("Servicio '" + tipoServicio + "' agregado a la reserva " + idReserva + ".");
                System.out.println("Costo actual de la reserva: " + String.format("%.2f", reserva.aplicarPoliticaPrecio(new DescuentoTemporadaAlta()))); // Recalcular con una política por defecto
            } catch (ServicioNoDisponibleException e) {
                System.err.println("Error: " + e.getMessage());
            }
            tipoServicio = leerCadena("Ingrese tipo de servicio a agregar (o 'listo' para terminar): ");
        }
    }


    private void procesarPagoReserva() {
        System.out.println("\n--- Procesar Pago de Reserva ---");
        String idReserva = leerCadena("ID de la Reserva: ");
        Reserva reserva = gestor.obtenerReserva(idReserva).orElse(null);
        if (reserva == null) {
            System.out.println("Reserva no encontrada.");
            return;
        }

        if (reserva.getCostoTotal() <= 0) {
            System.out.println("El costo total de la reserva es 0. No hay nada que pagar.");
            return;
        }

        System.out.println("Costo total de la reserva " + idReserva + ": " + String.format("%.2f", reserva.getCostoTotal()));
        System.out.println("Seleccione método de pago:");
        System.out.println("1. Tarjeta");
        System.out.println("2. Efectivo");
        int opcionPago = leerInt("Opción de pago: ");

        PagoStrategy pagoStrategy;
        if (opcionPago == 1) {
            pagoStrategy = new PagoTarjetaStrategy();
        } else if (opcionPago == 2) {
            pagoStrategy = new PagoEfectivoStrategy();
        } else {
            System.out.println("Opción de pago inválida.");
            return;
        }

        try {
            if (reserva.procesarPago(pagoStrategy)) {
                System.out.println("Pago para la reserva " + idReserva + " procesado exitosamente.");
            }
        } catch (PagoNoProcesadoException e) {
            System.err.println("Error al procesar pago: " + e.getMessage());
        }
    }

    private void verNotificacionesAuditoria() {
        System.out.println("\n--- Notificaciones de Auditoría (Logger) ---");
        // Las notificaciones se imprimen directamente en la consola por el LoggerObserver.
        // En un sistema real, esto leería de un archivo de log o base de datos.
        System.out.println("Consulta el output de la consola para ver los logs del sistema.");
    }
}
