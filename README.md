## Descripción del Repositorio

Este repositorio contiene el desarrollo del **microservicio de gestión de eventos** realizado como parte del proyecto de tesis titulado *"Construcción de un Prototipo de Arquitectura Modular Basada en Microservicios para la Optimización de Sistemas de Monitoreo GPS"*.

El objetivo principal del microservicio es **procesar y gestionar eventos relacionados con dispositivos GPS**, tales como alertas de movimiento, cambios de estado, notificaciones por geocerca (geofencing), entre otros. Este componente fue desarrollado utilizando **Spring Boot** en lenguaje **Java**, con el fin de desacoplar funcionalidades específicas del sistema monolítico original **Traccar** y migrar progresivamente hacia una arquitectura basada en microservicios.

---

## Objetivo del Microservicio

- **Procesamiento eficiente de eventos generados por dispositivos GPS**
- **Almacenamiento estructurado de los eventos en base de datos**
- **Desacoplamiento modular del sistema monolítico Traccar**
- **Servir como base para futuras implementaciones de microservicios similares**

---

## Arquitectura y Tecnologías Utilizadas

- **Lenguaje:** Java
- **Framework:** Spring Boot
- **Base de Datos:** MongoDB
- **Comunicación:** REST API
- **Colas de mensajes:** RabbitMQ
- **Contenerización:** Docker

---

## Clonar

### Pasos:

1. Clona el repositorio:
   ```bash
   git clone https://github.com/MrT-coder/TRACCAR_EVENTS_JAVA.git
   ```

2. Navega al directorio:
   ```bash
   cd TRACCAR_EVENTS_JAVA
   ```
---

## Endpoints Principales

| Método | Ruta                | Descripción                        |
|--------|---------------------|------------------------------------|
| GET    | `/api/events`       | Lista todos los eventos            |
| GET    | `/api/events/{id}`  | Obtiene un evento por ID           |
| POST   | `/api/events`       | Crea un nuevo evento               |
| PUT    | `/api/events/{id}`  | Actualiza un evento existente      |
| DELETE | `/api/events/{id}`  | Elimina un evento                  |

---

## Referencia al Proyecto Principal

Este repositorio forma parte de un conjunto más amplio de microservicios desarrollados durante la tesis mencionada. Otros repositorios relacionados:

- [Microservicio de Posición en Java](https://github.com/MrT-coder/TRACCAR_POSITION-GEOFENCE_JAVA)
- [Microservicio de Posición en C/C++](https://github.com/MrT-coder/POSITION_GEOFENCE_C-)

---

## Estado del Proyecto

Este proyecto se encuentra en estado **experimental y básico**, aún con fallos, desarrollado principalmente como parte de una investigación académica. Es útil como punto de partida para proyectos futuros relacionados con sistemas de monitoreo GPS basados en microservicios.

---

## Contribución

Si deseas contribuir al proyecto, siéntete libre de abrir un *issue* o enviar un *pull request*. ¡Cualquier ayuda es bienvenida!

---
