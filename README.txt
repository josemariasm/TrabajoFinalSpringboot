# Proyecto: API REST - Gestión de Proyectos


- José María Sánchez Martínez (DAM C)

## 1. Descripción del proyecto
Este es el trabajo práctico de desarrollo de una API REST con Spring Boot. El proyecto sirve como backend para un sistema de gestión de proyectos y tareas. Está programado usando Java 21, Spring Data JPA para la persistencia de datos y conectado a una base de datos MySQL. Hemos seguido la estructura clásica de capas: Controladores, Servicios, Repositorios (DAO) y Entidades.

## 2. Explicación del problema de negocio
El objetivo de la práctica es resolver el problema de organización que tienen muchas empresas a la hora de gestionar el trabajo de sus empleados. 
El sistema que hemos desarrollado emula una herramienta interna donde se pueden dar de alta proyectos, añadir trabajadores, crear tareas con fechas límite y asignarlas. También permite ir cambiando el estado de las tareas para ver cómo avanza el proyecto y dejar comentarios en ellas.

## 3. Entidades del sistema
Hemos modelado la base de datos con 6 entidades:
- Proyecto: guarda la información general y fechas.
- Usuario: los empleados que participan.
- Rol: el tipo de usuario (administrador, gestor, colaborador).
- Tarea: asociada a un proyecto y a un usuario.
- Estado: indica en qué punto está la tarea (pendiente, en progreso, finalizada).
- Comentario: mensajes que los usuarios dejan dentro de una tarea.

## 4. Instrucciones de ejecución
Para poder arrancar y probar el proyecto en un ordenador local, hay que seguir estos pasos:

1. Crear una base de datos en MySQL (desde DBeaver o consola) que se llame `gestion_proyectos`.
2. Revisar el archivo `application.properties` para comprobar que el usuario y la contraseña de MySQL coinciden con los del equipo local.
3. Arrancar la aplicación desde el IDE. Al tener activado `hibernate.ddl-auto=update`, las tablas se van a crear automáticamente.
4. Antes de probar nada, hay que insertar los roles y los estados base en la base de datos. Se puede hacer ejecutando este código SQL:

USE gestion_proyectos;

INSERT INTO rol (nombre) VALUES ('Administrador'), ('Gestor'), ('Colaborador');
INSERT INTO estado (nombre) VALUES ('Pendiente'), ('En progreso'), ('Finalizada');

## 5. Endpoints principales
La API funciona bajo la ruta base `/api`. Si hay errores (por ejemplo, si falta un nombre o no existe un id), devuelve un código 400 Bad Request con un mensaje personalizado.

Usuarios:
- GET /api/usuarios/lista -> Lista todos los usuarios.
- POST /api/usuarios/crear -> Crea un usuario nuevo.
- PUT /api/usuarios/actualizar/{id} -> Cambia el nombre de un usuario.
- DELETE /api/usuarios/delete/{id} -> Borra un usuario.

Proyectos:
- POST /api/proyectos/crear -> Crea un proyecto nuevo con fechas.
- POST /api/proyectos/{idProyecto}/asignarUsuarios -> Mete a los usuarios indicados en el proyecto.
- GET /api/proyectos/{idProyecto}/avance -> Devuelve cuántas tareas hay en cada estado.

Tareas:
- POST /api/tareas/crear/{idProyecto} -> Crea una tarea dentro de un proyecto.
- PUT /api/tareas/actualizar/{id} -> Modifica los datos de la tarea.
- DELETE /api/tareas/delete/{id} -> Borra la tarea.
- PUT /api/tareas/{idTarea}/asignarUsuario/{idUsuario} -> Asigna la tarea a un usuario.
- PUT /api/tareas/{idTarea}/cambiarEstado/{idEstado} -> Pasa la tarea a otro estado.
- POST /api/tareas/{idTarea}/comentar/{idUsuario} -> Añade un comentario a la tarea.

## 6. Pruebas con Postman
Para facilitar la corrección, adjuntamos la colección de Postman con todos los casos de uso ya preparados. Solo hay que importar el siguiente JSON en Postman (opción Import -> Raw text):

{
  "info": {
    "_postman_id": "b2c3d4e5-f6a7-8901-bcde-2345678901bc",
    "name": "Pruebas API Gestion Proyectos",
    "description": "Pruebas del trabajo final",
    "schema": "https://schema.getpostman.com/json/collection/v2.1.0/collection.json"
  },
  "item": [
    {
      "name": "1. Usuarios",
      "item": [
        {
          "name": "Crear Usuario",
          "request": {
            "method": "POST",
            "header": [{"key": "Content-Type", "value": "application/json"}],
            "body": {"mode": "raw", "raw": "{\n    \"nombre\": \"Guillermo Malde\"\n}"},
            "url": {"raw": "{{baseUrl}}/api/usuarios/crear", "host": ["{{baseUrl}}"], "path": ["api", "usuarios", "crear"]}
          }
        },
        {
          "name": "Listar Usuarios",
          "request": {
            "method": "GET",
            "url": {"raw": "{{baseUrl}}/api/usuarios/lista", "host": ["{{baseUrl}}"], "path": ["api", "usuarios", "lista"]}
          }
        },
        {
          "name": "Modificar Usuario",
          "request": {
            "method": "PUT",
            "header": [{"key": "Content-Type", "value": "application/json"}],
            "body": {"mode": "raw", "raw": "{\n    \"nombre\": \"Jose Carlos (Modificado)\"\n}"},
            "url": {"raw": "{{baseUrl}}/api/usuarios/actualizar/1", "host": ["{{baseUrl}}"], "path": ["api", "usuarios", "actualizar", "1"]}
          }
        },
        {
          "name": "Eliminar Usuario",
          "request": {
            "method": "DELETE",
            "url": {"raw": "{{baseUrl}}/api/usuarios/delete/2", "host": ["{{baseUrl}}"], "path": ["api", "usuarios", "delete", "2"]}
          }
        }
      ]
    },
    {
      "name": "2. Proyectos",
      "item": [
        {
          "name": "Crear Proyecto",
          "request": {
            "method": "POST",
            "header": [{"key": "Content-Type", "value": "application/json"}],
            "body": {"mode": "raw", "raw": "{\n    \"nombre\": \"Proyecto Final\",\n    \"descripcion\": \"Desarrollo de API\",\n    \"fechaInicio\": \"2024-05-01\",\n    \"fechaFin\": \"2024-06-15\"\n}"},
            "url": {"raw": "{{baseUrl}}/api/proyectos/crear", "host": ["{{baseUrl}}"], "path": ["api", "proyectos", "crear"]}
          }
        },
        {
          "name": "Asignar Usuarios a Proyecto",
          "request": {
            "method": "POST",
            "header": [{"key": "Content-Type", "value": "application/json"}],
            "body": {"mode": "raw", "raw": "[\n    1\n]"},
            "url": {"raw": "{{baseUrl}}/api/proyectos/1/asignarUsuarios", "host": ["{{baseUrl}}"], "path": ["api", "proyectos", "1", "asignarUsuarios"]}
          }
        },
        {
          "name": "Consultar Avance",
          "request": {
            "method": "GET",
            "url": {"raw": "{{baseUrl}}/api/proyectos/1/avance", "host": ["{{baseUrl}}"], "path": ["api", "proyectos", "1", "avance"]}
          }
        }
      ]
    },
    {
      "name": "3. Tareas",
      "item": [
        {
          "name": "Crear Tarea",
          "request": {
            "method": "POST",
            "header": [{"key": "Content-Type", "value": "application/json"}],
            "body": {"mode": "raw", "raw": "{\n    \"titulo\": \"Modelo de base de datos\",\n    \"descripcion\": \"Diseñar las entidades\",\n    \"fechaVencimiento\": \"2024-05-10\"\n}"},
            "url": {"raw": "{{baseUrl}}/api/tareas/crear/1", "host": ["{{baseUrl}}"], "path": ["api", "tareas", "crear", "1"]}
          }
        },
        {
          "name": "Actualizar Tarea",
          "request": {
            "method": "PUT",
            "header": [{"key": "Content-Type", "value": "application/json"}],
            "body": {"mode": "raw", "raw": "{\n    \"titulo\": \"Modelo de base de datos final\",\n    \"descripcion\": \"Diseñar entidades y relaciones\",\n    \"fechaVencimiento\": \"2024-05-12\"\n}"},
            "url": {"raw": "{{baseUrl}}/api/tareas/actualizar/1", "host": ["{{baseUrl}}"], "path": ["api", "tareas", "actualizar", "1"]}
          }
        },
        {
          "name": "Asignar Usuario a Tarea",
          "request": {
            "method": "PUT",
            "url": {"raw": "{{baseUrl}}/api/tareas/1/asignarUsuario/1", "host": ["{{baseUrl}}"], "path": ["api", "tareas", "1", "asignarUsuario", "1"]}
          }
        },
        {
          "name": "Cambiar Estado",
          "request": {
            "method": "PUT",
            "url": {"raw": "{{baseUrl}}/api/tareas/1/cambiarEstado/1", "host": ["{{baseUrl}}"], "path": ["api", "tareas", "1", "cambiarEstado", "1"]}
          }
        },
        {
          "name": "Comentar Tarea",
          "request": {
            "method": "POST",
            "header": [{"key": "Content-Type", "value": "application/json"}],
            "body": {"mode": "raw", "raw": "{\n    \"texto\": \"Ya he terminado el esquema relacional\"\n}"},
            "url": {"raw": "{{baseUrl}}/api/tareas/1/comentar/1", "host": ["{{baseUrl}}"], "path": ["api", "tareas", "1", "comentar", "1"]}
          }
        },
        {
          "name": "Eliminar Tarea",
          "request": {
            "method": "DELETE",
            "url": {"raw": "{{baseUrl}}/api/tareas/delete/2", "host": ["{{baseUrl}}"], "path": ["api", "tareas", "delete", "2"]}
          }
        }
      ]
    }
  ],
  "variable": [
    {
      "key": "baseUrl",
      "value": "http://localhost:8080",
      "type": "string"
    }
  ]
}