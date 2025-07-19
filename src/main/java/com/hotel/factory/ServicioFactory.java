package com.hotel.factory;

import com.hotel.interfaces.Servicio;
import com.hotel.services.ServicioBasico;
import com.hotel.decorator.DesayunoDecorator;
import com.hotel.decorator.SpaDecorator;
import com.hotel.decorator.MascotaDecorator;
import com.hotel.exceptions.ServicioNoDisponibleException;

public class ServicioFactory {
    public Servicio crearServicio(String tipoServicio, Servicio baseService) throws ServicioNoDisponibleException {
        switch (tipoServicio.toLowerCase()) {
            case "desayuno":
                return new DesayunoDecorator(baseService);
            case "spa":
                return new SpaDecorator(baseService);
            case "mascota":
                return new MascotaDecorator(baseService);
            case "basico":
                return new ServicioBasico("Estancia Base", 0.0);
            default:
                throw new ServicioNoDisponibleException("Servicio '" + tipoServicio + "' no disponible.");
        }
    }

    // Este método podría validar si un servicio es aplicable a una reserva o habitación específica
    public void validarRestricciones(String tipoServicio) throws ServicioNoDisponibleException {
        // Implementa lógica para, por ejemplo, verificar si un servicio requiere una habitación específica
        // o si hay cupo limitado. Por ahora, solo es un placeholder.
        System.out.println("Validando restricciones para el servicio: " + tipoServicio);
    }
}
