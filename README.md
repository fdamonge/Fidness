# Fidness - Aplicación de Gestión de Ejercicios y Rutinas

Aplicación de escritorio desarrollada en Java con interfaz gráfica Swing para la cadena de gimnasios Fidness.

## Funcionalidades

- Inicio de sesión con usuario y contraseña
- Registro de nuevos usuarios
- Consulta de ejercicios por grupo muscular
- Visualización detallada de cómo ejecutar cada ejercicio
- Creación de rutinas personalizadas
- Exportación de rutinas a archivo de texto

## Conceptos Aplicados

- Clases y objetos
- Herencia (Persona → Usuario, Administrador)
- Polimorfismo (métodos abstractos, interfaces)
- Excepciones personalizadas
- Colecciones (ArrayList, HashMap)
- Serialización (persistencia de datos)
- Interfaz gráfica de usuario (Java Swing)
- Multihilos (exportación en hilo separado)

## Cómo ejecutar

1. Abrir el proyecto en NetBeans o cualquier IDE Java
2. Compilar y ejecutar la clase `Main.java`

### Credenciales por defecto:
- **Admin:** usuario `admin` / contraseña `admin123`
- **Cliente:** usuario `cliente` / contraseña `cliente123`

## Estructura del proyecto

```
src/
├── Main.java
├── modelo/          → Entidades (Persona, Usuario, Administrador, Ejercicio, Rutina)
├── controlador/     → Lógica de negocio (GestorUsuarios, GestorEjercicios, GestorRutinas)
├── vista/           → Interfaz gráfica Swing (7 ventanas)
├── excepciones/     → Excepciones personalizadas
└── util/            → Utilidades (Exportable, ExportadorRutina)
```

## Tecnologías

- Java SE 17+
- Java Swing (sin frameworks)
- Serialización con ObjectOutputStream/ObjectInputStream

## Autora

María Fernanda Monge Sibaja  
Programación Cliente/Servidor Concurrente
