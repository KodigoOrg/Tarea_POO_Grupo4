package com.hotel.interfaces;

public interface Notifier {
    void agregarObservador(Object observer); // Simplified for archetypal purposes
    void removerObservador(Object observer); // Simplified for archetypal purposes
    void notificar(String mensaje);
}
