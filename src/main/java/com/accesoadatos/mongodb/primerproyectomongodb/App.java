package com.accesoadatos.mongodb.primerproyectomongodb;

import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import org.bson.Document;
import org.bson.conversions.Bson;

import com.mongodb.ConnectionString;
import com.mongodb.MongoException;
import com.mongodb.client.*;
import com.mongodb.client.model.Filters;

import com.mongodb.client.model.Updates;
import com.mongodb.client.result.DeleteResult;

import util.Utilidades;

/**
 * Hello world!
  @author Patricia Pola Caballero
 */
public class App {

	static MongoCollection<Document> coleccionMensajes;
	static MongoClient mongoClient;
	static ConnectionString connectionString;
	static Scanner input = new Scanner(System.in);

	public static void main(String[] args) {
		// Creo la conexion con mongo y me conecto a la bd para obtener la colección
		conexionMongo();
		conexionBd();
		//inserto documentos
		insertarDocumentos();
		boolean salir = false;
		int opcion = 0;

		do {
			Utilidades.muestraMenu();
			try {
				opcion = input.nextInt();
				input.nextLine();
				switch (opcion) {
				case 1:
					System.out.println("---------MENSAJES Y USUARIOS----------");
					System.out.println("Todos los elementos de la colección mensajes:");
					// Obtener todos los documentos de la colección
					FindIterable<Document> resultados = coleccionMensajes.find();
					// Iterar sobre los resultados y mostrar cada mensaje y su usuario
					MongoCursor<Document> cursor = resultados.iterator();
					while (cursor.hasNext()) {
						Document documento = cursor.next();
						System.out.println("Mensaje:");
						System.out.println("Texto: " + documento.getString("texto"));
						System.out.println("Número de me gustas: " + documento.getInteger("numero_megustas"));
						// Obtengo el subdocumento usuario
						Document usuario = documento.get("usuario", Document.class);
						System.out.println("Usuario:");
						System.out.println("Email: " + usuario.getString("email"));
						System.out.println("Ruta de la foto: " + usuario.getString("rutaFoto"));
						System.out.println("------------------------------------------");
					}
					cursor.close();

					break;
				case 2:
					System.out.println("---------MENSAJES CON MAS DE 3 ME GUSTA----------");
					// creo un filtro
					FindIterable<Document> resultado = coleccionMensajes.find(Filters.gt("numero_megustas", 3));
					// itero sobre los resultados
					MongoCursor<Document> curs = resultado.iterator();
					while (curs.hasNext()) {
						Document documento = curs.next();
						System.out.println("Mensaje:");
						System.out.println("Texto: " + documento.getString("texto"));
						System.out.println("Número de me gustas: " + documento.getInteger("numero_megustas"));
						// Obtengo el subdocumento usuario
						Document usuario = documento.get("usuario", Document.class);
						System.out.println("Usuario:");
						System.out.println("Email: " + usuario.getString("email"));
						System.out.println("Ruta de la foto: " + usuario.getString("rutaFoto"));
						System.out.println("------------------------------------------");
					}

					curs.close();
					break;

				case 3:
					System.out.println("---------INCREMENTAR ME GUSTAS DE LOS MENSAJES DE PEPE----------");
					// Filtro los doc de pepe
					FindIterable<Document> result = coleccionMensajes
							.find(Filters.eq("usuario.email", "pepe@gmail.com"));

					// Itero sobre los resultados y actualizo el campo "numero_megustas" en cada
					// documento
					for (Document mensaje : result) {
						// Obtener el valor actual de "numero_megustas" y sumarle 1
						int meGustasActuales = mensaje.getInteger("numero_megustas", 0);
						int nuevoNumeroMeGustas = meGustasActuales + 1;
						// Actualizo el campo "numero_megustas" en el documento
						coleccionMensajes.updateOne(Filters.eq("_id", mensaje.getObjectId("_id")), // Filtro por el ID
																									// del documento
								Updates.set("numero_megustas", nuevoNumeroMeGustas) // Actualizo el campo
																					// "numero_megustas"
						);

					}
					// Ahora, muestro los mensajes de Pepe con su número actualizado de "me gusta"
					System.out.println("---------MENSAJES DE PEPE CON SU NUEVO NÚMERO DE ME GUSTA----------");
					for (Document mensaje : result) {
						System.out.println("Mensaje: " + mensaje.getString("texto"));
						System.out.println("--Número de Me gusta: " + mensaje.getInteger("numero_megustas"));
					}
					break;
				case 4:
					System.out.println("---------ELIMINAR MENSAJES DE DOLORES----------");
					// Creo un filtro para identificar los mensajes de Dolores

					Bson filtroDolores = Filters.eq("usuario.email", "dolores@gmail.com");

					// Elimino los mensajes de Dolores
					DeleteResult resultDelete = coleccionMensajes.deleteMany(filtroDolores);
					// Muestro el número de mensajes eliminados
					System.out.println("Número de mensajes eliminados de Dolores: " + resultDelete.getDeletedCount());
					break;
				case 5:
					System.out.println("---------INSERTAR MENSAJES----------");
					System.out.println("Introduce texto para el mensaje:");
					String texto = Utilidades.solicitaString();
					System.out.println("Introduce número de me gustas");
					int meGusta = Utilidades.solicitaInt();
					String email = Utilidades.solicitaMail();
					System.out.println("Introduce ruta foto:");
					String ruta = Utilidades.solicitaString();
					// Crear un nuevo documento para representar el mensaje
					Document nuevoMensaje = new Document();
					nuevoMensaje.put("texto", texto);
					nuevoMensaje.put("numero_megustas", meGusta);
					Document usuario = new Document();
					usuario.put("email", email);
					usuario.put("rutaFoto", ruta);
					nuevoMensaje.put("usuario", usuario);

					try {
						// Inserto el nuevo mensaje en la colección
						coleccionMensajes.insertOne(nuevoMensaje);
						System.out.println("Mensaje insertado correctamente.");
					} catch (MongoException e) {
						System.out.println("Error al insertar el mensaje: " + e.getMessage());
					}
					break;
				case 6:
					salir = true;
					break;
				default:
					System.out.println("Las opciones son números del 1 al 6");
				}
			} catch (InputMismatchException e) {
				System.out.println("No has introducido un número");
				input.nextLine();
			}
		} while (!salir);

	}

	public static void conexionMongo() {
		// Creo la conexion con mongo y me conecto a la bd para obtener la colección
		try {
			connectionString = new ConnectionString("mongodb://localhost:27017");
			mongoClient = MongoClients.create(connectionString);
		} catch (MongoException e) {
			System.out.println("Error de conexion con mongo" + e.getMessage());
		}
	}

	public static void conexionBd() {
		try {
			MongoDatabase database = mongoClient.getDatabase("RedSocial");
			coleccionMensajes = database.getCollection("mensajes");
		} catch (MongoException e) {
			System.out.println("Error al obtener la colección" + coleccionMensajes + e.getMessage());
		}
	}

	public static void insertarDocumentos() {
		///////////// Insertar un documento ///////////////////////
		Document nuevoMensaje1 = new Document();
		nuevoMensaje1.put("texto", "a ver si sale el sol");
		nuevoMensaje1.put("numero_megustas", 5);

		Document usuario = new Document();
		usuario.put("email", "pepe@gmail.com");
		usuario.put("rutaFoto", "34deg4&d.jpg");
		nuevoMensaje1.put("usuario", usuario);
		/////////////////////////////////////////////////////////
		Document nuevoMensaje2 = new Document();
		nuevoMensaje2.put("texto", "parece que va a estar nublado todo el dia");
		nuevoMensaje2.put("numero_megustas", 0);
		Document usuario2 = new Document();
		usuario2.put("email", "dolores@gmail.com");
		usuario2.put("rutaFoto", " 45345oij34.jpg");
		nuevoMensaje2.put("usuario", usuario2);
		/////////////////////////////////////////////////////////
		Document nuevoMensaje3 = new Document();
		nuevoMensaje3.put("texto", "Pues menos mal que estamos en verano");
		nuevoMensaje3.put("numero_megustas", 57);
		// Inserto usuario
		// Para ello debemos crear un documento
		// y añadirlo después al cliente
		Document usuario3 = new Document();
		usuario3.put("email", "pepe@gmail.com");
		usuario3.put("rutaFoto", " 34deg4&d.jpg");
		nuevoMensaje3.put("usuario", usuario3);
		// Creo una lista de documentos
		List<Document> documentos = Arrays.asList(nuevoMensaje1, nuevoMensaje2, nuevoMensaje3);
		//controlo excepción , si ya existen los documentos con el mismo texto no se insertan los datos y se controla excp
		try {
		        // Inserto en la colección
		        coleccionMensajes.insertMany(documentos);
		        System.out.println("Documentos insertados correctamente.");
		    } catch (MongoException e) {
		        System.out.println("Error al insertar los documentos: " + e.getMessage());
		    }
	}


}
