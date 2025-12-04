TareaIngSoftware2
Tarea 2 de ingeniería de software
Nombre: Felipe Abello
Fecha: 12-11-2015

Mueblería Los Muebles Hermanos - Backend

Sistema de gestión de muebles con cotizaciones y ventas desarrollado con Spring Boot y MySQL.

Descripción

Backend para la gestión de una mueblería que permite:
- Administrar catálogo de muebles (CRUD)
- Registrar variantes de productos (barniz premium, cojines, ruedas, etc.)
- Crear cotizaciones con múltiples muebles
- Confirmar ventas con control de stock
- Cálculo de precios con diferentes estrategias

Tecnologías Utilizadas

- **Java 21**
- **Spring Boot 3.5.7**
  - Spring Web (API REST)
  - Spring Data JPA (Persistencia)
  - Spring Boot DevTools (Desarrollo)
- **MySQL** (Base de datos)
- **JUnit 5** (Testing)
- **Mockito** (Mocks para testing)
- **Maven** (Gestión de dependencias)

Dependencias del pom.xml
```xml
<!-- Spring Boot Starters -->
- spring-boot-starter-web: Para crear API REST
- spring-boot-starter-data-jpa: Para persistencia con JPA/Hibernate
- spring-boot-starter-test: Para testing con JUnit y Mockito
- spring-boot-devtools: Hot reload durante desarrollo

<!-- Base de datos -->
- mysql-connector-j: Driver de MySQL

<!-- Testing -->
- junit-jupiter-api: API de JUnit 5
- junit-jupiter-engine: Motor de ejecución de JUnit 5
- mockito-core: Framework de mocking
- mockito-junit-jupiter: Integración Mockito con JUnit 5
```

Patrones de Diseño Implementados

### 1. **Strategy Pattern** (Patrón Estrategia)
- **Ubicación**: `service/strategy/`
- **Propósito**: Permite cambiar dinámicamente el algoritmo de cálculo de precios
- **Clases**:
  - `PrecioStrategy` (interfaz)
  - `PrecioNormalStrategy` (precio base + variante)
  - `PrecioConDescuentoStrategy` (precio con descuentos)
- **Uso**: En `PrecioService` para calcular precios según diferentes estrategias

### 2. **Factory Pattern** (Patrón Fábrica)
- **Ubicación**: `service/factory/`
- **Propósito**: Centraliza la creación de objetos complejos (Cotizaciones)
- **Clase**: `CotizacionFactory`
- **Métodos**:
  - `crearCotizacionVacia()`
  - `crearCotizacionSimple()`
  - `crearDetalleCotizacion()`
  - `confirmarCotizacion()`
- **Uso**: En `CotizacionService` para crear y gestionar cotizaciones

### 3. **Singleton Pattern** (Patrón Singleton)
- **Implementación**: Automática mediante `@Service` de Spring
- **Todas las clases de servicio son Singleton**
- Spring gestiona una única instancia de cada servicio en el contenedor

Modelo de Datos

Entidades principales:
- **Mueble**: Productos del catálogo (sillas, mesas, estantes, etc.)
- **Variante**: Modificaciones al producto (barniz, cojines, ruedas)
- **Cotizacion**: Cotizaciones de venta
- **DetalleCotizacion**: Items de cada cotización (mueble + variante + cantidad)

-----INSTALACIÓN Y CONFIGURACIÓN-----

Requisitos previos:
- Java 21 o superior
- MySQL 8.0 o superior
- XAMPP (o cualquier servidor MySQL local)

Paso 1: Clonar el repositorio
```bash
git clone https://github.com/FelipeAbello/TareaIngSoftware2
cd TareaIngSoftware2
```

Paso 2: Configurar la base de datos

1. Inicia XAMPP y arranca MySQL
2. Abre phpMyAdmin (http://localhost/phpmyadmin)
3. Ejecutar el script sql que se encuentra en "comandoSQL.txt", en esta misma carpeta

Paso 3: Configurar application.properties

Edita `src/main/resources/application.properties`:
```properties
spring.datasource.url=jdbc:mysql://localhost:3306/muebleria
spring.datasource.username=root
spring.datasource.password=
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQLDialect

server.port=8080
```

**Nota**: Ajusta `username` y `password` según tu configuración de MySQL.

Paso 4: Compilar el proyecto
```bash
.\mvnw.cmd clean install
```

Paso 5: Ejecutar la aplicación
```bash
.\mvnw.cmd spring-boot:run
```

La aplicación estará disponible en: `http://localhost:8080`
Sin embargo, se mostrará una pantalla de "Whitelabel Error Page",
esto se debe a que no hay interfaz gráfica.
A lo que se puede acceder son los endpoints, como por ejemplo: `http://localhost:8080/api/muebles`

Ejecutar Tests
```bash
# Ejecutar todos los tests
.\mvnw.cmd test

# Ejecutar un test específico
.\mvnw.cmd test -Dtest=PrecioServiceTest

# Ejecutar tests con reporte detallado
.\mvnw.cmd test -X
```

### Tests implementados:
- **PrecioServiceTest**: Testing de cálculo de precios con variantes
- **MuebleServiceTest**: Testing de gestión de stock y ventas
- **MuebleCrudTest**: Testing de operaciones CRUD
- **CotizacionServiceTest**: Testing de confirmación de ventas

API REST - Endpoints

### Usar Thunder Client en Visual Studio Code (Recomendado)

#### Paso 1: Instalar Thunder Client
1. Abre Visual Studio Code
2. Presiona `Ctrl + Shift + X` para abrir las extensiones
3. Busca **"Thunder Client"**
4. Haz click en **Install**

#### Paso 2: Crear una petición
1. Haz click en el icono del rayo en la barra lateral izquierda
2. Click en **"New Request"**
3. Configura la petición:
   - **Método**: Selecciona GET, POST, PUT o DELETE según la operación
   - **URL**: `http://localhost:8080/api/muebles`
4. Para peticiones POST/PUT:
   - Ve a la pestaña **"Body"**
   - Selecciona **"JSON"**
   - Pega el contenido JSON
5. Click en **"Send"**

#### Paso 3: Realizar peticiones en Thunder Client, estas son las peticiones que se pueden hacer:

Muebles
```
GET    /api/muebles              - Listar todos los muebles
GET    /api/muebles/{id}         - Obtener mueble por ID
GET    /api/muebles/activos      - Listar muebles activos
GET    /api/muebles/tipo/{tipo}  - Buscar por tipo
POST   /api/muebles              - Crear nuevo mueble
PUT    /api/muebles/{id}         - Actualizar mueble (Para activar mueble: /api/muebles/{id}/activar)
DELETE /api/muebles/{id}         - Desactivar mueble
```

Variantes
```
GET    /api/variantes            - Listar todas las variantes
GET    /api/variantes/{id}       - Obtener variante por ID
POST   /api/variantes            - Crear nueva variante
PUT    /api/variantes/{id}       - Actualizar variante (Para activar variante: /api/variantes/{id}/activar)
DELETE /api/variantes/{id}       - Eliminar variante
```

Cotizaciones
```
GET    /api/cotizaciones                    - Listar todas
GET    /api/cotizaciones/{id}               - Obtener por ID
GET    /api/cotizaciones/pendientes         - Listar pendientes
GET    /api/cotizaciones/ventas             - Listar ventas confirmadas
POST   /api/cotizaciones                    - Crear cotización vacía
POST   /api/cotizaciones/{id}/detalles      - Agregar mueble a cotización
PUT    /api/cotizaciones/{id}/confirmar     - Confirmar venta
```

#### Paso 4: Click en Send

Ejemplos de uso con cURL (Comandos en la terminal)

Crear un mueble:
```bash
curl -X POST http://localhost:8080/api/muebles \
  -H "Content-Type: application/json" \
  -d '{
    "nombreMueble": "Silla Moderna",
    "tipo": "SILLA",
    "precioBase": 35000,
    "stock": 20,
    "tamanio": "PEQUENO",
    "material": "Metal"
  }'
```

Crear cotización y agregar mueble:
```bash
# 1. Crear cotización vacía
curl -X POST http://localhost:8080/api/cotizaciones

# 2. Agregar mueble a la cotización (ID=1)
curl -X POST http://localhost:8080/api/cotizaciones/1/detalles \
  -H "Content-Type: application/json" \
  -d '{
    "idMueble": 1,
    "idVariante": 2,
    "cantidad": 3
  }'

# 3. Confirmar venta
curl -X PUT http://localhost:8080/api/cotizaciones/1/confirmar
```

## 📂 Estructura del Proyecto
```
src/
├── main/
│   ├── java/com/tarea2/ingsoft/
│   │   ├── model/              # Entidades JPA
│   │   ├── repository/         # Repositorios (acceso a datos)
│   │   ├── service/            # Lógica de negocio
│   │   │   ├── strategy/       # Patrón Strategy
│   │   │   └── factory/        # Patrón Factory
│   │   └── controller/         # Controladores REST
│   └── resources/
│       └── application.properties
└── test/
    └── java/com/tarea2/ingsoft/
        └── service/            # Tests unitarios
```