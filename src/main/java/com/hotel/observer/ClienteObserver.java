package com.hotel.observer;

import com.hotel.models.Cliente;

public class ClienteObserver {
    private Cliente cliente;

    public ClienteObserver(Cliente cliente) {
        this.cliente = cliente;
    }

    public void update(String mensaje) {
        // En una aplicación real, esto enviaría un email o SMS al cliente
        System.out.println("Notificación para " + cliente.getNombre() + " (" + cliente.getEmail() + "): " + mensaje);
        recibirNotificacion(mensaje); // Llama al método específico del cliente
    }

    public void recibirNotificacion(String mensaje) {
        System.out.println("[Cliente " + cliente.getNombre() + "] Ha recibido la notificación: " + mensaje);
        // Aquí podrías agregar la notificación al historial del cliente si lo tuvieran
    }
}
