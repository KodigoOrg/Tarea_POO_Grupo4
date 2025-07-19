package com.hotel.decorator;

import com.hotel.interfaces.Servicio;

public abstract class ServicioAdicional implements Servicio {
    protected Servicio wrappee; // Objeto que será decorado

    public ServicioAdicional(Servicio wrappee) {
        this.wrappee = wrappee;
    }

    @Override
    public String getDescripcion() {
        return wrappee.getDescripcion();
    }

    @Override
    public double getCosto() {
        return wrappee.getCosto();
    }
}
