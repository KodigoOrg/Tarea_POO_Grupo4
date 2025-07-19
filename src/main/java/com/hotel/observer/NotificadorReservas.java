package com.hotel.observer;

import com.hotel.interfaces.Notifier;

import java.util.ArrayList;
import java.util.List;

public class NotificadorReservas implements Notifier {
    private List<ClienteObserver> clienteObservers = new ArrayList<>();
    private LoggerObserver loggerObserver; // Una sola instancia para el logger

    public NotificadorReservas() {
        this.loggerObserver = new LoggerObserver(); // Inicializa el logger
    }

    @Override
    public void agregarObservador(Object observer) {
        if (observer instanceof ClienteObserver) {
            clienteObservers.add((ClienteObserver) observer);
        } else if (observer instanceof LoggerObserver) {
            this.loggerObserver = (LoggerObserver) observer; // Sobrescribe o asigna
        }
    }

    @Override
    public void removerObservador(Object observer) {
        if (observer instanceof ClienteObserver) {
            clienteObservers.remove((ClienteObserver) observer);
        }
        // No removemos el LoggerObserver por simplicidad, siempre debe estar activo
    }

    @Override
    public void notificar(String mensaje) {
        // Notificar a los observadores de clientes
        for (ClienteObserver observer : clienteObservers) {
            observer.update(mensaje);
        }
        // Notificar al logger
        if (loggerObserver != null) {
            loggerObserver.update(mensaje);
        }
    }
}
