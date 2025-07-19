package com.hotel.factory;

import com.hotel.models.Habitacion;
import com.hotel.exceptions.CapacidadHabitacionExcedidaException;

public class HabitacionFactory {
    public Habitacion crearHabitacion(String numero, String tipo, int capacidad, double precioBase) throws CapacidadHabitacionExcedidaException {
        if (capacidad <= 0) {
            throw new CapacidadHabitacionExcedidaException("La capacidad de la habitación debe ser mayor a cero.");
        }
        // Lógica de validación o diferenciación de tipos de habitación si fuera más compleja
        return new Habitacion(numero, tipo, capacidad, precioBase);
    }

    public void validarCapacidad(int capacidadSolicitada, int capacidadHabitacion) throws CapacidadHabitacionExcedidaException {
        if (capacidadSolicitada > capacidadHabitacion) {
            throw new CapacidadHabitacionExcedidaException("La capacidad solicitada excede la capacidad de la habitación.");
        }
    }
}
