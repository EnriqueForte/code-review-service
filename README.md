# Code Review Service

**Code Review Service** es una aplicación web diseñada para automatizar la revisión de código. La herramienta clona repositorios, ejecuta análisis de calidad de código mediante Checkstyle y muestra issues y métricas en un dashboard interactivo. Esto facilita la identificación de problemas en el código y mejora la calidad general de los proyectos.

## Tabla de Contenidos

- [Funcionalidades](#funcionalidades)
- [Tecnologías](#tecnologías)
- [Requisitos](#requisitos)
- [Instalación y Configuración](#instalación-y-configuración)
  - [Clonar el Repositorio](#clonar-el-repositorio)
  - [Configurar la Base de Datos](#configurar-la-base-de-datos)
  - [Ejecutar la Aplicación](#ejecutar-la-aplicación)
- [Uso de la Aplicación](#uso-de-la-aplicación)
  - [Landing Page](#landing-page)
  - [Gestión de Proyectos (Index)](#gestión-de-proyectos-index)
  - [Visualización de Issues](#visualización-de-issues)
  - [Dashboard](#dashboard)

## Funcionalidades

- **Gestión de Proyectos:**  
  - Crear, editar y eliminar proyectos.
  - Visualizar la lista de proyectos existentes (sin mostrar el ID).
- **Análisis de Código:**  
  - Clonar repositorios mediante JGit.
  - Ejecutar Checkstyle para detectar problemas de estilo.
- **Visualización de Issues:**  
  - Mostrar en una tabla los problemas detectados.
- **Dashboard Interactivo:**  
  - Visualizar estadísticas (total de proyectos, total de issues) y gráficos (por ejemplo, issues por proyecto con Chart.js).
- **Interfaz moderna y responsiva:**  
  - Diseño original basado en Thymeleaf, Bootstrap, Font Awesome y Google Fonts.
  - Landing page (carátula) de bienvenida con un diseño que remite al mundo del código.

## Tecnologías

- **Backend:** Java 23, Spring Boot, Spring Data JPA.
- **Frontend:** Thymeleaf, Bootstrap 4.5, Font Awesome, Google Fonts (Montserrat).
- **Análisis de Código:** Checkstyle.
- **Utilidades:** JGit, Chart.js.
- **Base de Datos:** MySQL (con posibilidad de usar H2 para pruebas).
- **Build y Dependencias:** Maven.
- **Testing:** JUnit 5, Mockito, Spring Boot Test, JaCoCo (para cobertura).

## Requisitos

- JDK 23 (o la versión configurada en el proyecto)
- Maven 3.6 o superior
- MySQL (o H2 para entornos de prueba)
- Git

## Instalación y Configuración

### Clonar el Repositorio

1. Abre una terminal (o usa Visual Studio Code con la terminal integrada) y clona el repositorio:
   ```bash
   git clone https://github.com/EnriqueForte/code-review-service.git
   cd code-review-service
   
### Configurar la Base de Datos

1.Edita el archivo src/main/resources/application.properties para configurar la conexión a tu base de datos MySQL:
spring.datasource.url=jdbc:mysql://localhost:3306/nombredbtest?createDatabaseIfNotExist=true&useSSL=false
spring.datasource.driverClassName=com.mysql.cj.jdbc.Driver
spring.datasource.username=tu_usuario
spring.datasource.password=tu_contraseña
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
server.port=8081
Reemplaza tu_usuario y tu_contraseña con tus credenciales reales.

### Ejecutar la Aplicación

1.Desde la raíz del proyecto, ejecuta:
mvn spring-boot:run

### Uso de la Aplicación

## Landing Page

La landing page (carátula) se muestra en la ruta / y tiene un diseño original con fondo relacionado con código, tipografía moderna y un botón "Ingresar" que redirige a la página principal de gestión (/index).

## Gestión de Proyectos (Index)

En /index podrás:
Crear nuevos proyectos mediante un formulario.
Visualizar la lista de proyectos existentes en una tabla (sin mostrar el campo ID) junto con botones para:
Revisar el proyecto (clona el repositorio y ejecuta Checkstyle).
Ver Issues asociados.
Editar y Eliminar proyectos.
También se muestra un botón para acceder al dashboard.

## Visualización de Issues

Al pulsar "Ver Issues" en un proyecto, se muestra una página con la lista de issues detectados, usando la plantilla issues.html.
En esta vista se muestra el nombre del proyecto, una tabla con los issues y un botón "Volver" que redirige a /index.

## Dashboard

La plantilla dashboard.html muestra un panel de control interactivo que incluye:
Estadísticas generales (total de proyectos y total de issues).
Un gráfico (por ejemplo, de barras) que visualiza la distribución de issues por proyecto, utilizando Chart.js.
Un botón "Volver" para regresar a /index.



