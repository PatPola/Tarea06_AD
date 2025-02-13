# Tarea06_AD
Proyecto Java y MongoDB -RedSocial. Tarea06 de Acceso a datos (Bases de datos no SQL - MONGODB)|DAM-E ELARNING 2024

## Descripción

Este proyecto consiste en una aplicación Java que interactúa con MongoDB para gestionar una base de datos llamada **RedSocial**. En la base de datos, hay una colección llamada **mensajes**que almacena mensajes con información sobre el texto, número de "me gusta", y detalles de los usuarios. La aplicación Java proporciona una serie de operaciones CRUD sobre los mensajes.

## Enunciado
Mediante la consola o mediante CompassDB crear una base de datos llamada RedSocial con una colección llamada mensajes e insertar los siguientes documentos en la colección mensajes: 
texto: A ver si hoy sale el sol 
numero_megustas: 5 
usuario:  
       email: pepe@gmail.com 
       rutaFoto: 34deg4&d.jpg 
texto: Parece que va a estar nublado todo el día 
numero_megustas: 0 
usuario:  
     email: dolores@gmail.com 
     rutaFoto: 45345oij34.jpg 
texto: Pues menos mal que estamos en verano 
numero_megustas: 57 
usuario: 
       email: pepe@gmail.com 
       rutaFoto: 34deg4&d.jpg 
       
Crear una aplicación java con las siguientes opciones: 
1. Mostrar todos los mensajes (se mostrarán todos los datos de los mensajes y sus usuarios) 
2. Mostrar lo mensajes con numero_megustas > 3 
3. Incrementar en uno el numero_megustas de los mensajes escritos por pepe@gmail.com 
4. Borrar  los mensajes escritos por dolores@gmail.com 
5. Insertar un mensaje solicitando el texto, numero_megustas, email y rutaFoto 
6. Salir 
## Requisitos

- **Java** 11 o superior.
- **MongoDB** instalado en tu máquina o acceso a una instancia remota.
- **MongoDB Compass** (opcional, para facilitar la visualización y gestión de la base de datos).
  

