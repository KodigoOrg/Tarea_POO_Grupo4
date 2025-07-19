package com.hotel.observer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LoggerObserver {
    private static final Logger logger = LoggerFactory.getLogger(LoggerObserver.class);

    public void update(String mensaje) {
        log(mensaje);
    }

    public void log(String mensaje) {
        logger.info("[LOGGER] Evento del sistema: " + mensaje);
    }
}
