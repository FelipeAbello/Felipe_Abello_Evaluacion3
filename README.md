Nombre: Felipe Abello
Fecha: 04-12-2015

# Evaluación 3: Sistema de Gestión "Mueblería Los Muebles Hermanos S.A."

Este proyecto consiste en el desarrollo, dockerización y despliegue de una solución Full-Stack para la gestión de inventario y ventas de muebles. El sistema implementa una arquitectura de microservicios contenerizada, separando el Backend (Spring Boot), la Base de Datos (MySQL) y el Frontend (Web Estática/Nginx).

## Tecnologías Utilizadas

* **Lenguaje Backend:** Java 21 (Eclipse Temurin).
* **Framework:** Spring Boot 3.x (Spring Web, Spring Data JPA).
* **Base de Datos:** MySQL 8.0.
* **Frontend:** HTML5, CSS3 (Estilo Cyberpunk), JavaScript Vanilla.
* **Servidor Web:** Nginx (Alpine Linux).
* **Contenerización:** Docker & Docker Compose.
* **Herramientas de Build:** Maven Wrapper (`mvnw`).

---

## Arquitectura del Proyecto

El proyecto se orquesta mediante `docker-compose`, levantando 3 servicios interconectados:

1.  **`mysqldb`**: Persistencia de datos (Puerto interno 3306, expuesto en 3307).
2.  **`backend`**: API RESTful con lógica de negocio (Puerto 8080).
3.  **`frontend`**: Interfaz de Usuario servida por Nginx (Puerto 3000).

## Instrucciones de Ejecución y Despliegue

Requisitos Previos
Tener Docker Desktop instalado y corriendo.

Pasos para Ejecutar
Abrir una terminal en la carpeta raíz del proyecto (donde está docker-compose.yml).

Ejecutar el siguiente comando para compilar, construir y levantar los contenedores:

docker-compose up --build

Esperar a que la terminal muestre el mensaje: Started IngsoftApplication in ... seconds.

Acceso a la Aplicación
Frontend (Cliente & Admin): http://localhost:3000

Backend (API - JSON): http://localhost:8080/api/muebles

---

## Manual de usuario:

Modo Cliente (Catálogo y Compras)
Al ingresar a localhost:3000, verá el catálogo de muebles ACTIVOS.

Puede agregar productos al Carrito de Compras (barra lateral derecha).

Al presionar "CONFIRMAR VENTA", el sistema:

Genera una cotización en el backend.

Verifica si hay stock suficiente.

Descuenta el stock real de la base de datos.

Confirma la compra.

Modo Administrador (Gestión de Stock)
En la página principal, haga clic en el botón flotante "PANEL ADMIN".

Contraseña de acceso: admin123

Funcionalidades:

Crear: Formulario para registrar nuevos muebles.

Leer: Tabla con el inventario completo (incluyendo inactivos).

Actualizar: Botón "EDITAR" para modificar precio, stock o nombre.

Borrado Lógico: Botón para "DESACTIVAR" muebles (no los borra, solo los oculta del cliente).

Reactivación: Posibilidad de volver a "ACTIVAR" muebles previamente desactivados.

---

### Estructura de Directorios
```text
Felipe_Abello_Evaluacion3/
├── backend/                # Código Fuente Java Spring Boot
│   ├── src/
│   ├── Dockerfile          # Configuración de imagen Java 21
│   └── pom.xml
├── frontend/               # Código Fuente Interfaz Web
│   ├── index.html          # Catálogo y Carrito (Cliente)
│   ├── admin.html          # Panel de Gestión CRUD (Administrador)
│   └── Dockerfile          # Configuración de imagen Nginx
├── docker-compose.yml      # Orquestación de servicios
└── README.md               # Documentación