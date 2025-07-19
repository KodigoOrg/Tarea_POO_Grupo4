package com.hotel.strategy;

public class DescuentoTemporadaAlta implements PoliticaPrecio {
    private static final double FACTOR_TEMPORADA_ALTA = 1.20; // 20% de incremento

    @Override
    public double calcularPrecio(double precioBaseHabitacion, int numeroDias) {
        System.out.println("Aplicando política de precio: Temporada Alta (+" + ((FACTOR_TEMPORADA_ALTA - 1) * 100) + "%)");
        return precioBaseHabitacion * FACTOR_TEMPORADA_ALTA;
    }
}
