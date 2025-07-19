# Sistema de Reservas de Hotel - Grupo 4

## Descripción

Este proyecto es un sistema de gestión de reservas para un hotel. Está diseñado para manejar clientes, habitaciones, reservas y servicios adicionales. Utiliza varios patrones de diseño para estructurar el código de manera eficiente y escalable.

## Características

- Gestión de clientes y habitaciones.
- Creación y gestión de reservas.
- Aplicación de servicios adicionales a las reservas.
- Procesamiento de pagos.
- Notificaciones y logging de eventos.
- Aplicación de políticas de precios.

## Tecnologías Utilizadas

- Java
- Maven
- JUnit para pruebas
- SLF4J para logging
- Logback para implementación de logging

## Configuración

El proyecto utiliza Maven para la gestión de dependencias. Asegúrate de tener Maven instalado y configurado en tu entorno de desarrollo.

## Dependencias

Las dependencias principales se definen en el archivo `pom.xml`:

- JUnit para pruebas unitarias.
- SLF4J y Logback para el manejo de logs.

## Instalación

1. Copia la url https de este repositorio.
2. Clona el repositorio en tu máquina local.
3. Espera que se carguen puglins y demas archivos para poder ejecutar.

## Estructura del Proyecto

El proyecto sigue una arquitectura basada en patrones de diseño como:

- **Builder**: Para la construcción de objetos `Reserva`.
- **Command**: Para encapsular solicitudes como objetos.
- **Decorator**: Para añadir responsabilidades a objetos `Servicio`.
- **Factory**: Para la creación de objetos `Habitacion` y `Servicio`.
- **Observer**: Para notificar cambios a los observadores.
- **State**: Para manejar los diferentes estados de una reserva.
- **Strategy**: Para definir una familia de algoritmos de precios y pagos.

```
src/
├── main/
│   ├── java/com/hotel/
│   │   ├── builder/          # Patrón Builder
│   │   ├── command/          # Patrón Command
│   │   ├── core/             # Lógica principal del sistema
│   │   ├── decorator/        # Patrón Decorator
│   │   ├── exceptions/       # Excepciones personalizadas
│   │   ├── factory/          # Patrón Factory
│   │   ├── interfaces/       # Interfaces comunes
│   │   ├── models/           # Modelos de datos
│   │   ├── observer/         # Patrón Observer
│   │   ├── services/         # Implementaciones de servicios
│   │   ├── state/            # Patrón State
│   │   ├── strategy/         # Patrón Strategy
│   │   └── ui/               # Interfaz de usuario
│   └── resources/            # Recursos (logs, configuraciones)
└── test/                     # Pruebas unitarias
```

## Codigo para el Menu en Consola

```
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
```
Este código muestra:

1. Un menú con 9 opciones numeradas
2. Un sistema de lectura de opciones que valida que sea un número
3. Un switch que dirige a cada funcionalidad según la opción seleccionada
4. Manejo básico de errores
5. Un bucle que mantiene el menú activo hasta que se seleccione la opción 0 (Salir)

El método leerOpcion() se encarga de validar que la entrada sea un número entero, evitando errores si el usuario ingresa texto.

## Ejemplo de Datos

El sistema carga automáticamente datos de ejemplo al iniciar:

Habitaciones: 101 (Simple), 102 (Doble), 201 (Suite)

Clientes: Mery Acevedo (CL001), Nestor Colocho (CL002)

---

## Autores
Integrantes del grupo 4: Nestor Ivan Fabian Colocho, Mery Acevedo y Alejandro Ernesto Juarez Argumedo.
