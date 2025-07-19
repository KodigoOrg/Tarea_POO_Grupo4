# Desarrollo de Aplicación Usando POO y Patrones de Diseño

## 💻 Sistema de Reservas de Hotel - Grupo 4

### ℹ️ Descripción

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

## Creacion de una Reserva

En el proyecto la creación de una reserva se implementa principalmente en 3 partes clave:

### ConsolaUI.java - Donde se recogen los datos
```
private void crearReserva() {
    System.out.println("\n--- Crear Nueva Reserva ---");
    String idReserva = UUID.randomUUID().toString().substring(0, 8);
    System.out.println("ID de la reserva generada: " + idReserva);

    // 1. Selección de cliente
    String idCliente = leerCadena("ID del Cliente: ");
    Cliente cliente = gestor.obtenerCliente(idCliente).orElse(null);
    if (cliente == null) {
        System.out.println("Cliente no encontrado.");
        return;
    }

    // 2. Selección de habitación
    String numeroHabitacion = leerCadena("Número de Habitación: ");
    Habitacion habitacion = gestor.obtenerHabitacion(numeroHabitacion).orElse(null);
    if (habitacion == null) {
        System.out.println("Habitación no encontrada.");
        return;
    }

    // 3. Fechas
    LocalDate fechaLlegada = leerFecha("Fecha de Llegada");
    LocalDate fechaSalida = leerFecha("Fecha de Salida");

    // 4. Servicios adicionales (usando Builder)
    ReservaBuilder builder = new ReservaBuilder()
                            .conId(idReserva)
                            .conCliente(cliente)
                            .conHabitacion(habitacion)
                            .conFechas(fechaLlegada, fechaSalida);
    
    agregarServiciosABuilder(builder); // Método para añadir decoradores

    try {
        // 5. Creación final con el Gestor
        Reserva nuevaReserva = builder.build();
        gestor.crearReserva(nuevaReserva.getId(), cliente, habitacion, 
                           fechaLlegada, fechaSalida, 
                           nuevaReserva.getServiciosAdicionales());
        
        System.out.println("Reserva creada exitosamente!");
    } catch (ReservaInvalidaException | HabitacionNoDisponibleException e) {
        System.err.println("Error al crear reserva: " + e.getMessage());
    }
}
```

### ReservaBuilder.java - Construccion paso a paso

```
public class ReservaBuilder {
    private String id;
    private Cliente cliente;
    private Habitacion habitacion;
    private LocalDate fechaLlegada;
    private LocalDate fechaSalida;
    private List<Servicio> serviciosAdicionales = new ArrayList<>();

    public ReservaBuilder conId(String id) {
        this.id = id;
        return this;
    }

    public ReservaBuilder conCliente(Cliente cliente) {
        this.cliente = cliente;
        return this;
    }

    public ReservaBuilder conHabitacion(Habitacion habitacion) {
        this.habitacion = habitacion;
        return this;
    }

    public ReservaBuilder conFechas(LocalDate fechaLlegada, LocalDate fechaSalida) {
        this.fechaLlegada = fechaLlegada;
        this.fechaSalida = fechaSalida;
        return this;
    }

    public ReservaBuilder conServicio(Servicio servicio) {
        this.serviciosAdicionales.add(servicio);
        return this;
    }

    public Reserva build() throws ReservaInvalidaException {
        if (id == null || id.trim().isEmpty()) {
            throw new ReservaInvalidaException("El ID de la reserva es obligatorio.");
        }
        if (cliente == null) {
            throw new ReservaInvalidaException("El cliente de la reserva es obligatorio.");
        }
        if (habitacion == null) {
            throw new ReservaInvalidaException("La habitación de la reserva es obligatoria.");
        }
        if (fechaLlegada == null || fechaSalida == null || fechaLlegada.isAfter(fechaSalida)) {
            throw new ReservaInvalidaException("Las fechas de llegada y salida son inválidas.");
        }

        return new Reserva(id, cliente, habitacion, fechaLlegada, fechaSalida, serviciosAdicionales);
    }
}
```

### GestorReservas.java - Validacion y Almacenamiento

```
public Reserva crearReserva(String idReserva, Cliente cliente, Habitacion habitacion,
                          LocalDate fechaLlegada, LocalDate fechaSalida,
                          List<Servicio> serviciosAdicionales) 
        throws ReservaInvalidaException, HabitacionNoDisponibleException {

    // Validaciones
    if (idReserva == null || idReserva.trim().isEmpty()) {
        throw new ReservaInvalidaException("ID de reserva inválido");
    }

    // Verifica disponibilidad de habitación
    boolean isOverlapping = reservas.stream()
        .filter(r -> r.getHabitacion().equals(habitacion))
        .anyMatch(r -> fechaLlegada.isBefore(r.getFechaSalida()) 
                    && fechaSalida.isAfter(r.getFechaLlegada()));

    if (isOverlapping) {
        throw new HabitacionNoDisponibleException("Habitación ocupada en esas fechas");
    }

    // Crea y almacena la reserva
    Reserva nuevaReserva = new Reserva(idReserva, cliente, habitacion, 
                                     fechaLlegada, fechaSalida, serviciosAdicionales);
    reservas.add(nuevaReserva);
    habitacion.setDisponible(false);
    
    notificador.notificar("Nueva reserva creada: " + idReserva);
    return nuevaReserva;
}
```

## Utilizacion de Patrones de Diseño
### Singleton (GestorReservas)

Proposito: Garantizar que solo exista una instancia del gestor central de reservas.

Implementacion:
```
// En GestorReservas.java
public class GestorReservas {
    private static GestorReservas instancia;  // Instancia única
    
    private GestorReservas() {  // Constructor privado
        reservas = new ArrayList<>();
        clientes = new ArrayList<>();
        habitaciones = new ArrayList<>();
    }

    public static GestorReservas getInstance() {  // Método estático de acceso
        if (instancia == null) {
            instancia = new GestorReservas();
        }
        return instancia;
    }
}
```
Uso:
```
// En MainApp.java
GestorReservas gestor = GestorReservas.getInstance(); // Siempre devuelve la misma instancia
```
Razón: Centralizar el acceso a los datos (reservas, clientes, habitaciones) y evitar inconsistencia

---

### Factory (HabitacionFactory y ServicioFactory)
Proposito: Centralizar la creación de objetos complejos.

Ejemplo 1 - Creación de habitaciones:
```
// En HabitacionFactory.java
public Habitacion crearHabitacion(String numero, String tipo, int capacidad, double precioBase) 
    throws CapacidadHabitacionExcedidaException {
    
    if (capacidad <= 0) throw new CapacidadHabitacionExcedidaException("Capacidad inválida");
    return new Habitacion(numero, tipo, capacidad, precioBase);
}
```
Ejemplo 2 - Creación de servicios con Decorator:
```
// En ServicioFactory.java
public Servicio crearServicio(String tipoServicio, Servicio baseService) 
    throws ServicioNoDisponibleException {
    
    switch (tipoServicio.toLowerCase()) {
        case "desayuno":
            return new DesayunoDecorator(baseService);
        case "spa":
            return new SpaDecorator(baseService);
        // ... otros servicios
    }
}
```
Uso:
```
Servicio servicio = factory.crearServicio("spa", new ServicioBasico());
// Devuelve: ServicioBasico decorado con SpaDecorator
```
Razón: Simplificar la creación de objetos que pueden tener configuraciones complejas (como servicios anidados).

---

### Decorator (Servicios Adicionales)
Propósito: Añadir funcionalidad dinámicamente a los servicios base.

Estructura:
```
// Servicio base
public class ServicioBasico implements Servicio {
    public double getCosto() { return 0.0; }
    public String getDescripcion() { return "Estancia base"; }
}

// Decorator abstracto
public abstract class ServicioAdicional implements Servicio {
    protected Servicio wrappee;
    // ... implementa métodos delegando al wrappee
}

// Decorator concreto (Desayuno)
public class DesayunoDecorator extends ServicioAdicional {
    private static final double COSTO_DESAYUNO = 15.0;
    
    @Override
    public double getCosto() {
        return wrappee.getCosto() + COSTO_DESAYUNO; // Suma al costo base
    }
}
```
Uso:
```
Servicio miServicio = new DesayunoDecorator(new SpaDecorator(new ServicioBasico()));
System.out.println(miServicio.getDescripcion()); 
// Output: "Estancia base, con Acceso a Spa, con Desayuno"
```
Razón: Permitir combinar servicios (desayuno, spa, mascota) sin crear clases explosivas como ServicioConDesayunoYSpa.

---

### Observer (NotificadorReservas)
Propósito: Notificar eventos importantes a múltiples componentes.

Implementación:
```
// En NotificadorReservas.java
public class NotificadorReservas implements Notifier {
    private List<ClienteObserver> observadores = new ArrayList<>();

    public void agregarObservador(ClienteObserver observer) {
        observadores.add(observer);
    }

    public void notificar(String mensaje) {
        for (ClienteObserver obs : observadores) {
            obs.update(mensaje); // Notifica a todos los observadores
        }
    }
}
```
Observador Concreto (Logger):
```
public class LoggerObserver {
    public void update(String mensaje) {
        System.out.println("[LOG] " + mensaje); // Ejemplo simplificado
    }
}
```
Uso:
```
// En GestorReservas.java
notificador.notificar("Reserva cancelada: " + idReserva);
// Notifica tanto al logger como al cliente afectado
```
Razón: Desacoplar el código que genera eventos (ej: cancelar reserva) del que los procesa (ej: enviar email, loggear).

---

### 👨‍💼 Autores
Integrantes del grupo 4: Nestor Ivan Fabian Colocho, Mery Acevedo y Alejandro Ernesto Juarez Argumedo.
