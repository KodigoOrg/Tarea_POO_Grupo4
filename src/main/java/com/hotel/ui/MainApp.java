package com.hotel.ui;

import com.hotel.core.GestorReservas;
import com.hotel.models.Cliente;
import com.hotel.models.Habitacion;
import com.hotel.exceptions.ClienteDuplicadoException;
import com.hotel.ui.ConsolaUI;

public class MainApp {

    public static void main(String[] args) {
        // Encabezado decorativo
        System.out.println("::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::");
        System.out.println(":: Bienvenido al Sistema de Reservas de Hotel ::");
        System.out.println("::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::");

        // Inicialización del gestor y la interfaz de usuario
        GestorReservas gestor = GestorReservas.getInstance();
        ConsolaUI ui = new ConsolaUI(gestor);

        // Carga de datos de ejemplo
        cargarDatosEjemplo(gestor);

        // Mostrar el menú principal
        ui.mostrarMenu();
    }

    private static void cargarDatosEjemplo(GestorReservas gestor) {
        try {
            // Registrar habitaciones de ejemplo
            gestor.registrarHabitacion(new Habitacion("101", "Simple", 2, 100.0));
            gestor.registrarHabitacion(new Habitacion("102", "Doble", 4, 150.0));
            gestor.registrarHabitacion(new Habitacion("201", "Suite", 6, 300.0));

            // Registrar clientes de ejemplo
            gestor.registrarCliente(new Cliente("CL001", "Mery Acevedo", "mery@gmail.com", "555-1234"));
            gestor.registrarCliente(new Cliente("CL002", "Nestor Colocho", "nestor@gmail.com", "555-5678"));

            // Mensaje de confirmación
            System.out.println("::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::");
            System.out.println("Datos de ejemplo han sido cargados con éxito:");
            System.out.println("::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::");
            System.out.println("Habitaciones disponibles: 101 (Simple), 102 (Doble), 201 (Suite)");
            System.out.println("Clientes registrados: Mery Acevedo (CL001), Nestor Colocho (CL002)");
            System.out.println("::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::");

        } catch (ClienteDuplicadoException e) {
            System.err.println("::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::");
            System.err.println("Error al cargar datos de ejemplo: " + e.getMessage());
            System.err.println("::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::");
        }
    }
}
